package com.ponto.pontoautomacao.utils

import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageServiceImpl(val messageSource: MessageSource) : MessageService {

    override fun message(key: String, args: Array<String>?): String =
            messageSource.getMessage(key, args, Locale.getDefault())
}