# Health Beneficiaries API

API REST desenvolvida em Java com Spring Boot para gerenciamento de beneficiários de um plano de saúde e seus documentos associados.

Este projeto foi desenvolvido como parte de uma avaliação para Desenvolvedor Backend Java. A aplicação tem como objetivo manter o cadastro de beneficiários de um plano de saúde, permitindo cadastrar beneficiários junto com seus documentos, listar beneficiários cadastrados, listar documentos de um beneficiário, atualizar dados cadastrais e remover beneficiários.

## Tecnologias Utilizadas

- Java 21+
- Spring Boot
- Spring Web
- Spring Data JPA
- Bean Validation
- PostgreSQL
- Lombok
- SpringDoc OpenAPI / Swagger
- JUnit 5
- Mockito
- Docker Compose

## Funcionalidades

- Cadastrar um beneficiário junto com seus documentos
- Listar todos os beneficiários cadastrados
- Listar todos os documentos de um beneficiário a partir do seu ID
- Atualizar os dados cadastrais de um beneficiário
- Remover um beneficiário
- Validar dados de entrada com Bean Validation
- Retornar mensagens claras e padronizadas em caso de erro
- Documentar os endpoints com Swagger

## Arquitetura Do Projeto

O projeto foi organizado em camadas para manter separação de responsabilidades, facilitar manutenção e permitir evolução da aplicação.

- `controller`: camada responsável por expor os endpoints REST.
- `service`: camada responsável por orquestrar os casos de uso e aplicar regras de negócio da aplicação.
- `repository`: camada responsável pelo acesso ao banco de dados usando Spring Data JPA.
- `entity`: camada com as entidades JPA do domínio.
- `dto`: objetos usados para entrada e saída de dados da API.
- `mapper`: camada responsável por converter entidades em DTOs e DTOs em entidades.
- `exception`: camada responsável por exceções customizadas e tratamento global de erros.
- `config`: camada de configurações da aplicação, incluindo Swagger/OpenAPI.

## Modelo De Domínio

A aplicação possui duas entidades principais: `Beneficiario` e `Documento`.

Um beneficiário pode possuir vários documentos, e cada documento pertence a um único beneficiário.

### Beneficiario

Campos principais:

- `id`
- `nome`
- `telefone`
- `email`
- `dataNascimento`
- `documentos`

### Documento

Campos principais:

- `id`
- `tipo`
- `numero`
- `beneficiario`

## Relacionamento Entre Entidades

O relacionamento entre beneficiário e documento foi implementado com JPA da seguinte forma:

- `Beneficiario` possui relacionamento `@OneToMany` com `Documento`
- `Documento` possui relacionamento `@ManyToOne` com `Beneficiario`
- Foi utilizado `cascade = CascadeType.ALL` para persistir documentos junto com o beneficiário
- Foi utilizado `orphanRemoval = true` para remover documentos órfãos quando necessário

## Endpoints Da API

Base URL local:

