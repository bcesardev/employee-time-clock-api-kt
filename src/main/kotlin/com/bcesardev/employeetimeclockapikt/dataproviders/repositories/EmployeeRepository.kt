package com.bcesardev.employeetimeclockapikt.dataproviders.repositories

import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Employee
import org.springframework.data.mongodb.repository.MongoRepository

interface EmployeeRepository : MongoRepository<Employee, String> {

    fun findByEmail(email: String): Employee

    fun findByCpf(cpf: String): Employee
    
}