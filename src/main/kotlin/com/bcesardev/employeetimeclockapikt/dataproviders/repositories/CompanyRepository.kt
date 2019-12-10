package com.bcesardev.employeetimeclockapikt.dataproviders.repositories

import com.bcesardev.employeetimeclockapikt.dataproviders.documents.Company
import org.springframework.data.mongodb.repository.MongoRepository

interface CompanyRepository : MongoRepository<Company, String> {

    fun findByCnpj(cnpj: String): Company

}