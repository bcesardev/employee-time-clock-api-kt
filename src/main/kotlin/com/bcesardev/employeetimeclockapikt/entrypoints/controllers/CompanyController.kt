package com.bcesardev.employeetimeclockapikt.entrypoints.controllers

import com.bcesardev.employeetimeclockapikt.business.services.CompanyService
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Company
import com.bcesardev.employeetimeclockapikt.entrypoints.dtos.CompanyDto
import com.bcesardev.employeetimeclockapikt.entrypoints.dtos.ResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/company")
class CompanyController(val companyService: CompanyService) {

    @GetMapping("cnpj/{cnpj}")
    fun searchByCnpj(@PathVariable("cnpj") cnpj: String): ResponseEntity<ResponseDto<CompanyDto>> {

        val responseDto: ResponseDto<CompanyDto> = ResponseDto<CompanyDto>()

        val company: Company? = companyService.searchByCnpj(cnpj)

        if (company == null) {
            responseDto.errors.add("Empresa não encontrada para o CNPJ $cnpj")
            return ResponseEntity.badRequest().body(responseDto)
        }

        responseDto.data = convertEntityToDto(company)
        return ResponseEntity.ok(responseDto)
    }

    private fun convertEntityToDto(company: Company): CompanyDto? =
            with(company) {
                CompanyDto(corporateName, cnpj, id)
            }
}