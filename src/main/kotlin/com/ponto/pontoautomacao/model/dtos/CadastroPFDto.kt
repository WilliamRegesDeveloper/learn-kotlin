package com.ponto.pontoautomacao.model.dtos

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class CadastroPFDto(

        @get:NotEmpty(message = "Nome não pode estar vazio")
        @get:Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres")
        val nome: String = "",

        @get:NotEmpty(message = "Nome não pode estar vazio")
        @get:Length(min = 5, max = 200, message = "Nome deve conter entre 5 e 200 caracteres")
        @get:Email(message = "Email inválido")
        val email: String = "",

        @get:NotEmpty(message = "Senha não pode estar vazio")
        val senha: String = "",

        @get:NotEmpty(message = "Nome não pode estar vazio")
        @get:CPF(message = "CPF inválido")
        val cpf: String = "",

        @get:NotEmpty(message = "Nome não pode estar vazio")
        @get:CNPJ(message = "CNPJ inválido")
        val cnpj: String = "",

        val empresaId: String = "",

        val valorHora: String? = null,
        val qtdHorasTrabalhoDia: String? = null,
        val qtdHorasAlmoco: String? = null,
        val id: String? = null
)