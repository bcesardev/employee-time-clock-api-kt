package com.bcesardev.employeetimeclockapikt.dataproviders.documents

import com.bcesardev.employeetimeclockapikt.dataproviders.enums.ClockInTypeEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class ClockIn(
        val date: Date,
        val clockInType: ClockInTypeEnum,
        val employeeId: String,
        val description: String? = "",
        val locale: String? = "",
        @Id val id: String? = null
)