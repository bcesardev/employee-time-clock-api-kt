package com.bcesardev.employeetimeclockapikt.business.services.impl

import com.bcesardev.employeetimeclockapikt.business.services.CompanyService
import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Company
import com.bcesardev.employeetimeclockapikt.dataproviders.repositories.CompanyRepository
import org.springframework.stereotype.Service

@Service
class CompanyServiceImpl(val repository: CompanyRepository) : CompanyService {

    override fun searchByCnpj(cnpj: String): Company? = repository.findByCnpj(cnpj)

    override fun persist(company: Company): Company = repository.save(company)

}