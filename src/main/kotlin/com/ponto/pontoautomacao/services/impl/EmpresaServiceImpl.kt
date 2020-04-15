package com.ponto.pontoautomacao.services.impl

import com.ponto.pontoautomacao.model.document.Empresa
import com.ponto.pontoautomacao.repositories.EmpresaRepository
import com.ponto.pontoautomacao.services.EmpresaService
import org.springframework.stereotype.Service

@Service
class EmpresaServiceImpl(val empresaRepository: EmpresaRepository) : EmpresaService {

    override fun buscarPorCnpj(cnpj: String): Empresa? = empresaRepository.findByCnpj(cnpj)

    override fun persistir(empresa: Empresa): Empresa = empresaRepository.save(empresa)


}