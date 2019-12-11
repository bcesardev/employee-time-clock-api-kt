package com.bcesardev.employeetimeclockapikt.business.services

import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Employee

interface EmployeeService {

    fun persist(employee: Employee): Employee

    fun searchByCpf(cpf: String): Employee?

    fun searchByEmail(email: String): Employee?

    fun searchById(id: String): Employee?

}