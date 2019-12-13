package com.bcesardev.employeetimeclockapikt.entrypoints.controllers

import com.bcesardev.employeetimeclockapikt.business.services.EmployeeService
import com.bcesardev.employeetimeclockapikt.business.utils.generateBCrypt
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Employee
import com.bcesardev.employeetimeclockapikt.entrypoints.dtos.EmployeeDto
import com.bcesardev.employeetimeclockapikt.entrypoints.dtos.ResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/employee")
class EmployeeController(val employeeService: EmployeeService) {

    @PutMapping("{id}")
    fun update(@PathVariable("id") id: String, @Valid @RequestBody dto: EmployeeDto, result: BindingResult):
            ResponseEntity<ResponseDto<EmployeeDto>> {

        val responseDto: ResponseDto<EmployeeDto> = ResponseDto<EmployeeDto>()

        val employee: Employee? = employeeService.searchById(id)

        if (employee == null) {
            result.addError(ObjectError("employee", "Funcionário não encontrado"))
        }

        if (result.hasErrors()) {
            for (error in result.allErrors) responseDto.errors.add(error.defaultMessage ?: "")
            return ResponseEntity.badRequest().body(responseDto)
        }

        val employeeUpdated: Employee = updateEmployeeData(employee!!, dto)
        employeeService.persist(employeeUpdated)

        responseDto.data = convertEntityToDto(employeeUpdated)

        return ResponseEntity.ok(responseDto)
    }

    private fun updateEmployeeData(entity: Employee, dto: EmployeeDto): Employee {

        val password = if (dto.password == null) entity.password else generateBCrypt(dto.password)

        return Employee(dto.name, entity.email, password, entity.cpf, entity.profile, entity.companyId,
                dto.hourValue?.toDouble(), dto.qtyHoursWorkedByDay?.toFloat(),
                dto.qtyHoursLunch?.toFloat(), entity.id)
    }

    private fun convertEntityToDto(entity: Employee): EmployeeDto? =
            with(entity) {
                EmployeeDto(name, email, "", valueHour.toString(), qtyWorkHourByDay.toString(),
                        qtyLunchHour.toString(), id)
            }
}
