package com.ponto.pontoautomacao.services

import com.ponto.pontoautomacao.model.document.Empresa

interface EmpresaService {
    fun buscarPorCnpj(cnpj : String) : Empresa?
    fun persistir(empresa: Empresa) : Empresa
}