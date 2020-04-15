package com.ponto.pontoautomacao.controller

import com.ponto.pontoautomacao.model.document.Funcionario
import com.ponto.pontoautomacao.model.dtos.FuncionarioDto
import com.ponto.pontoautomacao.model.mapper.FuncionarioMapper.atualizarDadosFuncionario
import com.ponto.pontoautomacao.model.mapper.FuncionarioMapper.converterFuncionarioParaDto
import com.ponto.pontoautomacao.response.Response
import com.ponto.pontoautomacao.services.FuncionarioService
import com.ponto.pontoautomacao.utils.MessageService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/funcionarios")
class FuncionarioController(val funcionarioService: FuncionarioService,
                            val messageService: MessageService) {

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    fun atualizarFuncionario(@PathVariable("id") id: String,
                             @Valid @RequestBody dto: FuncionarioDto,
                             result: BindingResult):
            ResponseEntity<Response<FuncionarioDto>> {

        val response = Response<FuncionarioDto>()

        val funcionario = funcionarioService.buscarPorId(id)
        if (funcionario == null) {
            result.addError(
                    ObjectError("Funcionario",
                            messageService.message("funcionario.nao.encontrado", null)))
        }

        if (result.hasErrors()) {
            for (res in result.allErrors)
                response.erros.add(res.defaultMessage!!)
            return ResponseEntity.badRequest().body(response)
        }


        var newFuncionario: Funcionario? = atualizarDadosFuncionario(funcionario, dto)
        response.data =
                converterFuncionarioParaDto(
                        funcionarioService
                                .persistir(newFuncionario!!)
                )

        return ResponseEntity.ok(response)

    }


}