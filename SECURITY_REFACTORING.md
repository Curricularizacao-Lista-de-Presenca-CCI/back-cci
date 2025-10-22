# Refatoração da Camada de Segurança

## Resumo das Mudanças

Este documento descreve a refatoração completa da camada de segurança do sistema de curricularização, implementando um sistema robusto de autenticação JWT (JSON Web Token) com Spring Security.

## Componentes Implementados

### 1. SecurityFilter (Novo)
**Arquivo:** `src/main/java/com/fema/curricularizacao/auth/SecurityFilter.java`

Filtro de segurança que intercepta todas as requisições HTTP para validar tokens JWT.

**Funcionalidades:**
- Extrai o token JWT do header `Authorization`
- Valida o token usando o `TokenService`
- Autentica o usuário no contexto de segurança do Spring
- Permite requisições sem token (endpoints públicos continuam acessíveis)

### 2. UserDetailsServiceImpl (Novo)
**Arquivo:** `src/main/java/com/fema/curricularizacao/auth/UserDetailsServiceImpl.java`

Implementação do `UserDetailsService` do Spring Security para carregar informações de usuário.

**Funcionalidades:**
- Carrega funcionários do banco de dados pelo email
- Integra com o sistema de autenticação do Spring Security

### 3. Funcionario (Atualizado)
**Arquivo:** `src/main/java/com/fema/curricularizacao/models/Funcionario.java`

A entidade `Funcionario` agora implementa `UserDetails` do Spring Security.

**Mudanças:**
- Implementa interface `UserDetails`
- Define authorities baseadas no enum `Atuacao` (ROLE_COORDENADOR, ROLE_PROFESSOR)
- Implementa métodos de validação de conta (não expirada, não bloqueada, etc.)

### 4. SecurityConfig (Atualizado)
**Arquivo:** `src/main/java/com/fema/curricularizacao/auth/SecurityConfig.java`

Configuração de segurança atualizada para usar JWT.

**Mudanças:**
- Adiciona o `SecurityFilter` antes do `UsernamePasswordAuthenticationFilter`
- Configura o `AuthenticationManager` para uso no sistema
- Mantém endpoints públicos: login, cadastro e busca de funcionário

### 5. TokenService (Atualizado)
**Arquivo:** `src/main/java/com/fema/curricularizacao/auth/TokenService.java`

Serviço de geração e validação de tokens JWT atualizado para usar API moderna.

**Mudanças:**
- Remove uso de APIs deprecadas (`SignatureAlgorithm`, `setIssuer`, etc.)
- Usa métodos modernos da biblioteca JJWT (`issuer()`, `subject()`, etc.)
- Mantém compatibilidade com tokens já existentes

### 6. Configuração de Testes (Novo)
**Arquivo:** `src/test/resources/application.properties`

Configuração específica para testes usando banco H2 em memória.

**Funcionalidades:**
- Evita necessidade de PostgreSQL em ambiente de testes
- Usa H2 com criação automática de schema (`create-drop`)

### 7. SecurityIntegrationTest (Novo)
**Arquivo:** `src/test/java/com/fema/curricularizacao/auth/SecurityIntegrationTest.java`

Suite completa de testes de integração para validar a segurança.

**Testes:**
- ✅ Acesso a endpoints públicos sem autenticação
- ✅ Negação de acesso a endpoints protegidos sem token
- ✅ Acesso permitido com token válido
- ✅ Negação de acesso com token inválido
- ✅ Cadastro sem autenticação
- ✅ Login sem autenticação

## Fluxo de Autenticação

### 1. Cadastro de Funcionário
```
POST /funcionario/cadastrar
{
  "nome": "João Silva",
  "email": "joao@fema.com",
  "senha": "senha123",
  "atuacao": "COORDENADOR"
}
```
- Senha é criptografada com BCrypt
- Não requer autenticação

### 2. Login
```
POST /funcionario/login
{
  "email": "joao@fema.com",
  "senha": "senha123"
}
```
Resposta:
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@fema.com",
  "atuacao": "COORDENADOR",
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 3. Acessar Endpoints Protegidos
```
GET /api/recurso-protegido
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

## Endpoints Públicos
- `POST /funcionario/login` - Login
- `POST /funcionario/cadastrar` - Cadastro
- `GET /funcionario/buscar/{email}` - Buscar funcionário

## Endpoints Protegidos
Todos os outros endpoints requerem autenticação via JWT.

## Roles/Perfis
- `ROLE_COORDENADOR` - Coordenadores
- `ROLE_PROFESSOR` - Professores

## Configurações JWT
**Arquivo:** `src/main/resources/application.properties`
```properties
jwt.secret=Ch4v3Sup3rM3g4Gr4nd3S3gur41nqu3br4v3lD4Curr2cul4r1z4c40
jwt.expiration=3600000  # 1 hora
```

## Segurança
- ✅ Senhas criptografadas com BCrypt
- ✅ Tokens JWT assinados com HMAC-SHA256
- ✅ Sessões stateless (sem armazenamento de estado no servidor)
- ✅ CSRF desabilitado (apropriado para API REST stateless)
- ✅ Validação automática de tokens em todas as requisições

## Testes
Todos os testes passam com sucesso:
```
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

## Compatibilidade
- ✅ Mantém compatibilidade com código existente
- ✅ Não quebra funcionalidades anteriores
- ✅ Controllers e Services não precisam de alterações
- ✅ Banco de dados não precisa de alterações

## Próximos Passos Recomendados (Opcional)
1. Implementar refresh tokens para melhor experiência do usuário
2. Adicionar rate limiting para prevenir ataques de força bruta
3. Implementar log de auditoria de tentativas de login
4. Adicionar testes de segurança específicos por role
5. Configurar CORS adequadamente para ambientes de produção
