🚀 Configuração do Ambiente de Desenvolvimento

Este guia rápido vai te ajudar a configurar o banco de dados PostgreSQL para rodar este projeto na sua máquina. Vamos lá!

✅ Pré-requisitos
Antes de começar, você só precisa ter duas coisas instaladas:

🐘 PostgreSQL 17: O nosso sistema de banco de dados.

👨‍💻 pgAdmin: Uma ferramenta visual para gerenciar o banco de forma fácil.

🔧 Configuração da Conexão
A aplicação já sabe como se conectar ao banco. As credenciais estão no arquivo application.properties e são as seguintes:

Host: localhost

Porta: 5433

Banco de Dados: postgres

Usuário: postgres

🔑 Senha: 123

Atenção: Verifique se o seu ambiente PostgreSQL local está configurado com essas mesmas informações!

🏗️ Criação das Tabelas

Execução Manual do Script

Abra o pgAdmin e conecte-se ao seu servidor.

Encontre o banco postgres, clique com o botão direito e abra a Query Tool.

Copie e cole o script SQL abaixo.

Execute o script clicando no ícone de ▶️ (Play) ou pressionando F5.

Script SQL
SQL

-- Cria a tabela 'funcionario'
CREATE TABLE funcionario (
    funcionario_id INT PRIMARY KEY,
    atuacao_enum VARCHAR(1),
    nome VARCHAR(150),
    email_usuario VARCHAR(50),
    senha VARCHAR(50)
);

-- Cria a tabela 'evento'
CREATE TABLE evento (
    evento_id INT PRIMARY KEY,
    funcionario_id INT,
    titulo VARCHAR(50),
    data_2 DATE,
    local_2 VARCHAR(150),
    uuid_arquivo VARCHAR(150),
    FOREIGN KEY (funcionario_id) REFERENCES funcionario(funcionario_id)
);

-- Cria a tabela 'lista_de_presenca'
CREATE TABLE lista_de_presenca (
    evento_id INT,
    nome_aluno VARCHAR(50),
    presenca_enum CHAR(1),
    PRIMARY KEY (evento_id, nome_aluno),
    FOREIGN KEY (evento_id) REFERENCES evento(evento_id)
);

🎉 Tudo Pronto!
Com o banco de dados configurado, você já pode rodar a aplicação sem problemas.
