package com.ponto.pontoautomacao.utils

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories

class SenhaUtilsTest {

    private val SENHA : String = "password"
    private val bCryptEncoder = BCryptPasswordEncoder()

    @Test
    fun gerarBcrypt() {
        val hash = bCryptEncoder.encode(SENHA)
        Assert.assertTrue(bCryptEncoder.matches(SENHA, hash))
    }
}