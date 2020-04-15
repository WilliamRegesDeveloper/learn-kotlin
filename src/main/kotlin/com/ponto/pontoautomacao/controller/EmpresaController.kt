package com.ponto.pontoautomacao.controller

import com.ponto.pontoautomacao.model.document.Empresa
import com.ponto.pontoautomacao.model.dtos.EmpresaDto
import com.ponto.pontoautomacao.model.mapper.EmpresaMapper.converterEmpresaParaDto
import com.ponto.pontoautomacao.response.Response
import com.ponto.pontoautomacao.services.EmpresaService
import com.ponto.pontoautomacao.utils.MessageService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/empresas")
class EmpresaController(val empresaService: EmpresaService,
                        val messageService: MessageService) {

    @PreAuthorize("hasAnyRole('USUARIO') OR hasAnyRole('ADMIN')")
    @GetMapping("/cnpj/{cnpj}")
    fun obterEmpresaPorCNPJ(@PathVariable("cnpj") cnpj: String):
            ResponseEntity<Response<EmpresaDto>> {

        val response = Response<EmpresaDto>()
        val empresa: Empresa? = empresaService.buscarPorCnpj(cnpj)

        if (empresa == null) {
            response.erros.add(messageService.message("empresa.nao.encontrado", arrayOf(cnpj)))
            return ResponseEntity.badRequest().body(response)
        }

        response.data = converterEmpresaParaDto(empresa!!)
        return ResponseEntity.ok(response)
    }
}