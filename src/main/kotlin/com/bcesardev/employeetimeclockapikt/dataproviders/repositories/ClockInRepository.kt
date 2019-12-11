package com.bcesardev.employeetimeclockapikt.dataproviders.repositories

import com.bcesardev.employeetimeclockapikt.dataproviders.documents.ClockIn
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface ClockInRepository : MongoRepository<ClockIn, String> {

    fun findByEmployeeId(employeeId: String, pageable: Pageable): Page<ClockIn>

}