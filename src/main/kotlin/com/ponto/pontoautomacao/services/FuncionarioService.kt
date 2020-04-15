package com.ponto.pontoautomacao.services

import com.ponto.pontoautomacao.model.document.Funcionario

interface FuncionarioService {

    fun persistir(funcionario: Funcionario) : Funcionario

    fun buscarPorCpf(cpf: String) : Funcionario?

    fun buscarPorEmail(email: String) : Funcionario?

    fun buscarPorId(id: String) : Funcionario?
}