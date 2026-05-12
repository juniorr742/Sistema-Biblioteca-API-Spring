# Sistema Biblioteca — API Spring Boot

API REST de gerenciamento de biblioteca desenvolvida como projeto de portfólio em três etapas evolutivas, com foco em fundamentos sólidos antes de frameworks — do Java puro ao Spring Boot com autenticação JWT.

---

## Sobre o Projeto

O sistema gerencia o fluxo completo de uma biblioteca: cadastro de livros e usuários, controle de empréstimos com prazo e multa por atraso, e processamento de pagamentos. Cada etapa foi construída do zero, sem aproveitar código da anterior, para demonstrar a evolução do conhecimento.

---

## Evolução do Projeto

### Etapa 1 — Java Puro
**Repositório:** [ProjetoBiblioteca](https://github.com/juniorr742/ProjetoBiblioteca)

Lógica de negócio completa sem frameworks ou banco de dados. Dados armazenados em memória.

- Arquitetura em camadas — model, service, controller, factory, view, config
- Herança e polimorfismo — `Aluno` e `Professor` estendem `Usuario`
- Factory Pattern — `UsuarioFactory` centraliza a criação de objetos
- Injeção de dependência pelo construtor
- SRP — cada classe com uma única responsabilidade
- Encapsulamento com `Collections.unmodifiableList`
- Constantes centralizadas em `BibliotecaConfig`

---

### Etapa 2 — JDBC
**Repositório:** [Sistema-Biblioteca-JDBC](https://github.com/juniorr742/Sistema-Biblioteca-JDBC)

Substituição do armazenamento em memória por persistência real com MySQL.

- `ConnectionFactory` com credenciais externalizadas em `db.properties`
- Padrão DAO com interface genérica `IDao<T>`
- `PreparedStatement` — proteção contra SQL Injection
- `try-with-resources` — fechamento automático de conexões
- IDs gerados pelo banco com `AUTO_INCREMENT` e recuperados via `getGeneratedKeys()`
- Exceções customizadas no lugar de mensagens de console

---

### Etapa 3 — Spring Boot + Spring Security + JWT
**Repositório:** [Sistema-Biblioteca-API-Spring](https://github.com/juniorr742/Sistema-Biblioteca-API-Spring)

Migração para API REST com autenticação. Etapa atual.

**Stack:**
- Java 21
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Data JPA + Hibernate
- Spring Security
- JWT (jjwt 0.12.6)
- MySQL
- JUnit 5 + Mockito

**O que foi implementado:**

- Models com mapeamento JPA — `Livro`, `Usuario` (abstrata), `Aluno`, `Professor`, `Pagamento`, `RegistroEmprestimo`
- Herança com `@Inheritance(SINGLE_TABLE)` e `@DiscriminatorColumn`
- Repositories com métodos customizados — `existsByEmailIgnoreCaseAndIdNot`, `findByTituloContainingIgnoreCase`, query JPQL com JOIN resolvendo N+1
- Services — `LivroService`, `UsuarioService`, `EmprestimoService`, `PagamentoService`, `ValidadorEmprestimo`, `CalculadoraMulta`
- DTOs separados por contexto — `UsuarioCadastroDTO`, `UsuarioAtualizarDTO`, `UsuarioLoginDTO`, `EmprestimoRequestDTO`, `LivroRequestDTO`
- Controllers REST completos com os quatro verbos HTTP
- Exceções customizadas — `NotFoundException` (404), `BusinessException` (400), `GlobalExceptionHandler` centralizando o tratamento
- `@Transactional` em empréstimo e devolução — atomicidade garantida
- **31 testes unitários** — LivroServiceTest (8), UsuarioServiceTest (9), EmprestimoServiceTest (7), ValidadorEmprestimoTest (5), CalculadoraMultaTest (2)
- **Autenticação JWT** — login, geração de token, validação por filtro em cada requisição
- **BCrypt** — senhas nunca armazenadas em texto puro
- `SecurityConfig` — `/auth/login` público, todos os demais endpoints protegidos com Bearer Token

---

## Como Rodar — Etapa 3

### Pré-requisitos

- Java 21
- Maven
- MySQL rodando localmente

### 1. Clone o repositório

```bash
git clone https://github.com/juniorr742/Sistema-Biblioteca-API-Spring.git
cd Sistema-Biblioteca-API-Spring
```

### 2. Crie o banco de dados

```sql
CREATE DATABASE biblioteca;
```

### 3. Configure o `application.properties`

Crie ou edite o arquivo em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=SUA_CHAVE_SECRETA_COM_PELO_MENOS_32_CARACTERES
```

> ⚠️ O `application.properties` está no `.gitignore` — nunca commite credenciais.

### 4. Rode a aplicação

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

---

## Autenticação

Todos os endpoints exigem Bearer Token, exceto o login.

### Login

```http
POST /auth/login
Content-Type: application/json

{
  "email": "usuario@email.com",
  "senha": "senha123"
}
```

Retorna um token JWT. Use ele no header de todas as requisições seguintes:

```
Authorization: Bearer SEU_TOKEN_AQUI
```

---

## Endpoints Principais

### Livros
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/livros` | Lista todos os livros |
| GET | `/livros/{id}` | Busca livro por ID |
| GET | `/livros/busca?titulo=` | Busca por título |
| POST | `/livros` | Cadastra livro |
| PUT | `/livros/{id}` | Atualiza livro |
| DELETE | `/livros/{id}` | Remove livro |

### Usuários
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/usuarios` | Lista todos os usuários |
| GET | `/usuarios/{id}` | Busca usuário por ID |
| GET | `/usuarios/busca?nome=` | Busca por nome |
| POST | `/usuarios` | Cadastra usuário |
| PUT | `/usuarios/{id}` | Atualiza usuário |
| DELETE | `/usuarios/{id}` | Remove usuário |

### Empréstimos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/emprestimos` | Realiza empréstimo |
| PUT | `/emprestimos/devolver/{id}` | Registra devolução |
| GET | `/emprestimos/historico/{idUsuario}` | Histórico do usuário |
| GET | `/emprestimos/ativos/{idUsuario}` | Livros ativos do usuário |

### Pagamentos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/pagamentos/saldo/{idUsuario}` | Consulta saldo devedor |
| POST | `/pagamentos/total/{idUsuario}` | Quita débito total |
| POST | `/pagamentos/parcial/{idUsuario}` | Pagamento parcial |

---

## Decisões de Design

- **DTO como camada de segurança** — o cliente só envia o que o sistema permite; IDs não são expostos no body
- **DTOs separados por contexto** — `Cadastro`, `Atualizar` e `Login` expõem só o necessário para cada operação
- **Histórico de empréstimos imutável** — sem delete e sem update exposto; auditoria por design
- **Stack trace nunca exposto** — `GlobalExceptionHandler` retorna mensagem genérica no 500
- **Validação dupla** — no código (400 claro) e constraint no banco (fallback de integridade)
- **N+1 eliminado** — query JPQL com JOIN explícito em `RegistrosRepository`

---

## Testes

```bash
./mvnw test
```

31 testes unitários cobrindo as regras de negócio principais. Mockito isola as dependências — nenhum teste acessa banco de dados.
