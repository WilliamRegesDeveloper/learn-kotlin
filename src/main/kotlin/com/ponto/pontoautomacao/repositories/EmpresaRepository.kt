package com.ponto.pontoautomacao.repositories

import com.ponto.pontoautomacao.model.document.Empresa
import org.springframework.data.mongodb.repository.MongoRepository

interface EmpresaRepository : MongoRepository<Empresa, String> {
    fun findByCnpj(cnpj: String): Empresa? = null
}