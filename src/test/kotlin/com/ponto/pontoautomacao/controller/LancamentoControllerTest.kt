package com.ponto.pontoautomacao.controller

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.ponto.pontoautomacao.model.document.Funcionario
import com.ponto.pontoautomacao.model.document.Lancamento
import com.ponto.pontoautomacao.model.dtos.LancamentoDto
import com.ponto.pontoautomacao.enums.PerfilEnum
import com.ponto.pontoautomacao.enums.TipoEnum
import com.ponto.pontoautomacao.services.FuncionarioService
import com.ponto.pontoautomacao.services.LancamentoService
import com.ponto.pontoautomacao.utils.SenhaUtils
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.text.SimpleDateFormat
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class LancamentoControllerTest {


    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val lancamentoService: LancamentoService? = null

    @MockBean
    private val funcionarioService: FuncionarioService? = null

    private val url: String = "/api/lancamentos/"
    private val funcionarioId: String = "1"
    private val lancamentoId: String = "1"
    private val tipo: String = TipoEnum.INICIO_TRABALHO.name
    private val date = Date()
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


    @Before
    fun setUp() {
    }

    @Test
    @WithMockUser
    @Throws(Exception::class)
    fun testAdicionarLancamento() {
        val lancamento = obterDadosLancamento()

        BDDMockito.given<Funcionario>(funcionarioService?.buscarPorId(funcionarioId)).willReturn(obterFuncionario())
        BDDMockito.given(lancamentoService?.persistir(obterDadosLancamento())).willReturn(lancamento)

        this.mvc!!.perform(MockMvcRequestBuilders.post(url)
                .content(obterJsonRequsicao())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.tipo").value(tipo))
                .andExpect(jsonPath("$.data.data").value(dateFormat.format(date)))
                .andExpect(jsonPath("$.data.funcionarioId").value(funcionarioId))
                .andExpect(jsonPath("$.erros").isEmpty())

    }


    @Test
    @WithMockUser
    @Throws(Exception::class)
    fun testAdicionarLancamentoFuncionarioInvalido() {

        BDDMockito.given(funcionarioService!!.buscarPorId(funcionarioId)).willReturn(null)

        mvc!!.perform(MockMvcRequestBuilders.post(url)
                .content(obterJsonRequsicao())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.erros")
                        .value("Funcionário não encontrado. ID inexistente."))
                .andExpect(jsonPath("$.data").isEmpty)

    }

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun testRemoverLancamento() {

        BDDMockito.given(lancamentoService?.buscarPorId(lancamentoId))
                .willReturn(obterDadosLancamento())

        mvc!!.perform(MockMvcRequestBuilders.delete(url + lancamentoId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }


    @Throws(JsonProcessingException::class)
    private fun obterJsonRequsicao(): String {
        val lancamentoDTO: LancamentoDto? =
                LancamentoDto(
                        dateFormat.format(date),
                        tipo,
                        "Descrição",
                        "1.243,4.345",
                        funcionarioId
                )

        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(lancamentoDTO)
    }

    private fun obterDadosLancamento(): Lancamento =
            Lancamento(
                    date,
                    TipoEnum.valueOf(tipo),
                    funcionarioId,
                    "Descriçâo",
                    "1.243,4.345",
                    lancamentoId
            )

    private fun obterFuncionario(): Funcionario =
            Funcionario(
                    "Nome",
                    "email@gmail.com",
                    SenhaUtils().gerarBcrypt("12345"),
                    "22233344455",
                    PerfilEnum.ROLE_USUARIO,
                    funcionarioId)
}
