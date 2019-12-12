package com.bcesardev.employeetimeclockapikt.business.utils.business.services

import com.bcesardev.employeetimeclockapikt.business.services.EmployeeService
import com.bcesardev.employeetimeclockapikt.business.utils.generateBCrypt
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Employee
import com.bcesardev.employeetimeclockapikt.dataproviders.enums.ProfileEnum
import com.bcesardev.employeetimeclockapikt.dataproviders.repositories.EmployeeRepository
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class EmployeeServiceTest {

    @MockBean
    private val repository: EmployeeRepository? = null

    @Autowired
    private val service: EmployeeService? = null

    private val email: String = "email@email.com"
    private val cpf: String = "98765432100"
    private val id: String = "1"

    @Before
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given(repository?.save(Mockito.any(Employee::class.java))).willReturn(employee())
        BDDMockito.given(repository?.findByEmail(email)).willReturn(employee())
        BDDMockito.given(repository?.findByCpf(cpf)).willReturn(employee())
        BDDMockito.given(repository?.findByIdOrNull(cpf)).willReturn(employee())
    }

    @Test
    fun testPersistEmployee() {
        val employee: Employee? = this.service?.persist(employee())
        assertNotNull(employee)
    }

    @Test
    fun testSearchEmployeeByCpf() {
        val employee: Employee? = this.service?.searchByCpf(cpf)
        assertNotNull(employee)
    }

    @Test
    fun testSearchEmployeeByEmail() {
        val employee: Employee? = this.service?.searchByEmail(cpf)
        assertNotNull(employee)
    }

    @Test
    fun testSearchEmployeeById() {
        val employee: Employee? = this.service?.searchById(id)
        assertNotNull(employee)
    }

    fun employee(): Employee = Employee("Name", email, generateBCrypt("123456"), cpf, ProfileEnum.ROLE_USER, id)

}