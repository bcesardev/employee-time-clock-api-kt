package com.bcesardev.employeetimeclockapikt.business.services.impl

import com.bcesardev.employeetimeclockapikt.business.services.EmployeeService
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Employee
import com.bcesardev.employeetimeclockapikt.dataproviders.repositories.EmployeeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EmployeeServiceImpl(val repository: EmployeeRepository) : EmployeeService {

    override fun persist(employee: Employee): Employee = repository.save(employee)

    override fun searchByCpf(cpf: String): Employee? = repository.findByCpf(cpf)

    override fun searchByEmail(email: String): Employee? = repository.findByEmail(email)

    override fun searchById(id: String): Employee? = repository.findByIdOrNull(id)
}