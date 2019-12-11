package com.bcesardev.employeetimeclockapikt.configurations

import com.bcesardev.employeetimeclockapikt.business.utils.generateBCrypt
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Company
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Employee
import com.bcesardev.employeetimeclockapikt.dataproviders.enums.ProfileEnum
import com.bcesardev.employeetimeclockapikt.dataproviders.repositories.CompanyRepository
import com.bcesardev.employeetimeclockapikt.dataproviders.repositories.EmployeeRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration

@Configuration
class DefaultValuesConfiguration(val companyRepository: CompanyRepository,
                                 val employeeRepository: EmployeeRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        companyRepository.deleteAll()
        employeeRepository.deleteAll()

        val company: Company = Company("Company", "10443887000146","1")
        companyRepository.save(company)

        val admin: Employee = Employee("Admin", "admin@company.com", generateBCrypt("123456"), "98765432100", ProfileEnum.ROLE_ADMIN, company.id!!, id = "1")
        employeeRepository.save(admin)

        val employee: Employee = Employee("Employee", "employee@company.com", generateBCrypt("123456"), "01234567890", ProfileEnum.ROLE_USER, company.id!!, id = "2")
        employeeRepository.save(employee)

        println("Company ID: ${company.id}")
        println("Admin ID: ${admin.id}")
        println("Employee ID: ${employee.id}")
    }

}