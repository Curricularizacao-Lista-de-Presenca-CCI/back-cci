package com.fema.curricularizacao.services;

import com.fema.curricularizacao.DTO.*;
import com.fema.curricularizacao.form.StatusForm;
import com.fema.curricularizacao.models.Funcionario;
import com.fema.curricularizacao.repositories.FuncionarioRepository;
import com.fema.curricularizacao.utils.exceptions.custom.EmailOuSenhaInvalidos;
import com.fema.curricularizacao.utils.exceptions.custom.ObjetoNaoEncontradoException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            throw new EmailOuSenhaInvalidos("Email ou senha inválidos");
        }
        Funcionario funcionario = funcionarioOpt.get();

        if(funcionario.getAtivo() == null || !funcionario.getAtivo()) {
            throw new EmailOuSenhaInvalidos("Usuário inativo. Entre em contato com o coordenador.");
        }

        if(passwordEncoder.matches(senhaUsuario, funcionario.getSenha())) {
            return funcionario;
        }
        throw new EmailOuSenhaInvalidos("Email ou senha inválidos");
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
        return FuncionariosAtivosDTO.converter(listaFuncionarios)
                .stream().sorted(Comparator.comparing(FuncionariosAtivosDTO::getIdFuncionario))
                .collect(Collectors.toList());
    }

    public List<InformacoesDosFuncionariosDTO> buscarTodosFuncionarios() {
        List<Funcionario> encontrarFuncionarios = this.funcionarioRepository.findAll();
        return InformacoesDosFuncionariosDTO.converter(encontrarFuncionarios)
                .stream().sorted(Comparator.comparing(InformacoesDosFuncionariosDTO::getId))
                .collect(Collectors.toList());
    }

    @Transactional
    public void alterarInformacoesFuncionario(AlterarFuncionarioDTO alterarFuncionarioDTO, Long idFuncionario) {
        Funcionario funcionarEncontrado = this.funcionarioRepository.findById(idFuncionario)
                .orElseThrow(()-> new ObjetoNaoEncontradoException("Não foi encontrado nenhum funcionário com id: " + idFuncionario));
        funcionarEncontrado.setNome(alterarFuncionarioDTO.getNome());
        funcionarEncontrado.setEmail(alterarFuncionarioDTO.getEmail());
        funcionarEncontrado.setAtuacao(alterarFuncionarioDTO.getAtuacao());
        funcionarioRepository.save(funcionarEncontrado);
    }

    @Transactional
    public void alterarStatusFuncionario(StatusForm statusFuncionario) {
        Funcionario funcionarioEncontrado = this.funcionarioRepository.findById(statusFuncionario.getIdFuncionario())
                .orElseThrow(()-> new ObjetoNaoEncontradoException("Não foi encontrado nenhum funcionario com o id: " + statusFuncionario.getIdFuncionario()));
        funcionarioEncontrado.setAtivo(statusFuncionario.getStatusFuncionario());
        funcionarioRepository.save(funcionarioEncontrado);
    }
}
