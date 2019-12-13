package com.bcesardev.employeetimeclockapikt.entrypoints.controllers

import com.bcesardev.employeetimeclockapikt.business.services.CompanyService
import com.bcesardev.employeetimeclockapikt.business.services.EmployeeService
import com.bcesardev.employeetimeclockapikt.business.utils.generateBCrypt
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Company
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Employee
import com.bcesardev.employeetimeclockapikt.dataproviders.enums.ProfileEnum
import com.bcesardev.employeetimeclockapikt.entrypoints.dtos.RegisterIndividualDto
import com.bcesardev.employeetimeclockapikt.entrypoints.dtos.ResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("api/register-individual")
class RegisterIndividualController(val companyService: CompanyService,
                                   val employeeService: EmployeeService) {

    @PostMapping
    fun register(@Valid @RequestBody dto: RegisterIndividualDto,
                 result: BindingResult): ResponseEntity<ResponseDto<RegisterIndividualDto>> {

        val responseDto: ResponseDto<RegisterIndividualDto> = ResponseDto<RegisterIndividualDto>()

        val company: Company? = companyService.searchByCnpj(dto.cnpj)
        validateExistingData(dto, company, result)

        if (result.hasErrors()) {
            for (error in result.allErrors) responseDto.errors.add(error.defaultMessage ?: "error")
            return ResponseEntity.badRequest().body(responseDto)
        }

        val employee: Employee = convertDtoToEmployee(dto, company!!)
        employeeService.persist(employee)

        responseDto.data = convertEntityToDto(employee, company!!)

        return ResponseEntity.ok(responseDto)
    }

    private fun validateExistingData(dto: RegisterIndividualDto, company: Company?, result: BindingResult) {

        if (company == null) {
            result.addError(ObjectError("company", "Empresa não cadastrada"))
        }

        val employeeCpf: Employee? = employeeService.searchByCpf(dto.cpf)
        if (employeeCpf != null) {
            result.addError(ObjectError("employee", "CPF já existente"))
        }

        val employeeEmail: Employee? = employeeService.searchByEmail(dto.email)
        if (employeeService != null) {
            result.addError(ObjectError("employee", "Email já existente"))
        }

    }

    private fun convertDtoToEmployee(dto: RegisterIndividualDto, company: Company): Employee =
            with(dto) {
                Employee(name, email, generateBCrypt(password), cpf, ProfileEnum.ROLE_USER, company.id.toString(),
                        hourValue?.toDouble(), qtyHoursWorkedByDay?.toFloat(), qtyHoursLunch?.toFloat(), id)
            }


    private fun convertEntityToDto(employee: Employee, company: Company): RegisterIndividualDto? =
            with(employee) {
                RegisterIndividualDto(name, email, "", cpf, company.cnpj, company.id.toString(),
                        valueHour.toString(), qtyWorkHourByDay.toString(), qtyLunchHour.toString(), id)
            }

}