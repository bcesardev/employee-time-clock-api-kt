package com.bcesardev.employeetimeclockapikt.business.services

import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Company

interface CompanyService {

    fun searchByCnpj(cnpj: String): Company?

    fun persist(company: Company): Company

}