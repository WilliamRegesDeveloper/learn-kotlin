package com.ponto.pontoautomacao.model.dtos

data class EmpresaDto(
        val razaoSocial: String,
        val cnpj: String,
        val id: String? = null
)