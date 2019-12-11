package com.bcesardev.employeetimeclockapikt.entrypoints.dtos

import javax.validation.constraints.NotBlank

data class ClockInDto(

        @get:NotBlank(message = "Data não pode ser vazia")
        val date: String? = null,

        @get:NotBlank(message = "Tipo não pode ser vazio")
        val type: String? = null,

        val description: String? = null,
        val location: String? = null,
        val employeeId: String? = null,
        var id: String? = null
)