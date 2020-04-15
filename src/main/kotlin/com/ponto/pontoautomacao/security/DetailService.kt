package com.ponto.pontoautomacao.security

import com.ponto.pontoautomacao.services.FuncionarioService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class DetailService(val funcionarioService: FuncionarioService) : UserDetailsService {

    override fun loadUserByUsername(email: String?): UserDetails {
        val funcionario = funcionarioService.buscarPorEmail(email!!)

        if (email != null) {
            if (funcionario != null)
                return UserCredential(funcionario)
        }

        throw UsernameNotFoundException(email)
    }
}