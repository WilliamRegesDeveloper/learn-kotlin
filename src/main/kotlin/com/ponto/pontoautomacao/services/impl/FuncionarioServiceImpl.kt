package com.ponto.pontoautomacao.services.impl

import com.ponto.pontoautomacao.model.document.Funcionario
import com.ponto.pontoautomacao.repositories.FuncionarioRepository
import com.ponto.pontoautomacao.services.FuncionarioService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FuncionarioServiceImpl (val funcionarioRepository: FuncionarioRepository) : FuncionarioService {

    override fun persistir(funcionario: Funcionario): Funcionario =
            funcionarioRepository.save(funcionario)

    override fun buscarPorCpf(cpf: String): Funcionario? = funcionarioRepository.findByCpf(cpf)

    override fun buscarPorEmail(email: String): Funcionario? = funcionarioRepository.findByEmail(email)

    override fun buscarPorId(id: String): Funcionario? = funcionarioRepository.findByIdOrNull(id)
}