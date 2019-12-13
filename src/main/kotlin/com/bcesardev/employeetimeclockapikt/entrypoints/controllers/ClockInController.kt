package com.bcesardev.employeetimeclockapikt.entrypoints.controllers

import com.bcesardev.employeetimeclockapikt.business.services.ClockInService
import com.bcesardev.employeetimeclockapikt.business.services.EmployeeService
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.ClockIn
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Employee
import com.bcesardev.employeetimeclockapikt.dataproviders.enums.ClockInTypeEnum
import com.bcesardev.employeetimeclockapikt.entrypoints.dtos.ClockInDto
import com.bcesardev.employeetimeclockapikt.entrypoints.dtos.ResponseDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import javax.validation.Valid

@RestController
@RequestMapping("/api/clock-in")
class ClockInController(val clockInService: ClockInService, val employeeService: EmployeeService) {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Value("\${pagination.qty-by-page}")
    val qtyByPage: Int = 0

    @PostMapping
    fun add(@Valid @RequestBody dto: ClockInDto, result: BindingResult): ResponseEntity<ResponseDto<ClockInDto>> {

        val response: ResponseDto<ClockInDto> = ResponseDto<ClockInDto>()
        validateEmployee(dto, result)

        if (result.hasErrors()) {
            for (error in result.allErrors) response.errors.add(error.defaultMessage ?: "")
            return ResponseEntity.badRequest().body(response)
        }

        val entity: ClockIn = convertDtoToEntity(dto, result)
        clockInService.persist(entity)
        response.data = convertEntityToDto(entity)
        return ResponseEntity.ok(response)
    }

    @GetMapping("{id}")
    fun getById(@PathVariable("id") id: String): ResponseEntity<ResponseDto<ClockInDto>> {
        val response: ResponseDto<ClockInDto> = ResponseDto<ClockInDto>()
        val entity: ClockIn? = clockInService.searchById(id)

        if (entity == null) {
            response.errors.add("Lançamento não encontrado para o id $id")
            return ResponseEntity.badRequest().body(response)
        }

        response.data = convertEntityToDto(entity)
        return ResponseEntity.ok(response)
    }

    @GetMapping("employee/{employeeId}")
    fun listByEmployeeId(@PathVariable("employeeId") employeeId: String,
                         @RequestParam(value = "page", defaultValue = "0") page: Int,
                         @RequestParam(value = "ord", defaultValue = "id") ord: String,
                         @RequestParam(value = "dir", defaultValue = "DESC") dir: String):
            ResponseEntity<ResponseDto<Page<ClockInDto>>> {

        val response: ResponseDto<Page<ClockInDto>> = ResponseDto<Page<ClockInDto>>()

        val pageRequest: PageRequest = PageRequest.of(page, qtyByPage, Sort.Direction.valueOf(dir), ord)

        val entities: Page<ClockIn> = clockInService.searchByEmployeeId(employeeId, pageRequest)

        response.data = entities.map { entity -> convertEntityToDto(entity) }

        return ResponseEntity.ok(response)
    }

    @PutMapping("{id}")
    fun update(@PathVariable("id") id: String, @Valid @RequestBody dto: ClockInDto,
               result: BindingResult): ResponseEntity<ResponseDto<ClockInDto>> {

        val response: ResponseDto<ClockInDto> = ResponseDto<ClockInDto>()
        validateEmployee(dto, result)
        dto.id = id

        val entity: ClockIn = convertDtoToEntity(dto, result)

        if (result.hasErrors()) {
            for (error in result.allErrors) response.errors.add(error.defaultMessage ?: "")
            return ResponseEntity.badRequest().body(response)
        }

        clockInService.persist(entity)
        response.data = convertEntityToDto(entity)
        return ResponseEntity.ok(response)

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun delete(@PathVariable("id") id: String): ResponseEntity<ResponseDto<String>> {

        val response: ResponseDto<String> = ResponseDto<String>()
        val entity: ClockIn? = clockInService.searchById(id)

        if (entity == null) {
            response.errors.add("Erro ao remover lançamento. Registro não encontrado para o id $id")
            return ResponseEntity.badRequest().body(response)
        }

        clockInService.remove(id)
        return ResponseEntity.ok(ResponseDto<String>())
    }


    private fun validateEmployee(clockInDto: ClockInDto, result: BindingResult) {
        if (clockInDto.employeeId == null) {
            result.addError(ObjectError("employee", "Funcionário não informado"))
            return
        }

        val employee: Employee? = employeeService.searchById(clockInDto.employeeId)
        if (employee == null) {
            result.addError(ObjectError("employee", "Funcionário não encontrado. ID inexistente"))
        }
    }

    private fun convertDtoToEntity(dto: ClockInDto, result: BindingResult): ClockIn {

        if (dto.id != null) {
            val ci: ClockIn? = clockInService.searchById(dto.id!!)
            if (ci == null) result.addError(ObjectError("clockIn", "Lançamento não encontrado"))
        }

        return ClockIn(dateFormat.parse(dto.date), ClockInTypeEnum.valueOf(dto.type!!),
                dto.employeeId!!, dto.description, dto.location, dto.id)
    }

    private fun convertEntityToDto(entity: ClockIn): ClockInDto =
            ClockInDto(dateFormat.format(entity.date), entity.clockInType.toString(),
                    entity.description, entity.locale, entity.employeeId, entity.id)
}