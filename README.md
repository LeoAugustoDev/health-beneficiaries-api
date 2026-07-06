# Health Beneficiaries API

API REST desenvolvida em Java com Spring Boot para gerenciamento de beneficiários de um plano de saúde e seus documentos associados.

Este projeto foi desenvolvido como parte de uma avaliação para Desenvolvedor Backend Java. A aplicação permite manter o cadastro de beneficiários, cadastrar documentos vinculados, listar beneficiários cadastrados, listar documentos de um beneficiário, atualizar dados cadastrais e remover beneficiários.

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

## Arquitetura do Projeto

O projeto foi organizado em camadas para manter separação de responsabilidades, facilitar manutenção e permitir evolução da aplicação.

```text
src/main/java/.../
├── controller
├── service
├── repository
├── entity
├── dto
├── mapper
├── exception
└── config
```

- `controller`: camada responsável por expor os endpoints REST.
- `service`: camada responsável por orquestrar os casos de uso e aplicar regras de negócio da aplicação.
- `repository`: camada responsável pelo acesso ao banco de dados usando Spring Data JPA.
- `entity`: camada com as entidades JPA do domínio.
- `dto`: objetos usados para entrada e saída de dados da API.
- `mapper`: camada responsável por converter entidades em DTOs e DTOs em entidades.
- `exception`: camada responsável por exceções customizadas e tratamento global de erros.
- `config`: camada de configurações da aplicação, incluindo Swagger/OpenAPI.

## Modelo de Domínio

A aplicação possui duas entidades principais: `Beneficiario` e `Documento`.

Um beneficiário pode possuir vários documentos, e cada documento pertence a um único beneficiário.

### Beneficiario

Campos:

- `id`
- `nome`
- `telefone`
- `dataNascimento`
- `dataInclusao`
- `dataAtualizacao`
- `documentos`

### Documento

Campos:

- `id`
- `tipoDocumento`
- `descricao`
- `dataInclusao`
- `dataAtualizacao`
- `beneficiario`

## Relacionamento entre Entidades

O relacionamento entre beneficiário e documento foi implementado com JPA da seguinte forma:

- `Beneficiario` possui relacionamento `@OneToMany` com `Documento`.
- `Documento` possui relacionamento `@ManyToOne` com `Beneficiario`.
- Foi utilizado `cascade = CascadeType.ALL` para persistir documentos junto com o beneficiário.
- Foi utilizado `orphanRemoval = true` para remover documentos órfãos quando necessário.

## Endpoints da API

Base URL local:

```text
http://localhost:8080/health-beneficiaries/api
```

### Cadastrar Beneficiário

```http
POST /beneficiarios
```

Exemplo de request:

```json
{
  "nome": "Maria Silva",
  "telefone": "11999999999",
  "dataNascimento": "1990-05-20",
  "documentos": [
    {
      "tipoDocumento": "CPF",
      "descricao": "Cadastro de Pessoa Fisica"
    }
  ]
}
```

Exemplo de response:

```json
{
  "id": 1,
  "nome": "Maria Silva",
  "telefone": "11999999999",
  "dataNascimento": "1990-05-20",
  "dataInclusao": "2026-07-06T15:30:00",
  "dataAtualizacao": "2026-07-06T15:30:00",
  "documentos": [
    {
      "tipoDocumento": "CPF",
      "descricao": "Cadastro de Pessoa Fisica",
      "dataInclusao": "2026-07-06T15:30:00",
      "dataAtualizacao": "2026-07-06T15:30:00"
    }
  ]
}
```

### Listar Beneficiários

```http
GET /beneficiarios
```

Retorna todos os beneficiários cadastrados com seus documentos associados.

### Listar Documentos de um Beneficiário

```http
GET /beneficiarios/{id}/documentos
```

Retorna todos os documentos associados ao beneficiário informado.

### Atualizar Beneficiário

```http
PUT /beneficiarios/{id}
```

Exemplo de request:

```json
{
  "nome": "Maria Silva Atualizada",
  "telefone": "11988888888",
  "dataNascimento": "1990-05-20",
  "documentos": [
    {
      "tipoDocumento": "CPF",
      "descricao": "Cadastro de Pessoa Fisica"
    },
    {
      "tipoDocumento": "RG",
      "descricao": "Registro Geral"
    }
  ]
}
```

