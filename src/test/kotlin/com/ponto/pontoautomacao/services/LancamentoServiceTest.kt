package com.ponto.pontoautomacao.services

import com.ponto.pontoautomacao.model.document.Lancamento
import com.ponto.pontoautomacao.enums.TipoEnum
import com.ponto.pontoautomacao.repositories.LancamentoRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class LancamentoServiceTest {

    @Autowired
    private val lancamentoService: LancamentoService? = null

    @MockBean
    private val lancamentoRepository: LancamentoRepository? = null

    private val id: String = "1"

    @Before
    fun setUp() {
        BDDMockito.given(lancamentoRepository?.findByFuncionarioId(id, PageRequest.of(0, 10)))
                .willReturn(PageImpl(ArrayList<Lancamento>()))
        BDDMockito.given(lancamentoRepository?.findById(id)).willReturn(Optional.of(getLancamento()))
        BDDMockito.given(lancamentoRepository?.save(Mockito.any(Lancamento::class.java)))
                .willReturn(getLancamento())
    }

    @Test
    fun testBuscarPorFuncionarioId() {
        val page: Page<Lancamento>? = lancamentoService?.buscarPorFuncionarioId(id, PageRequest.of(0, 10))
        Assert.assertNotNull(page)
    }

    @Test
    fun testBuscarPorId() {
        val lancamento: Lancamento? = lancamentoService?.buscarPorId(id)
        Assert.assertNotNull(lancamento)
    }

    @Test
    fun testPersistir() {
        val lancamento: Lancamento? = lancamentoService?.persistir(getLancamento())
        Assert.assertNotNull(lancamento)
    }


    private fun getLancamento(): Lancamento = Lancamento(Date(), TipoEnum.INICIO_ALMOCO, id)

}
