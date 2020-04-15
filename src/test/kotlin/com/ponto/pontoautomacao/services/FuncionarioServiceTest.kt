package com.ponto.pontoautomacao.services

import com.ponto.pontoautomacao.model.document.Funcionario
import com.ponto.pontoautomacao.enums.PerfilEnum
import com.ponto.pontoautomacao.repositories.FuncionarioRepository
import com.ponto.pontoautomacao.utils.SenhaUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class FuncionarioServiceTest {

    @Autowired
    private val funcionarioService : FuncionarioService? = null

    @MockBean
    private val funcionarioRepository : FuncionarioRepository? = null

    private val email: String = "email@email.com"
    private val cpf: String = "1234567890"
    private val id: String = "1"

    @Before
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given(funcionarioRepository?.save(Mockito.any(Funcionario::class.java)))
                .willReturn(getFuncionario())

        BDDMockito.given(funcionarioRepository?.findById(id)).willReturn(Optional.of(getFuncionario()))
        BDDMockito.given(funcionarioRepository?.findByEmail(email)).willReturn(getFuncionario())
        BDDMockito.given(funcionarioRepository?.findByCpf(cpf)).willReturn(getFuncionario())
    }

    @Test
    fun testPersistir() {
        val funcionario : Funcionario? = funcionarioService?.persistir(getFuncionario())
        Assert.assertNotNull(funcionario)
    }

    @Test
    fun testBuscarPorCpf() {
        val funcionario : Funcionario? = funcionarioService?.buscarPorCpf(cpf)
        Assert.assertNotNull(funcionario)
    }

    @Test
    fun testBuscarPorEmail() {
        val funcionario : Funcionario? = funcionarioService?.buscarPorEmail(email)
        Assert.assertNotNull(funcionario)
    }

    @Test
    fun testBuscarPorId() {
        val funcionario : Funcionario? = funcionarioService?.buscarPorId(id)
        Assert.assertNotNull(funcionario)
    }

    fun getFuncionario() : Funcionario =
            Funcionario("nome", email, SenhaUtils().gerarBcrypt("12345"),
                    cpf, PerfilEnum.ROLE_USUARIO, id)
}