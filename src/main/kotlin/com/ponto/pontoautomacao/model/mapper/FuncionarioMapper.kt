package com.ponto.pontoautomacao.model.mapper

import com.ponto.pontoautomacao.enums.PerfilEnum
import com.ponto.pontoautomacao.model.document.Empresa
import com.ponto.pontoautomacao.model.document.Funcionario
import com.ponto.pontoautomacao.model.dtos.CadastroPJDto
import com.ponto.pontoautomacao.model.dtos.FuncionarioDto
import com.ponto.pontoautomacao.utils.SenhaUtils

object FuncionarioMapper {

    fun converterCadastroPJDto(funcionario: Funcionario, empresa: Empresa): CadastroPJDto =
            CadastroPJDto(
                    funcionario.nome,
                    funcionario.email,
                    funcionario.senha,
                    funcionario.cpf,
                    empresa.razaoSocial,
                    funcionario.id!!)

    fun converterDtoParaFuncionario(cadastroPJDto: CadastroPJDto, empresa: Empresa): Funcionario =
            Funcionario(cadastroPJDto.nome,
                    cadastroPJDto.email,
                    gerarCriptografia(cadastroPJDto),
                    cadastroPJDto.cpf,
                    PerfilEnum.ROLE_ADMIN,
                    empresa.id.toString()
            )

    private fun gerarCriptografia(cadastroPJDto: CadastroPJDto) =
            if (cadastroPJDto.senha != null && cadastroPJDto.senha != "password")
                SenhaUtils().gerarBcrypt(cadastroPJDto.senha)
            else "\$2a\$10\$91DaQihAuLx3LdxzxMM1DOqpLYiSkb5PO0wrkDnqtMilQ9xGEA5ea"


    fun converterFuncionarioParaDto(funcionario: Funcionario): FuncionarioDto =
            FuncionarioDto(funcionario.nome,
                    funcionario.email,
                    "",
                    funcionario.valorHora.toString(),
                    funcionario.qtdHorasTrabalhoDia.toString(),
                    funcionario.qtdHorasAlmoco.toString(),
                    funcionario.id)


    fun atualizarDadosFuncionario(funcionario: Funcionario?, dto: FuncionarioDto): Funcionario? {

        var senha: String
        if (dto.senha == null)
            senha = funcionario!!.senha
        else
            senha = SenhaUtils().gerarBcrypt(dto.senha)

        return funcionario!!
                .copy(
                        nome = dto.nome,
                        senha = senha,
                        valorHora = dto.valorHora?.toDouble(),
                        qtdHorasTrabalhoDia = dto.qtdHorasTrabalhoDia?.toFloat(),
                        qtdHorasAlmoco = dto.qtdHorasAlmoco?.toFloat())
    }
}