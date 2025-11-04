package com.fema.curricularizacao.services;

import com.fema.curricularizacao.DTO.FuncionarioFormDto;
import com.fema.curricularizacao.DTO.FuncionariosAtivosDTO;
import com.fema.curricularizacao.models.Funcionario;
import com.fema.curricularizacao.repositories.FuncionarioRepository;
import com.fema.curricularizacao.utils.exceptions.custom.ObjetoNaoEncontradoException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final PasswordEncoder passwordEncoder;
    private final FuncionarioRepository funcionarioRepository;
    public FuncionarioService(FuncionarioRepository funcionarioRepository, PasswordEncoder passwordEncoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Funcionario buscarFuncionarioPorEmail(String email) {
        return funcionarioRepository.findByEmail(email).orElse(null);
    }

    public Funcionario autenticar(String email, String senhaUsuario) {
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findByEmail(email);
        if(funcionarioOpt.isEmpty()) {
            return null;
        }
        Funcionario funcionario = funcionarioOpt.get();
        if(passwordEncoder.matches(senhaUsuario, funcionario.getSenha())) {
            return funcionario;
        }
        return null;
    }

    @Transactional
    public void cadastrarFuncionario(FuncionarioFormDto form) {
        if(funcionarioRepository.findByEmail(form.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        Funcionario funcionario = new Funcionario();
        form.converterFormParaFuncionario(funcionario);
        String senhaCriptografada = passwordEncoder.encode(funcionario.getSenha());
        funcionario.setSenha(senhaCriptografada);
        this.funcionarioRepository.save(funcionario);
    }

    public List<FuncionariosAtivosDTO> buscarTodosOsFuncionariosAtivos() {
        List<Funcionario> listaFuncionarios = this.funcionarioRepository.findAllByAtivo(true);
        if (listaFuncionarios.isEmpty()){
            throw new ObjetoNaoEncontradoException("Não foi encontrado nenhum funcionário ativo.");
        }
        return FuncionariosAtivosDTO.converter(listaFuncionarios);
    }
}
