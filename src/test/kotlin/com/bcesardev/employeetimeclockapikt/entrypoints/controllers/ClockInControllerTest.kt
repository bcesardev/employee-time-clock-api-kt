package com.bcesardev.employeetimeclockapikt.entrypoints.controllers

import com.bcesardev.employeetimeclockapikt.business.services.ClockInService
import com.bcesardev.employeetimeclockapikt.business.services.EmployeeService
import com.bcesardev.employeetimeclockapikt.business.utils.generateBCrypt
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.ClockIn
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Employee
import com.bcesardev.employeetimeclockapikt.dataproviders.enums.ClockInTypeEnum
import com.bcesardev.employeetimeclockapikt.dataproviders.enums.ProfileEnum
import com.bcesardev.employeetimeclockapikt.entrypoints.dtos.ClockInDto
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.text.SimpleDateFormat
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ClockInControllerTest {

    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val clockInService: ClockInService? = null

    @MockBean
    private val employeeService: EmployeeService? = null

    private val urlBase: String = "/api/clock-in/"
    private val employeeId: String = "1"
    private val clockInId: String = "1"
    private val type: String = ClockInTypeEnum.START_WORK.name
    private val date: Date = Date()

    private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun testRegisterClockIn() {
        val entity = getClockInEntity()

        BDDMockito.given<Employee>(employeeService?.searchById(employeeId)).willReturn(employee())
        BDDMockito.given(clockInService?.persist(getClockInEntity())).willReturn(entity)

        mvc!!.perform(MockMvcRequestBuilders.post(urlBase)
                .content(getJsonRequestPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.data.type").value(type))
                .andExpect(jsonPath("$.data.date").value(dateFormat.format(date)))
                .andExpect(jsonPath("$.data.employeeId").value(employeeId))
                .andExpect(jsonPath("$.errors").isEmpty)
    }

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun testRegisterClockInWithInvalidEmployeeId() {

        BDDMockito.given<Employee>(employeeService?.searchById(employeeId)).willReturn(null)

        mvc!!.perform(MockMvcRequestBuilders.post(urlBase)
                .content(getJsonRequestPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.errors").value("Funcionário não encontrado. ID inexistente"))
                .andExpect(jsonPath("$.data").isEmpty)
    }

    @Test
    @Throws(Exception::class)
    @WithMockUser(username = "admin@admin.com", roles = arrayOf("ADMIN"))
    fun testRemoveClockIn() {

        BDDMockito.given<ClockIn>(clockInService?.searchById(clockInId)).willReturn(getClockInEntity())

        mvc!!.perform(MockMvcRequestBuilders.delete(urlBase + clockInId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Throws(JsonProcessingException::class)
    private fun getJsonRequestPost(): String {
        val dto = ClockInDto(dateFormat.format(date), type, "Description", "1.234,4.234", employeeId)
        return ObjectMapper().writeValueAsString(dto)
    }

    private fun getClockInEntity(): ClockIn = ClockIn(date, ClockInTypeEnum.valueOf(type), employeeId,
            "Description", "1.243,4.345", clockInId)

    private fun employee(): Employee = Employee("Name", "email@email.com",
            generateBCrypt("123456"), "98765432100", ProfileEnum.ROLE_USER, employeeId)
}