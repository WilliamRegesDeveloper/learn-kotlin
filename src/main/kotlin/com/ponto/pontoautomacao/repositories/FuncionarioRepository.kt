package com.ponto.pontoautomacao.repositories

import com.ponto.pontoautomacao.model.document.Funcionario
import org.springframework.data.mongodb.repository.MongoRepository

interface FuncionarioRepository : MongoRepository<Funcionario, String> {
    fun findByEmail(email: String): Funcionario? = null
    fun findByCpf(cpf: String): Funcionario? = null
}