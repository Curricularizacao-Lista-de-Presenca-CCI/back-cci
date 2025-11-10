🚀 Configuração do Ambiente de Desenvolvimento

Este guia rápido vai te ajudar a configurar o banco de dados PostgreSQL para rodar este projeto na sua máquina. Vamos lá!

✅ Pré-requisitos
Antes de começar, você só precisa ter duas coisas instaladas:

🐘 PostgreSQL 17: O nosso sistema de banco de dados.

👨‍💻 pgAdmin: Uma ferramenta visual para gerenciar o banco de forma fácil.

🔧 Configuração da Conexão
A aplicação já sabe como se conectar ao banco. As credenciais estão no arquivo application.properties:


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
   funcionario_id SERIAL PRIMARY KEY,
   atuacao_enum VARCHAR(1),
   nome VARCHAR(150),
   email_usuario VARCHAR(50),
   senha VARCHAR(50),
   status_enum CHAR(1),
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


🎉 Tudo Pronto!
Com o banco de dados configurado, você já pode rodar a aplicação sem problemas.

Em caso de erro com o tipo de dado UUID, rodar esse comando no banco:
ALTER TABLE evento
ALTER COLUMN uuid_arquivo TYPE uuid USING uuid_arquivo::uuid;
