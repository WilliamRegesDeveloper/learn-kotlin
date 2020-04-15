package com.ponto.pontoautomacao.model.dtos

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class CadastroPJDto(

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

        @get:NotEmpty(message = "Razão Social não pode estar vazio")
        @get:Length(min = 5, max = 200, message = "Razão Social deve conter entre 5 e 200 caracteres")
        val razaoSocial: String = "",

        val id: String? = null
)