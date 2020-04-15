package com.ponto.pontoautomacao.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ponto.pontoautomacao.model.document.Empresa
import com.ponto.pontoautomacao.model.document.Funcionario
import com.ponto.pontoautomacao.model.dtos.CadastroPJDto
import com.ponto.pontoautomacao.model.mapper.EmpresaMapper.converterDtoParaEmpresa
import com.ponto.pontoautomacao.model.mapper.FuncionarioMapper.converterDtoParaFuncionario
import com.ponto.pontoautomacao.services.EmpresaService
import com.ponto.pontoautomacao.services.FuncionarioService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class CadastroPJControllerTest {

    @Autowired
    private val mock: MockMvc? = null

    @MockBean
    private val empresaService: EmpresaService? = null

    @MockBean
    private val funcionarioService: FuncionarioService? = null


    private val url: String = "/api/cadastrar-pj/"


    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun cadastrar() {

        val dto = getCadastroPJDto()

        BDDMockito.given(empresaService!!.buscarPorCnpj(Mockito.anyString())).willReturn(null)
        BDDMockito.given(funcionarioService!!.buscarPorCpf(Mockito.anyString())).willReturn(null)
        BDDMockito.given(funcionarioService!!.buscarPorEmail(Mockito.anyString())).willReturn(null)

        var empresa = converterDtoParaEmpresa(dto)
        var newEmpresa = empresa.copy(id = "1")
        BDDMockito.given<Empresa>(
                empresaService!!
                        .persistir(empresa))
                .willReturn(newEmpresa)


        var funcionario = converterDtoParaFuncionario(
                dto,
                newEmpresa)
        var newFuncionario = funcionario.copy(id = "1")
        BDDMockito.given<Funcionario>(
                funcionarioService!!
                        .persistir(
                                funcionario))
                .willReturn(newFuncionario)

        mock!!.perform(MockMvcRequestBuilders.post(url)
                .content(getBody())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)

    }

    private fun getBody(): String =
            ObjectMapper()
                    .writeValueAsString(getCadastroPJDto())


    fun getCadastroPJDto(): CadastroPJDto =
            CadastroPJDto("william",
                    "william@everis.com",
                    "password",
                    "22869991800",
                    "26.973.484/0001-49",
                    "William LTDA")

}