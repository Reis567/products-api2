# Products API

Bem-vindo à Products API! Esta API fornece funcionalidades para gerenciar produtos, autenticação de usuários e muito mais.

## Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- [Java 17](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Banco de Dados (ex: PostgreSQL, H2, MySQL)](https://www.postgresql.org/download/)
- [Postman](https://www.postman.com/downloads/) ou qualquer outra ferramenta para testar APIs

## Configuração do Banco de Dados

1. Crie um banco de dados no seu sistema de gerenciamento de banco de dados (por exemplo, PostgreSQL) para ser usado pela aplicação.
2. Atualize as configurações do banco de dados no arquivo `application.properties` ou `application.yml` no diretório `src/main/resources` com as informações do seu banco de dados.

```properties
# Exemplo para PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco_de_dados
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

## Executando a Aplicação

1. Abra um terminal e navegue até o diretório do projeto.
2. Execute o seguinte comando para compilar e iniciar a aplicação:

```bash
mvn spring-boot:run
```

A aplicação estará acessível em `http://localhost:8080`.

## Endpoints

### Autenticação

#### `POST /auth/login`

- Realiza a autenticação do usuário.

#### `POST /auth/register`

- Registra um novo usuário.

### Produtos

#### `GET /product`

- Obtém todos os produtos.

#### `GET /product/{id}`

- Obtém detalhes de um produto específico.

#### `POST /product`

- Cria um novo produto. (Requer autenticação de administrador)

#### `PUT /product/{id}`

- Atualiza um produto existente. (Requer autenticação)

#### `DELETE /product/{id}`

- Deleta um produto existente. (Requer autenticação)

## Autenticação JWT

A API usa autenticação JWT para proteger endpoints sensíveis. Certifique-se de incluir o token JWT válido no cabeçalho `Authorization` para acessar recursos autenticados.

Exemplo de cabeçalho para enviar o token JWT:
```
Authorization: Bearer {seu_token_jwt_aqui}
```

## Papéis (Roles)

- **ROLE_USER:** Permite acesso básico aos recursos da API.
- **ROLE_ADMIN:** Necessário apenas para criar novos produtos.

## Exemplos de Requisições

### Autenticação

#### Login

```bash
curl -X POST \
  http://localhost:8080/auth/login \
  -H 'Content-Type: application/json' \
  -d '{
    "login": "seu_usuario",
    "password": "sua_senha"
  }'
```

#### Registro

```bash
curl -X POST \
  http://localhost:8080/auth/register \
  -H 'Content-Type: application/json' \
  -d '{
    "login": "novo_usuario",
    "password": "nova_senha",
    "role": "USER"
  }'
```

### Produtos

#### Listar Produtos

```bash
curl -X GET \
  http://localhost:8080/product
```

#### Criar Produto

```bash
curl -X POST \
  http://localhost:8080/product \
  -H 'Authorization: Bearer {seu_token_jwt}' \
  -H 'Content-Type: application/json' \
  -d '{
    "name": "Nome do Produto",
    "price_in_cents": 1000
  }'
```

#### Atualizar Produto

```bash
curl -X PUT \
  http://localhost:8080/product/{id_do_produto} \
  -H 'Authorization: Bearer {seu_token_jwt}' \
  -H 'Content-Type: application/json' \
  -d '{
    "name": "Novo Nome do Produto",
    "price_in_cents": 1200
  }'
```

#### Deletar Produto

```bash
curl -X DELETE \
  http://localhost:8080/product/{id_do_produto} \
  -H 'Authorization: Bearer {seu_token_jwt}'
```

