package com.ponto.pontoautomacao.model.document

import com.ponto.pontoautomacao.enums.PerfilEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Funcionario(
        var nome: String,
        var email: String,
        var senha: String,
        var cpf: String,
        var perfil: PerfilEnum,
        var empresaId: String,
        var valorHora: Double? = 0.0,
        var qtdHorasTrabalhoDia: Float? = 0.0f,
        var qtdHorasAlmoco: Float? = 0.0f,
        @Id var id: String? = null
)