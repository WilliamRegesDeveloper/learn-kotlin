package com.ponto.pontoautomacao.controller

import com.ponto.pontoautomacao.model.document.Funcionario
import com.ponto.pontoautomacao.model.document.Lancamento
import com.ponto.pontoautomacao.model.dtos.LancamentoDto
import com.ponto.pontoautomacao.enums.TipoEnum
import com.ponto.pontoautomacao.response.Response
import com.ponto.pontoautomacao.services.FuncionarioService
import com.ponto.pontoautomacao.services.LancamentoService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import javax.validation.Valid

@RestController
@RequestMapping("/api/lancamentos")
class LancamentoController(val lancamentoService: LancamentoService,
                           val funcionarioService: FuncionarioService) {

    @Value("\${paginacao.qtd_por_pagina}")
    val qtdPorPagina: Int = 15

    private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @PostMapping
    fun adicionar(@Valid @RequestBody lancamentoDto: LancamentoDto,
                  result: BindingResult): ResponseEntity<Response<LancamentoDto>> {
        validarFuncionario(lancamentoDto, result)

        val response: Response<LancamentoDto> = Response<LancamentoDto>()
        if (result.hasErrors()) {
            for (erro in result.allErrors) response.erros.add(erro.defaultMessage!!)
            return ResponseEntity.badRequest().body(response)
        }

        val lancamento: Lancamento = converteDtoParaLancamento(lancamentoDto, result)
        lancamentoService.persistir(lancamento)

        response.data = converterLancamentoDto(lancamento)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun atualizarLancamento(@PathVariable("id") id: String,
                            @Valid @RequestBody lancamentoDto: LancamentoDto,
                            result: BindingResult):
            ResponseEntity<Response<LancamentoDto>> {
        val response = Response<LancamentoDto>()

        validarFuncionario(lancamentoDto, result)
        lancamentoDto.id = id

        val lancamento = converteDtoParaLancamento(lancamentoDto, result)

        if (result.hasErrors()) {
            for (res in result.allErrors) {
                response.erros.add(res.defaultMessage!!)
            }
            return ResponseEntity.badRequest().body(response)
        }

        lancamentoService.persistir(lancamento)
        response.data = converterLancamentoDto(lancamento)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun remover(@PathVariable("id") id: String): ResponseEntity<Response<String>> {
        val response = Response<String>()
        val lancamento: Lancamento? = lancamentoService.buscarPorId(id)
        if (lancamento == null) {
            response.erros.add("Erro ao remover lancamento. Registro não encontrado para id $id")
            return ResponseEntity.badRequest().body(response)
        }

        lancamentoService.remover(id)
        return ResponseEntity.ok(Response())
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable("id") id: String): ResponseEntity<Response<LancamentoDto>> {
        val response: Response<LancamentoDto> = Response<LancamentoDto>()
        val lancamento = lancamentoService.buscarPorId(id)
        if (lancamento == null) {
            response.erros.add("Lançamento nao encontrado para o ID $id")
            return ResponseEntity.badRequest().body(response)
        }

        response.data = converterLancamentoDto(lancamento)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/funcionario/{funcionarioId}")
    fun listarPorFuncionarioId(@PathVariable("funcionarioId") funcionarioId: String,
                               @RequestParam("pag", defaultValue = "0") pag: Int,
                               @RequestParam("ord", defaultValue = "id") ord: String,
                               @RequestParam("dir", defaultValue = "ASC") dir: String):
            ResponseEntity<Response<Page<LancamentoDto>>> {

        val response = Response<Page<LancamentoDto>>()
        val pageRequest = PageRequest.of(pag, qtdPorPagina, Sort.Direction.valueOf(dir), ord)

        val lancamentos: Page<Lancamento>? = lancamentoService.buscarPorFuncionarioId(
                funcionarioId, pageRequest)

        val lancamentosDTO: Page<LancamentoDto> =
                lancamentos!!.map { lancamento -> converterLancamentoDto(lancamento) }
        response.data = lancamentosDTO
        return ResponseEntity.ok(response)

    }

    private fun converterLancamentoDto(lancamento: Lancamento): LancamentoDto? =
            LancamentoDto(
                    dateFormat.format(lancamento.data),
                    lancamento.tipo.toString(),
                    lancamento.descricao,
                    lancamento.localizacao,
                    lancamento.funcionarioId,
                    lancamento.id
            )


    fun converteDtoParaLancamento(lancamentoDto: LancamentoDto, result: BindingResult): Lancamento {
        if (lancamentoDto.id != null) {
            val lanc: Lancamento? = lancamentoService.buscarPorId(lancamentoDto.id!!)
            if (lanc == null)
                result.addError(
                        ObjectError("lancamento",
                                "Lançamento não encontrado."))
        }

        return Lancamento(
                dateFormat.parse(lancamentoDto.data),
                TipoEnum.valueOf(lancamentoDto.tipo!!),
                lancamentoDto.funcionarioId!!,
                lancamentoDto.descricao,
                lancamentoDto.localizacao,
                lancamentoDto.id)
    }

    private fun validarFuncionario(lancamentoDto: LancamentoDto, result: BindingResult) {
        if (lancamentoDto.funcionarioId == null) {
            result.addError(
                    ObjectError("funcionario",
                            "Funcionario não informado"))
            return
        }

        val funcionario: Funcionario? = funcionarioService.buscarPorId(lancamentoDto.funcionarioId)
        if (funcionario == null) {
            result.addError(
                    ObjectError("funcionario",
                            "Funcionário não encontrado. ID inexistente."))
        }

    }
}