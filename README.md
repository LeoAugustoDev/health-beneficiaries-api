# Health Beneficiaries API

API REST desenvolvida em Java com Spring Boot para gerenciamento de beneficiários de um plano de saúde e seus documentos associados.

Este projeto foi desenvolvido como parte de uma avaliação para Desenvolvedor Backend Java. A aplicação tem como objetivo manter o cadastro de beneficiários de um plano de saúde, permitindo cadastrar beneficiários junto com seus documentos, listar beneficiários cadastrados, listar documentos de um beneficiário, atualizar dados cadastrais e remover beneficiários.

---

# Tecnologias Utilizadas

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

---

# Funcionalidades

- Cadastrar um beneficiário junto com seus documentos
- Listar todos os beneficiários cadastrados
- Listar todos os documentos de um beneficiário a partir do seu ID
- Atualizar os dados cadastrais de um beneficiário
- Remover um beneficiário
- Validar dados de entrada com Bean Validation
- Retornar mensagens claras e padronizadas em caso de erro
- Documentar os endpoints com Swagger

---

# Arquitetura do Projeto

O projeto foi organizado em camadas para manter separação de responsabilidades, facilitar manutenção e permitir evolução da aplicação.

```
controller
service
repository
entity
dto
mapper
exception
config
```

### Descrição das camadas

- **controller** → responsável por expor os endpoints REST.
- **service** → responsável pelas regras de negócio.
- **repository** → acesso ao banco de dados utilizando Spring Data JPA.
- **entity** → entidades JPA.
- **dto** → objetos de entrada e saída da API.
- **mapper** → conversão entre entidades e DTOs.
- **exception** → exceções customizadas e tratamento global.
- **config** → configurações da aplicação.

---

# Modelo de Domínio

A aplicação possui duas entidades principais:

## Beneficiario

Campos:

- id
- nome
- telefone
- email
- dataNascimento
- documentos

## Documento

Campos:

- id
- tipo
- numero
- beneficiario

---

# Relacionamento Entre Entidades

Foi utilizado JPA da seguinte forma:

- `Beneficiario` → `@OneToMany`
- `Documento` → `@ManyToOne`
- `cascade = CascadeType.ALL`
- `orphanRemoval = true`

---

# Endpoints da API

**Base URL**

```text
http://localhost:8080/health-beneficiaries/api
```

## Cadastrar Beneficiário

```http
POST /beneficiarios
```

### Request

```json
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
```

### Response

```json
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
```

---

## Listar Beneficiários

```http
GET /beneficiarios
```

---

## Listar Documentos de um Beneficiário

```http
GET /beneficiarios/{id}/documentos
```

---

## Atualizar Beneficiário

```http
PUT /beneficiarios/{id}
```

### Request

```json
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
```

---

## Remover Beneficiário

```http
DELETE /beneficiarios/{id}
```

### Retorno

```http
204 No Content
```

---

# Validações

A API utiliza Bean Validation para validar os dados recebidos.

Validações implementadas:

- Nome obrigatório
- Telefone obrigatório
- Email obrigatório e válido
- Data de nascimento obrigatória e no passado
- Lista de documentos obrigatória
- Pelo menos um documento
- Tipo do documento obrigatório
- Número do documento obrigatório

---

# Tratamento de Erros

Foi implementado tratamento global utilizando:

```
@RestControllerAdvice
```

Exceções customizadas:

- ResourceNotFoundException
- BusinessException

## Exemplo de erro

```json
{
  "message": "Beneficiario nao encontrado",
  "status": 404,
  "timestamp": "2026-07-06T15:30:00-03:00"
}
```

## Exemplo de erro de validação

```json
{
  "message": "Erro de validacao",
  "status": 400,
  "timestamp": "2026-07-06T15:30:00-03:00",
  "errors": {
    "email": "Email deve ser valido",
    "nome": "Nome e obrigatorio"
  }
}
```

---

# Banco de Dados

A aplicação utiliza PostgreSQL.

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

---

# Executando com Docker

Subir o banco:

```bash
docker compose up -d
```

Configuração:

| Campo | Valor |
|-------|-------|
| Database | health_beneficiaries |
| Usuário | health |
| Senha | health |
| Porta | 5432 |

Parar o banco:

```bash
docker compose down
```

---

# Como Executar a Aplicação

## Pré-requisitos

- Java 21+
- Docker
- Docker Compose
- Maven

### 1. Subir o banco

```bash
docker compose up -d
```

### 2. Executar a aplicação

Linux/macOS

```bash
./mvnw spring-boot:run
```

Windows

```bash
mvnw.cmd spring-boot:run
```

A aplicação estará disponível em:

```text
http://localhost:8080/health-beneficiaries/api
```

---

# Swagger

Após iniciar a aplicação, acesse:

```text
http://localhost:8080/health-beneficiaries/api/swagger-ui.html
```

---

# Testes

Foram desenvolvidos testes unitários utilizando:

- JUnit 5
- Mockito

### Cenários

- Criar beneficiário com sucesso
- Impedir criação com email já cadastrado
- Atualizar beneficiário inexistente
- Deletar beneficiário com sucesso
- Deletar beneficiário inexistente

Executar testes:

Linux/macOS

```bash
./mvnw test
```

Windows

```bash
mvnw.cmd test
```

---

# Build

Gerar o artefato:

Linux/macOS

```bash
./mvnw clean package
```

Windows

```bash
mvnw.cmd clean package
```

O arquivo `.jar` será gerado em:

```text
target/
```

---

# Autenticação

A autenticação foi definida como **opcional** no enunciado da avaliação.

Nesta implementação, foi priorizada a entrega completa das funcionalidades obrigatórias:

- CRUD completo
- Persistência com PostgreSQL
- Validações
- Tratamento global de erros
- Swagger
- Testes unitários

---

# Considerações Finais

A aplicação foi desenvolvida seguindo boas práticas de desenvolvimento backend com Java e Spring Boot, adotando arquitetura em camadas, separação de responsabilidades, validação de entrada, tratamento centralizado de exceções e documentação dos endpoints.

O projeto foi estruturado para facilitar futuras evoluções, como:

- Autenticação JWT
- Paginação
- Filtros de busca
- Auditoria de criação e atualização
- Deploy em ambiente cloud