### Remover Beneficiário

```http
DELETE /beneficiarios/{id}
```

Retorno esperado:

```http
204 No Content
```

## Validações

A API utiliza Bean Validation para validar os dados recebidos nas requisições.

Exemplos de validações aplicadas:

- Nome obrigatório
- Telefone obrigatório
- Data de nascimento obrigatória
- Data de nascimento deve estar no passado
- Lista de documentos obrigatória
- Ao menos um documento deve ser informado
- Tipo do documento obrigatório
- Descrição do documento obrigatória

## Tratamento de Erros

A aplicação possui tratamento global de erros com `@RestControllerAdvice`.

Foram criadas exceções customizadas para tornar os retornos mais claros:

- `ResourceNotFoundException`
- `BusinessException`

Exemplo de erro para recurso não encontrado:

```json
{
  "message": "Beneficiario nao encontrado",
  "status": 404,
  "timestamp": "2026-07-06T15:30:00-03:00"
}
```

Exemplo de erro de validação:

```json
{
  "message": "Erro de validacao",
  "status": 400,
  "timestamp": "2026-07-06T15:30:00-03:00",
  "errors": {
    "nome": "Nome e obrigatorio",
    "dataNascimento": "Data de nascimento deve estar no passado"
  }
}
```

## Banco de Dados

A aplicação utiliza PostgreSQL como banco de dados relacional.

As configurações estão no arquivo `application.yaml`.

Configuração padrão:

```yaml
spring:
  datasource:
    url: ${DATABASE_URL:jdbc:postgresql://localhost:5432/health_beneficiaries}
    username: ${DATABASE_USERNAME:health}
    password: ${DATABASE_PASSWORD:health}
  jpa:
    hibernate:
      ddl-auto: ${JPA_DDL_AUTO:update}
    show-sql: ${JPA_SHOW_SQL:true}
```

## Executando com Docker

O projeto possui um arquivo `docker-compose.yml` para subir o PostgreSQL localmente.

Para iniciar o banco:

```bash
docker compose up -d
```

Configuração do banco:

```text
Database: health_beneficiaries
User: health
Password: health
Port: 5432
```

Para parar o banco:

```bash
docker compose down
```

## Como Executar a Aplicação

### Pré-requisitos

- Java 21 ou superior
- Docker e Docker Compose
- Maven ou Maven Wrapper

### Passo 1: Subir o Banco de Dados

```bash
docker compose up -d
```

### Passo 2: Executar a Aplicação

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080/health-beneficiaries/api
```

## Swagger

A documentação dos endpoints foi feita com SpringDoc OpenAPI.

Após iniciar a aplicação, acesse:

```text
http://localhost:8080/health-beneficiaries/api/swagger-ui.html
```

## Testes

Foram criados testes unitários para a camada de service utilizando JUnit 5 e Mockito.

Cenários testados:

- Criar beneficiário com sucesso
- Atualizar beneficiário com sucesso
- Erro ao atualizar beneficiário inexistente
- Deletar beneficiário com sucesso
- Erro ao deletar beneficiário inexistente

Para executar os testes:

No Linux ou macOS:

```bash
./mvnw test
```

No Windows:

```bash
mvnw.cmd test
```

## Build

Para gerar o build da aplicação:

No Linux ou macOS:

```bash
./mvnw clean package
```

No Windows:

```bash
mvnw.cmd clean package
```

O artefato `.jar` será gerado no diretório:

```text
target/
```

## Autenticação

A autenticação/autorização foi indicada como opcional no enunciado da avaliação. Nesta implementação, o foco foi a entrega completa das funcionalidades obrigatórias da API REST, persistência relacional, validações, tratamento de erros, documentação Swagger e testes unitários.

## Considerações Finais

A aplicação foi desenvolvida seguindo boas práticas de backend com Java e Spring Boot, mantendo código limpo, responsabilidades bem definidas entre camadas, validação de entrada, tratamento centralizado de erros e documentação dos endpoints.

O projeto está preparado para evolução, permitindo futuras melhorias como autenticação JWT, paginação, filtros de busca, auditoria avançada e deploy em ambiente cloud.
