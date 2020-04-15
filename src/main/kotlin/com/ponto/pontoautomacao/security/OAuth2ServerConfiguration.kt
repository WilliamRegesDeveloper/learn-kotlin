package com.ponto.pontoautomacao.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

@Configuration
@EnableWebSecurity
@EnableResourceServer
class OAuth2ServerConfiguration : ResourceServerConfigurerAdapter() {

    val RESOURCE_ID: String = "resource_id"

    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests()
                .anyRequest()
                .fullyAuthenticated()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .and()
                .cors().disable()
                .csrf().disable()
    }

    override fun configure(resources: ResourceServerSecurityConfigurer?) {
        resources!!.resourceId(RESOURCE_ID)
    }


    @Configuration
    @EnableAuthorizationServer
    class AuthorizationConfiguration(val authentication: AuthenticationManager?,
                                     val userDetailService: DetailService?,
                                     val passwordEncoder: PasswordEncoder) : AuthorizationServerConfigurerAdapter() {


        override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
            endpoints!!
                    .tokenStore(getTokenStore())
                    .accessTokenConverter(getConverterJWT())
                    .authenticationManager(authentication)
                    .userDetailsService(userDetailService)
        }


        @Bean
        fun getConverterJWT(): JwtAccessTokenConverter? {
            val jwt = JwtAccessTokenConverter()
            jwt.setSigningKey("senha@10")
            return jwt
        }

        @Bean
        fun getTokenStore(): TokenStore? = JwtTokenStore(getConverterJWT())

        override fun configure(clients: ClientDetailsServiceConfigurer?) {
            clients!!
                    .inMemory()
                    .withClient("client")
                    .secret(passwordEncoder.encode("123"))
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                    .accessTokenValiditySeconds(1800)
                    .scopes("all")
                    .refreshTokenValiditySeconds(30000)
                    .resourceIds("resource_id")
        }

    }

}