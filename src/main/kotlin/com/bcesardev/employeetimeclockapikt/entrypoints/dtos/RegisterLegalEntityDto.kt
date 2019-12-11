package com.bcesardev.employeetimeclockapikt.entrypoints.dtos

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class RegisterLegalEntityDto(

        @get:NotBlank(message = "Nome não pode estar vazio")
        @get:Length(min = 3, max = 200, message = "Nome deve conter entre {min} e {max} caracteres")
        val name: String = "",

        @get:NotBlank(message = "Email não pode estar vazio")
        @get:Length(min = 5, max = 200, message = "Email deve conter entre {min} e {max} caracteres")
        @get:Email(message = "Email inválido")
        val email: String = "",

        @get:NotBlank(message = "Senha não pode estar vazia")
        val password: String = "",

        @get:NotBlank(message = "CPF não pode estar vazio")
        @get:CPF(message = "CPF Inválido")
        val cpf: String = "",

        @get:NotBlank(message = "CNPJ não pode estar vazio")
        @get:CNPJ(message = "CNPJ invalido")
        val cnpj: String = "",

        @get:NotBlank(message = "Nome da empresa não pode estar vazio")
        @get:Length(min = 5, max = 200, message = "Nome da empresa deve conter entre {min} e {max} caracteres")
        val companyName: String = "",

        val id: String? = null

)