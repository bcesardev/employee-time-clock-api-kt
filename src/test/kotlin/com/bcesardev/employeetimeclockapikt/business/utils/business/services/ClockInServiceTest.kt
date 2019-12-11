package com.bcesardev.employeetimeclockapikt.business.utils.business.services

import com.bcesardev.employeetimeclockapikt.business.services.ClockInService
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.ClockIn
import com.bcesardev.employeetimeclockapikt.dataproviders.enums.ClockInTypeEnum
import com.bcesardev.employeetimeclockapikt.dataproviders.repositories.ClockInRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*
import kotlin.collections.ArrayList

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ClockInServiceTest {

    @MockBean
    private val repository: ClockInRepository? = null

    @Autowired
    private val service: ClockInService? = null

    private val id: String = "1"

    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given<Page<ClockIn>>(repository?.findByEmployeeId(id, PageRequest.of(0, 10))).willReturn(PageImpl(ArrayList<ClockIn>()))
        BDDMockito.given(repository?.findByIdOrNull(id)).willReturn(clockIn())
        BDDMockito.given(repository?.save(Mockito.any(ClockIn::class.java))).willReturn(clockIn())
    }

    @Test
    fun testSearchClockInByEmployeeId() {
        val clockIn: Page<ClockIn>? = service?.searchByEmployeeId(id, PageRequest.of(0, 10))
        Assertions.assertNotNull(clockIn)
    }

    @Test
    fun testSearchClockInById() {
        val clockIn: ClockIn? = service?.searchById(id)
        Assertions.assertNotNull(clockIn)
    }

    @Test
    fun testPersistClockIn() {
        val clockIn: ClockIn? = service?.persist(clockIn())
        Assertions.assertNotNull(clockIn)
    }

    fun clockIn(): ClockIn = ClockIn(Date(), ClockInTypeEnum.START_WORK, id)
}