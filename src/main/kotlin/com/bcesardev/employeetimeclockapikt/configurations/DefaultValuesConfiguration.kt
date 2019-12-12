package com.bcesardev.employeetimeclockapikt.configurations

import com.bcesardev.employeetimeclockapikt.business.utils.generateBCrypt
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Company
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Employee
import com.bcesardev.employeetimeclockapikt.dataproviders.enums.ProfileEnum
import com.bcesardev.employeetimeclockapikt.dataproviders.repositories.ClockInRepository
import com.bcesardev.employeetimeclockapikt.dataproviders.repositories.CompanyRepository
import com.bcesardev.employeetimeclockapikt.dataproviders.repositories.EmployeeRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration

@Configuration
class DefaultValuesConfiguration(val companyRepository: CompanyRepository,
                                 val employeeRepository: EmployeeRepository,
                                 val clockInRepository: ClockInRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        companyRepository.deleteAll()
        employeeRepository.deleteAll()
        clockInRepository.deleteAll()

        val company: Company = Company("Company", "10443887000146")
        val companySaved = companyRepository.save(company)

        val admin: Employee = Employee("Admin", "admin@company.com", generateBCrypt("123456"),
                "98765432100", ProfileEnum.ROLE_ADMIN, companySaved.id!!)
        val adminSaved = employeeRepository.save(admin)

        val employee: Employee = Employee("Employee", "employee@company.com",
                generateBCrypt("123456"), "01234567890", ProfileEnum.ROLE_USER, companySaved.id!!)
        val employeeSaved = employeeRepository.save(employee)

        println("Company ID: ${companySaved.id}")
        println("Admin ID: ${adminSaved.id}")
        println("Employee ID: ${employeeSaved.id}")
    }

}