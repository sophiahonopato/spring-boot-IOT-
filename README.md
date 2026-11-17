# API de Livros — Spring Boot 2

API REST para gerenciamento de livros, desenvolvida como projeto prático do curso **Spring Boot Essential 2** (Cursa).

## Tecnologias
- Java 11 · Spring Boot 2.7.18
- Spring Web, Spring Data JPA, Spring Validation, Spring Security, Spring Actuator
- MySQL 8 (Docker) · Lombok
- JUnit 5, Mockito, MockMvc, H2 (somente testes)

## Arquitetura
```
controller  → endpoints REST
service     → regras de negócio
repository  → acesso a dados (Spring Data JPA)
model       → entidade Livro (com Bean Validation)
exception   → GlobalExceptionHandler (@RestControllerAdvice) + ApiError
config      → SecurityConfig (HTTP Basic, usuários em memória)
```

## Como executar
Pré-requisitos: JDK 11+, Maven 3.8+ e Docker.

```bash
# 1. Subir o MySQL em container
docker compose up -d

# 2. Executar a aplicação
mvn spring-boot:run
```
A API fica disponível em `http://localhost:8080`.

## Autenticação (HTTP Basic)
| Usuário | Senha    | Perfil | Permissões             |
|---------|----------|--------|------------------------|
| user    | user123  | USER   | somente GET            |
| admin   | admin123 | ADMIN  | GET, POST, PUT, DELETE |

## Endpoints
| Método | Rota               | Descrição          | Perfil  | Status         |
|--------|--------------------|--------------------|---------|----------------|
| GET    | `/api/livros`      | Lista livros       | USER+   | 200            |
| GET    | `/api/livros/{id}` | Busca por ID       | USER+   | 200 / 404      |
| POST   | `/api/livros`      | Cria livro         | ADMIN   | 201 / 400      |
| PUT    | `/api/livros/{id}` | Atualiza livro     | ADMIN   | 200 / 400 / 404|
| DELETE | `/api/livros/{id}` | Remove livro       | ADMIN   | 204 / 404      |
| GET    | `/actuator/health` | Saúde da aplicação | público | 200            |

### Exemplo
```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Dom Casmurro","autor":"Machado de Assis","anoPublicacao":1899,"preco":29.9}'
```

### Formato de erro
```json
{
  "timestamp": "2026-11-17T10:00:00",
  "status": 400,
  "erro": "Bad Request",
  "mensagens": ["titulo: O título é obrigatório"]
}
```

## Testes
```bash
mvn test
```
- `LivroServiceTest`: 3 testes unitários (Mockito) da camada Service.
- `LivroControllerIT`: 5 testes de integração (MockMvc + H2 + Security).

Relatórios em `target/surefire-reports/`.
# spring-boot-IOT-
