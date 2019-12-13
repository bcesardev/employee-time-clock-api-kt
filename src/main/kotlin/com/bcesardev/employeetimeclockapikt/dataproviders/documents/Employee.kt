package com.bcesardev.employeetimeclockapikt.dataproviders.documents

import com.bcesardev.employeetimeclockapikt.dataproviders.enums.ProfileEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Employee(
        val name: String,
        val email: String,
        val password: String,
        val cpf: String,
        val profile: ProfileEnum,
        val companyId: String,
        val valueHour: Double? = 0.0,
        val qtyWorkHourByDay: Float? = 0.0f,
        val qtyLunchHour: Float? = 0.0f,
        @Id val id: String? = null
)