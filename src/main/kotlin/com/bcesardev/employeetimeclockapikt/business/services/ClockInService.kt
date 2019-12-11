package com.bcesardev.employeetimeclockapikt.business.services

import com.bcesardev.employeetimeclockapikt.dataproviders.documents.ClockIn
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface ClockInService {

    fun searchByEmployeeId(employeeId: String, pageRequest: PageRequest): Page<ClockIn>

    fun searchById(id: String): ClockIn?

    fun persist(clockIn: ClockIn): ClockIn

    fun remove(id: String)
    
}