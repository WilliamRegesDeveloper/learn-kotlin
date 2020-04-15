package com.ponto.pontoautomacao.security

import com.ponto.pontoautomacao.model.document.Funcionario
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCredential(val funcionario: Funcionario) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        var authority: MutableCollection<GrantedAuthority> = mutableListOf()
        authority.add(SimpleGrantedAuthority(funcionario.perfil.toString()))
        return authority
    }

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = funcionario.nome

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = funcionario.senha

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}