package com.bcesardev.employeetimeclockapikt.entrypoints.dtos

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class RegisterIndividualDto(

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

        val companyId: String = "",

        val hourValue: String? = null,
        val qtyHoursWorkedByDay: String? = null,
        val qtyHoursLunch: String? = null,
        val id: String? = null

)