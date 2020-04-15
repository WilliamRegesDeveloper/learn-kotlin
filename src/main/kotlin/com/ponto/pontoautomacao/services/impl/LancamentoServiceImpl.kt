package com.ponto.pontoautomacao.services.impl

import com.ponto.pontoautomacao.model.document.Lancamento
import com.ponto.pontoautomacao.repositories.LancamentoRepository
import com.ponto.pontoautomacao.services.LancamentoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class LancamentoServiceImpl (val lancamentoRepository: LancamentoRepository): LancamentoService {

    override fun buscarPorFuncionarioId(id: String, pageRequest: PageRequest): Page<Lancamento>? =
            lancamentoRepository.findByFuncionarioId(id, pageRequest)


    override fun buscarPorId(id: String): Lancamento? = lancamentoRepository.findByIdOrNull(id)

    override fun persistir(lancamento: Lancamento): Lancamento = lancamentoRepository.save(lancamento)

    override fun remover(id: String) = lancamentoRepository.deleteById(id)
}