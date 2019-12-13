package com.bcesardev.employeetimeclockapikt.security

import com.bcesardev.employeetimeclockapikt.business.services.EmployeeService
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Employee
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class EmployeeDetailsService(val employeeService: EmployeeService) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username != null) {
            val employee: Employee? = employeeService.searchByEmail(username)
            if (employee != null) {
                return EmployeePrincipal(employee)
            }
        }
        throw UsernameNotFoundException(username)
    }

}