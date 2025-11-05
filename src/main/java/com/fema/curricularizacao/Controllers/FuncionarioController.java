package com.fema.curricularizacao.Controllers;

import com.fema.curricularizacao.DTO.*;
import com.fema.curricularizacao.auth.TokenService;
import com.fema.curricularizacao.models.Funcionario;
import com.fema.curricularizacao.services.FuncionarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    private final TokenService tokenService;
    private final FuncionarioService funcionarioService;
    public FuncionarioController(FuncionarioService funcionarioService, TokenService tokenService) {
        this.funcionarioService = funcionarioService;
        this.tokenService = tokenService;
    }

    @GetMapping("/buscar/{email}")
    public ResponseEntity<FuncionarioDto> buscarFuncionario(@PathVariable String email) {
        Funcionario funcionario = funcionarioService.buscarFuncionarioPorEmail(email);
        if (funcionario != null) {
            FuncionarioDto dto = new FuncionarioDto(
                    funcionario.getId(),
                    funcionario.getNome(),
                    funcionario.getEmail(),
                    null,
                    funcionario.getAtuacao(),
                    null,
                    funcionario.getAtivo()
            );
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> fazerLogin(@RequestBody FuncionarioDto funcionarioDto) {
        Funcionario funcionarioAutenticado = funcionarioService.autenticar(
                funcionarioDto.getEmail(),
                funcionarioDto.getSenha()
        );

        if (funcionarioAutenticado != null) {
            String jwtToken = tokenService.gerarToken(funcionarioAutenticado);

            FuncionarioDto funcionarioAutenticadoDTO = new FuncionarioDto(
                    funcionarioAutenticado.getId(),
                    funcionarioAutenticado.getNome(),
                    funcionarioAutenticado.getEmail(),
                    null,
                    funcionarioAutenticado.getAtuacao(),
                    jwtToken,
                    funcionarioAutenticado.getAtivo()
            );
            return ResponseEntity.ok(funcionarioAutenticadoDTO);
        } else {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrarFuncionario(@RequestBody FuncionarioFormDto form) {
        this.funcionarioService.cadastrarFuncionario(form);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/buscar-funcionarios-ativos")
    public ResponseEntity<List<FuncionariosAtivosDTO>> buscarFuncionarios (){
        return ResponseEntity.status(200).body(this.funcionarioService.buscarTodosOsFuncionariosAtivos());
    }

    @GetMapping("/buscar-funcionarios")
    public ResponseEntity<List<InformacoesDosFuncionariosDTO>> buscarTodosFuncionarios(){
        return ResponseEntity.status(200).body(this.funcionarioService.buscarTodosFuncionarios());
    }

    @PostMapping("/alterar-informacoes-servidor/{idFuncionario}")
    public ResponseEntity<Object> alterarInformacoesFuncionario(@RequestBody AlterarFuncionarioDTO alterarFuncionarioDTO, @PathVariable Long idFuncionario){
        this.funcionarioService.alterarInformacoesFuncionario(alterarFuncionarioDTO ,idFuncionario);
        return ResponseEntity.ok(200);
    }
}
