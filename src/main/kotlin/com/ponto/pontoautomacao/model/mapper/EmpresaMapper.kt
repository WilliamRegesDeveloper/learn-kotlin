package com.ponto.pontoautomacao.model.mapper

import com.ponto.pontoautomacao.model.document.Empresa
import com.ponto.pontoautomacao.model.dtos.CadastroPJDto
import com.ponto.pontoautomacao.model.dtos.EmpresaDto

object EmpresaMapper {

    fun converterDtoParaEmpresa(cadastroPJDto: CadastroPJDto): Empresa =
            Empresa(cadastroPJDto.razaoSocial,
                    cadastroPJDto.cnpj)

    fun converterEmpresaParaDto(empresa: Empresa): EmpresaDto =
            EmpresaDto(empresa.razaoSocial,
                    empresa.cnpj!!,
                    empresa.id)
}