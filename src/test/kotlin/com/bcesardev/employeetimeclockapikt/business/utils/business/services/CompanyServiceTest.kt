package com.bcesardev.employeetimeclockapikt.business.utils.business.services

import com.bcesardev.employeetimeclockapikt.business.services.CompanyService
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Company
import com.bcesardev.employeetimeclockapikt.dataproviders.repositories.CompanyRepository
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class CompanyServiceTest {

    @Autowired
    val companyService: CompanyService? = null

    @MockBean
    private val repository: CompanyRepository? = null

    private val CNPJ = "51463645000100"

    @Before
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