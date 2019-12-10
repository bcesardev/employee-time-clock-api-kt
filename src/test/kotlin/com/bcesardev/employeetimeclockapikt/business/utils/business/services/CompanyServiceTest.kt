package com.bcesardev.employeetimeclockapikt.business.utils.business.services

import com.bcesardev.employeetimeclockapikt.business.services.CompanyService
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Company
import com.bcesardev.employeetimeclockapikt.dataproviders.repositories.CompanyRepository
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class CompanyServiceTest {

    @Autowired
    val companyService: CompanyService? = null

    @MockBean
    private val repository: CompanyRepository? = null

    private val CNPJ = "51463645000100"

    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given(repository?.findByCnpj(CNPJ)).willReturn(company())
        BDDMockito.given(repository?.save(company())).willReturn(company())
    }

    @Test
    fun testSearchCompanyByCnpj() {
        val company: Company? = companyService?.searchByCnpj(CNPJ)
        assertNotNull(company)
    }

    @Test
    fun testPersistCompany() {
        val company: Company? = companyService?.persist(company())
        assertNotNull(company)
    }

    private fun company(): Company = Company("Corporate Name", CNPJ, "1")

}