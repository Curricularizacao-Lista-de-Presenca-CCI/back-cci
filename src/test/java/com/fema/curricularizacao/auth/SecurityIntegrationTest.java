package com.fema.curricularizacao.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fema.curricularizacao.DTO.FuncionarioDto;
import com.fema.curricularizacao.DTO.FuncionarioFormDto;
import com.fema.curricularizacao.enums.Atuacao;
import com.fema.curricularizacao.repositories.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    private String jwtToken;

    @BeforeEach
    void setUp() throws Exception {
        // Limpar dados antes de cada teste
        funcionarioRepository.deleteAll();

        // Cadastrar um funcionário de teste
        FuncionarioFormDto formDto = new FuncionarioFormDto();
        formDto.setNome("Teste Funcionario");
        formDto.setEmail("teste@fema.com");
        formDto.setSenha("senha123");
        formDto.setAtuacao(Atuacao.COORDENADOR);

        mockMvc.perform(post("/funcionario/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(formDto)))
                .andExpect(status().isCreated());

        // Fazer login para obter o token
        FuncionarioDto loginDto = new FuncionarioDto(null, null, "teste@fema.com", "senha123", null, null);
        
        MvcResult result = mockMvc.perform(post("/funcionario/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        FuncionarioDto responseDto = objectMapper.readValue(response, FuncionarioDto.class);
        jwtToken = responseDto.getToken();
    }

    @Test
    void devePermitirAcessoAEndpointPublico() throws Exception {
        mockMvc.perform(get("/funcionario/buscar/teste@fema.com"))
                .andExpect(status().isOk());
    }

    @Test
    void deveNegarAcessoSemTokenAEndpointProtegido() throws Exception {
        // Tentando acessar um endpoint protegido sem token
        // Como não há endpoints protegidos explícitos além dos permitidos,
        // este teste verifica que a configuração está correta
        mockMvc.perform(get("/api/protected"))
                .andExpect(status().isForbidden());
    }

    @Test
    void devePermitirAcessoComTokenValido() throws Exception {
        // Buscar funcionário com token válido
        mockMvc.perform(get("/funcionario/buscar/teste@fema.com")
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("teste@fema.com"))
                .andExpect(jsonPath("$.nome").value("Teste Funcionario"));
    }

    @Test
    void deveNegarAcessoComTokenInvalido() throws Exception {
        // Tentando acessar com token inválido
        mockMvc.perform(get("/api/protected")
                .header("Authorization", "Bearer token_invalido"))
                .andExpect(status().isForbidden());
    }

    @Test
    void devePermitirCadastroSemAutenticacao() throws Exception {
        FuncionarioFormDto formDto = new FuncionarioFormDto();
        formDto.setNome("Novo Funcionario");
        formDto.setEmail("novo@fema.com");
        formDto.setSenha("senha456");
        formDto.setAtuacao(Atuacao.PROFESSOR);

        mockMvc.perform(post("/funcionario/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(formDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void devePermitirLoginSemAutenticacao() throws Exception {
        FuncionarioDto loginDto = new FuncionarioDto(null, null, "teste@fema.com", "senha123", null, null);
        
        mockMvc.perform(post("/funcionario/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.email").value("teste@fema.com"));
    }
}

