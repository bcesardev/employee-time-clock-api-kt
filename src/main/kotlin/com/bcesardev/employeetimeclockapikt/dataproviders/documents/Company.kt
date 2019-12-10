package com.bcesardev.employeetimeclockapikt.dataproviders.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Company(
        val corporateName: String,
        val cnpj: String,
        @Id val id: String? = null
)