package com.ponto.pontoautomacao.utils

interface MessageService {
    fun message(key: String, args: Array<String>?) : String
}