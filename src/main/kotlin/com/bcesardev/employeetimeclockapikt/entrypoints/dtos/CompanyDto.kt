package com.bcesardev.employeetimeclockapikt.entrypoints.dtos

data class CompanyDto (
        val corporateName: String,
        val cnpj: String,
        val id: String? = null
)