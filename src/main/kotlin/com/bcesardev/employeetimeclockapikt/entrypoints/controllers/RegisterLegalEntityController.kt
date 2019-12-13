package com.bcesardev.employeetimeclockapikt.entrypoints.controllers

import com.bcesardev.employeetimeclockapikt.business.services.CompanyService
import com.bcesardev.employeetimeclockapikt.business.services.EmployeeService
import com.bcesardev.employeetimeclockapikt.business.utils.generateBCrypt
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Company
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Employee
import com.bcesardev.employeetimeclockapikt.dataproviders.enums.ProfileEnum
import com.bcesardev.employeetimeclockapikt.entrypoints.dtos.RegisterLegalEntityDto
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
@RequestMapping("api/register-legal-entity")
class RegisterLegalEntityController(val companyService: CompanyService,
                                    val employeeService: EmployeeService) {

    @PostMapping
    fun register(@Valid @RequestBody dto: RegisterLegalEntityDto,
                 result: BindingResult): ResponseEntity<ResponseDto<RegisterLegalEntityDto>> {

        val responseDto: ResponseDto<RegisterLegalEntityDto> = ResponseDto<RegisterLegalEntityDto>()

        validateExistingData(dto, result)
        if (result.hasErrors()) {
            for (error in result.allErrors) responseDto.errors.add(error.defaultMessage ?: "error")
            return ResponseEntity.badRequest().body(responseDto)
        }

        val company: Company = convertDtoToCompany(dto)
        companyService.persist(company)

        val employee: Employee = convertDtoToEmployee(dto, company)
        employeeService.persist(employee)

        responseDto.data = convertEntityToDto(employee, company)

        return ResponseEntity.ok(responseDto)
    }

    private fun validateExistingData(dto: RegisterLegalEntityDto, result: BindingResult) {

        val company: Company? = companyService.searchByCnpj(dto.cnpj)
        if (company != null) {
            result.addError(ObjectError("company", "Empresa já existente"))
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

    private fun convertDtoToCompany(dto: RegisterLegalEntityDto): Company =
            with(dto) {
                Company(companyName, cnpj)
            }

    private fun convertDtoToEmployee(dto: RegisterLegalEntityDto, company: Company): Employee =
            with(dto) {
                Employee(name, email, generateBCrypt(password), cpf, ProfileEnum.ROLE_ADMIN, company.id.toString())
            }

    private fun convertEntityToDto(employee: Employee, company: Company): RegisterLegalEntityDto =
            RegisterLegalEntityDto(employee.name, employee.email, "", employee.cpf, company.cnpj,
                    company.corporateName, employee.id)
}