## Manual do Usuário 

## 🛠️ Tecnologias Utilizadas

Este projeto foi construído utilizando as seguintes tecnologias:

* **Java 21**
* **Spring Boot** (para a API REST)
* **Maven** (para gerenciamento de dependências)
* **PostgreSQL 17** (para o banco de dados)

---

## 🚀 Começando

Este guia irá te auxiliar a configurar e executar o back-end do projeto em sua máquina local.

### 1. Pré-requisitos

Antes de começar, garanta que você tenha as seguintes ferramentas instaladas e configuradas:

* **JDK 21** (Java Development Kit)
* **Apache Maven**
* **PostgreSQL 17**
* **pgAdmin** (ou qualquer outro cliente SQL de sua preferência)

### 2. Clonando o Repositório

Primeiro, clone este repositório para sua máquina local:

```bash
git clone (https://github.com/Curricularizacao-Lista-de-Presenca-CCI/back-cci.git)
```

### 3. Configuração do Banco de Dados

#### 3.1. Arquivo de Configuração

A aplicação espera se conectar a um banco de dados local. As credenciais estão localizadas no arquivo `src/main/resources/application.properties`.

**Verifique se o seu ambiente PostgreSQL local está configurado com as mesmas informações.**

```properties
# Exemplo de application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=123
```

#### 3.2. Criação das Tabelas (Script Manual)

Você precisará criar as tabelas manualmente antes de iniciar a aplicação.

1.  Abra o **pgAdmin** e conecte-se ao seu servidor PostgreSQL.
2.  Encontre o banco de dados `postgres` (ou o banco que você configurou no `application.properties`).
3.  Clique com o botão direito nele e abra a **Query Tool**.
4.  Copie, cole e execute o script SQL abaixo (pressione `F5` ou clique no ícone ▶️).

```sql
-- Cria a tabela 'funcionario'
CREATE TABLE funcionario (
    funcionario_id SERIAL PRIMARY KEY,
    atuacao_enum VARCHAR(1),
    nome VARCHAR(150),
    email_usuario VARCHAR(50),
    senha VARCHAR(50),
    status_enum VARCHAR(10),
    CONSTRAINT chk_status CHECK (status_enum IN ('ativo', 'inativo'))
);

-- Cria a tabela 'evento' 
CREATE TABLE evento (
    evento_id SERIAL PRIMARY KEY,
    funcionario_id INT,
    titulo VARCHAR(50),
    data_2 DATE,
    local_2 VARCHAR(150),
    arquivo_pdf BYTEA,
    FOREIGN KEY (funcionario_id) REFERENCES funcionario(funcionario_id)
);

-- Cria a tabela 'lista_de_presenca'
CREATE TABLE lista_de_presenca (
     id BIGSERIAL PRIMARY KEY,
    evento_id INT NOT NULL,
    nome_aluno VARCHAR(50) NOT NULL,
    presenca_enum CHAR(1),
    FOREIGN KEY (evento_id) REFERENCES evento(evento_id)
);
```

---

## ▶️ Executando a Aplicação

Com o banco de dados configurado, você pode iniciar o back-end.

### Opção 1: Via Terminal (com Maven)

1.  Abra seu terminal.
2.  Navegue até a pasta raiz do projeto (onde está o arquivo `pom.xml`).
3.  Execute o comando:

    ```bash
    mvn spring-boot:run
    ```

### Opção 2: Via IntelliJ IDEA

1.  **Verifique a SDK do Projeto:**
    * Vá em **File > Project Structure...**
    * Em **Project**, certifique-se de que o **SDK** selecionado é o **Java 21**.

2.  **Encontre a Classe Principal:**
    * Navegue até a classe que contém a anotação `@SpringBootApplication` (geralmente `[NomeDoProjeto]Application.java`).

3.  **Execute:**
    * Clique no ícone verde de "Play" (▶️) ao lado da declaração da classe ou do método `main`.
    * Selecione **Run '...Application.main()'**.

4.  **Verifique o Console:**
    * Aguarde a inicialização. Se tudo der certo, você verá a arte do Spring e a mensagem:
    * `... Started [NomeDoProjeto]Application in ... seconds ...`

Pronto! Sua aplicação está rodando localmente, geralmente em `http://localhost:8080`.
