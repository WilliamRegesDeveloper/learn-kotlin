package com.ponto.pontoautomacao.services

import com.ponto.pontoautomacao.model.document.Empresa
import com.ponto.pontoautomacao.repositories.EmpresaRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class EmpresaServiceTest {

    @Autowired
    val empresaService: EmpresaService? = null

    @MockBean
    val empresaRepository: EmpresaRepository? = null

    val CNPJ: String = "22822822899"

    @Before
    fun setUp() {
        BDDMockito.given(empresaRepository?.findByCnpj(CNPJ)).willReturn(getEmpresa())
        BDDMockito.given(empresaRepository?.save(getEmpresa())).willReturn(getEmpresa())
    }

    @Test
    fun buscarPorCnpj() {
        val empresa = empresaService?.buscarPorCnpj(CNPJ)
        Assert.assertNotNull(empresa)
    }

    @Test
    fun persistir() {
        val empresa = empresaService?.persistir(getEmpresa())
        Assert.assertNotNull(empresa)
    }

    fun getEmpresa(): Empresa = Empresa("William Franklin", CNPJ, "1")
}