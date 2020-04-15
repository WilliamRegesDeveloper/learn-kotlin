package com.ponto.pontoautomacao.controller

import com.ponto.pontoautomacao.model.document.Empresa
import com.ponto.pontoautomacao.model.dtos.CadastroPJDto
import com.ponto.pontoautomacao.model.mapper.EmpresaMapper.converterDtoParaEmpresa
import com.ponto.pontoautomacao.model.mapper.FuncionarioMapper.converterCadastroPJDto
import com.ponto.pontoautomacao.model.mapper.FuncionarioMapper.converterDtoParaFuncionario
import com.ponto.pontoautomacao.response.Response
import com.ponto.pontoautomacao.services.EmpresaService
import com.ponto.pontoautomacao.services.FuncionarioService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/cadastrar-pj")
class CadastroPJController(val empresaService: EmpresaService,
                           val funcionarioService: FuncionarioService) {

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    fun cadastrar(@Valid @RequestBody cadastroPJDto: CadastroPJDto, result: BindingResult):
            ResponseEntity<Response<CadastroPJDto>> {

        val response = Response<CadastroPJDto>()

        validarDadosExistente(cadastroPJDto, result)

        if (result.hasErrors()) {
            result.allErrors.forEach { erro -> erro.defaultMessage?.let{response.erros.add(it)} }
            return ResponseEntity.badRequest().body(response)
        }

        var empresa: Empresa? = converterDtoParaEmpresa(cadastroPJDto)
        empresa = empresaService.persistir(empresa!!)

        var funcionario = converterDtoParaFuncionario(cadastroPJDto, empresa)
        funcionario = funcionarioService.persistir(funcionario)

        response.data = converterCadastroPJDto(funcionario, empresa)
        return ResponseEntity.ok(response)

    }


    private fun validarDadosExistente(cadastroPJDto: CadastroPJDto, result: BindingResult) {
        val empresa = empresaService.buscarPorCnpj(cadastroPJDto.cnpj)
        if (empresa != null)
            result.addError(ObjectError("Empresa", "Empresa já existe"))

        val funcionario = funcionarioService.buscarPorCpf(cadastroPJDto.cpf)
        if (funcionario != null)
            result.addError(ObjectError("Funcionario", "CNPJ já existente"))

        val email = funcionarioService.buscarPorEmail(cadastroPJDto.email)
        if (email != null)
            result.addError(ObjectError("Email", "Email já existente"))

    }

}