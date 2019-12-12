package com.bcesardev.employeetimeclockapikt.business.services.impl

import com.bcesardev.employeetimeclockapikt.business.services.ClockInService
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.ClockIn
import com.bcesardev.employeetimeclockapikt.dataproviders.repositories.ClockInRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ClockInServiceImpl(val repository: ClockInRepository) : ClockInService {

    override fun searchByEmployeeId(employeeId: String, pageRequest: PageRequest): Page<ClockIn> =
            repository.findByEmployeeId(employeeId, pageRequest)

    override fun searchById(id: String): ClockIn? = repository.findByIdOrNull(id)

    override fun persist(clockIn: ClockIn): ClockIn = repository.save(clockIn)

    override fun remove(id: String) = repository.deleteById(id)

}