```text
http://localhost:8080/health-beneficiaries/api
Cadastrar Beneficiário
POST /beneficiarios
Exemplo de request:

{
  "nome": "Maria Silva",
  "telefone": "11999999999",
  "email": "maria.silva@email.com",
  "dataNascimento": "1990-05-20",
  "documentos": [
    {
      "tipo": "CPF",
      "numero": "12345678900"
    }
  ]
}
Exemplo de response:

{
  "id": 1,
  "nome": "Maria Silva",
  "telefone": "11999999999",
  "email": "maria.silva@email.com",
  "dataNascimento": "1990-05-20",
  "documentos": [
    {
      "tipo": "CPF",
      "numero": "12345678900"
    }
  ]
}
Listar Beneficiários
GET /beneficiarios
Listar Documentos De Um Beneficiário
GET /beneficiarios/{id}/documentos
Atualizar Beneficiário
PUT /beneficiarios/{id}
Exemplo de request:

{
  "nome": "Maria Silva Atualizada",
  "telefone": "11988888888",
  "email": "maria.atualizada@email.com",
  "dataNascimento": "1990-05-20",
  "documentos": [
    {
      "tipo": "CPF",
      "numero": "12345678900"
    },
    {
      "tipo": "RG",
      "numero": "123456789"
    }
  ]
}
Remover Beneficiário
DELETE /beneficiarios/{id}
Retorno esperado:

204 No Content
Validações
A API utiliza Bean Validation para validar os dados recebidos nas requisições.

Exemplos de validações aplicadas:

Nome obrigatório
Telefone obrigatório
Email obrigatório e em formato válido
Data de nascimento obrigatória e no passado
Lista de documentos obrigatória
Ao menos um documento deve ser informado
Tipo do documento obrigatório
Número do documento obrigatório
Tratamento De Erros
A aplicação possui tratamento global de erros com @RestControllerAdvice.

Foram criadas exceções customizadas para tornar os retornos mais claros:

ResourceNotFoundException
BusinessException
Exemplo de erro para recurso não encontrado:

{
  "message": "Beneficiario nao encontrado",
  "status": 404,
  "timestamp": "2026-07-06T15:30:00-03:00"
}
Exemplo de erro de validação:

{
  "message": "Erro de validacao",
  "status": 400,
  "timestamp": "2026-07-06T15:30:00-03:00",
  "errors": {
    "email": "Email deve ser valido",
    "nome": "Nome e obrigatorio"
  }
}
Banco De Dados
A aplicação utiliza PostgreSQL como banco de dados relacional.

As configurações estão no arquivo application.yaml.

Configuração padrão:

spring:
  datasource:
    url: ${DATABASE_URL:jdbc:postgresql://localhost:5432/health_beneficiaries}
    username: ${DATABASE_USERNAME:health}
    password: ${DATABASE_PASSWORD:health}
  jpa:
    hibernate:
      ddl-auto: ${JPA_DDL_AUTO:update}
    show-sql: ${JPA_SHOW_SQL:true}
Executando Com Docker
O projeto possui um arquivo docker-compose.yml para subir o PostgreSQL localmente.

Para iniciar o banco:

docker compose up -d
Configuração do banco:

Database: health_beneficiaries
User: health
Password: health
Port: 5432
Para parar o banco:

docker compose down
Como Executar A Aplicação
Pré-requisitos
Java 21 ou superior
Docker e Docker Compose
Maven ou Maven Wrapper
Passo 1: Subir O Banco De Dados
docker compose up -d
Passo 2: Executar A Aplicação
No Linux ou macOS:

./mvnw spring-boot:run
No Windows:

mvnw.cmd spring-boot:run
A aplicação ficará disponível em:

http://localhost:8080/health-beneficiaries/api
Swagger
A documentação dos endpoints foi feita com SpringDoc OpenAPI.

Após iniciar a aplicação, acesse:

http://localhost:8080/health-beneficiaries/api/swagger-ui.html
Testes
Foram criados testes unitários para a camada de service utilizando JUnit 5 e Mockito.

Cenários testados:

Criar beneficiário com sucesso
Impedir criação com email já cadastrado
Erro ao atualizar beneficiário inexistente
Deletar beneficiário com sucesso
Erro ao deletar beneficiário inexistente
Para executar os testes:

No Linux ou macOS:

./mvnw test
No Windows:

mvnw.cmd test
Build
Para gerar o build da aplicação:

No Linux ou macOS:

./mvnw clean package
No Windows:

mvnw.cmd clean package
O artefato .jar será gerado no diretório:

target/
Autenticação
A autenticação/autorização foi indicada como opcional no enunciado da avaliação. Nesta implementação, o foco foi a entrega completa das funcionalidades obrigatórias da API REST, persistência relacional, validações, tratamento de erros, documentação Swagger e testes unitários.

Considerações Finais
A aplicação foi desenvolvida seguindo boas práticas de backend com Java e Spring Boot, mantendo código limpo, responsabilidades bem definidas entre camadas, validação de entrada, tratamento centralizado de erros e documentação dos endpoints.

O projeto está preparado para evolução, permitindo futuras melhorias como autenticação JWT, paginação, filtros de busca, auditoria de datas de inclusão/atualização e deploy em ambiente cloud. `
