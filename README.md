# # Sistema Biblioteca — API Spring Boot

API REST de gerenciamento de biblioteca desenvolvida como projeto de portfólio em três etapas evolutivas, com foco em fundamentos sólidos antes de frameworks — do Java puro ao Spring Boot com autenticação JWT.

---

## 🚀 Deploy & Documentação

A API está em produção e totalmente funcional na nuvem:

* **Base URL da API:** `https://sistema-biblioteca-api-spring-production.up.railway.app`
* **Documentação Interativa (Swagger UI):** `https://sistema-biblioteca-api-spring-production.up.railway.app/swagger-ui/index.html`

> 🔐 **Nota de Segurança:** O Swagger está configurado para rodar em ambiente de produção utilizando HTTPS. Para testar os endpoints protegidos, siga o guia de autenticação abaixo.

---

## Endpoints

### Autenticação
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/auth/login` | Autentica e retorna token JWT |

### Livros
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/livros` | Lista todos os livros |
| GET | `/livros/{id}` | Busca livro por ID |
| GET | `/livros/buscar/{titulo}` | Busca por título |
| POST | `/livros` | Cadastra livro |
| PUT | `/livros/{id}` | Atualiza livro |
| DELETE | `/livros/{id}` | Remove livro |

### Usuários
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/usuarios` | Lista todos os usuários |
| GET | `/usuarios/{id}` | Busca usuário por ID |
| GET | `/usuarios/buscarNome/{nome}` | Busca por nome |
| POST | `/usuarios` | Cadastra usuário |
| PUT | `/usuarios/{id}` | Atualiza usuário |
| DELETE | `/usuarios/{id}` | Remove usuário |

### Registros de Empréstimo
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/registros` | Histórico completo |
| GET | `/registros/{idTransacao}/buscar` | Busca por ID da transação |
| GET | `/registros/{idUsuario}` | Livros ativos do usuário |
| POST | `/registros` | Realiza empréstimo |
| PUT | `/registros/{idTransacao}` | Devolve livro |

### Pagamentos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/usuarios/pagamento/{id}` | Consulta saldo devedor |
| GET | `/usuarios/pagamento` | Consulta custo fixo de empréstimo |
| PUT | `/usuarios/pagamento/{id}` | Quita débito total |
| PUT | `/usuarios/pagamento/{id}/{valorPago}` | Pagamento parcial |

---

## Como testar a API (Swagger ou Local)

### 1. Gerar o token JWT
Realize uma requisição de login utilizando o endpoint `/auth/login` (pode ser feito diretamente pelo Swagger UI em produção):

```http
POST /auth/login
Content-Type: application/json

{
  "email": "junior@teste.com",
  "senha": "teste@123"
}

2. Autenticar no Swagger UI
Copie o token (hash longa) retornado no corpo da resposta do login.

No topo da página do Swagger, clique no botão Authorize (ícone de cadeado).

No campo Value, cole apenas o token copiado (sem aspas, sem a palavra 'Bearer').

Clique em Authorize e depois em Close.

O Swagger injetará automaticamente o header Authorization: Bearer SEU_TOKEN em todas as requisições protegidas seguintes.

3. Cadastrar um livro
HTTP
POST /livros
Authorization: Bearer SEU_TOKEN
Content-Type: application/json

{
  "titulo": "Clean Code",
  "autor": "Robert C. Martin"
}
Anote o id retornado.

4. Cadastrar um usuário
O tipo pode ser Aluno (limite de 3 livros, saldo R$ 15) ou Professor (limite de 5 livros, saldo R$ 30). O saldo é gerado automaticamente com base no tipo.

HTTP
POST /usuarios
Authorization: Bearer SEU_TOKEN
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "senha123",
  "tipo": "Aluno"
}
Anote o id retornado.

5. Realizar um empréstimo
HTTP
POST /registros
Authorization: Bearer SEU_TOKEN
Content-Type: application/json

{
  "idUsuario": ID_DO_USUARIO,
  "idLivro": ID_DO_LIVRO
}
Anote o idTransacao retornado.

6. Devolver o livro
HTTP
PUT /registros/{idTransacao}
Authorization: Bearer SEU_TOKEN
7. Consultar histórico
HTTP
GET /registros
Authorization: Bearer SEU_TOKEN
O cadastro de usuários é restrito a administradores autenticados por design — a biblioteca controla quem tem acesso ao sistema.

Sobre o Projeto
O sistema gerencia o fluxo completo de uma biblioteca: cadastro de livros e usuários, controle de empréstimos com prazo e multa por atraso, e processamento de pagamentos. Cada etapa foi construída do zero, sem aproveitar código da anterior, para demonstrar a evolução do conhecimento.

Evolução do Projeto
Etapa 1 — Java Puro
Repositório: ProjetoBiblioteca

Lógica de negócio completa sem frameworks ou banco de dados. Dados armazenados em memória.

Arquitetura em camadas — model, service, controller, factory, view, config

Herança e polimorfismo — Aluno e Professor estendem Usuario

Factory Pattern — UsuarioFactory centraliza a criação de objetos

Injeção de dependência pelo construtor

SRP — cada classe com uma única responsabilidade

Encapsulamento com Collections.unmodifiableList

Constantes centralizadas in BibliotecaConfig

Etapa 2 — JDBC
Repositório: Sistema-Biblioteca-JDBC

Substituição do armazenamento em memória por persistência real com MySQL.

ConnectionFactory com credenciais externalizadas em db.properties

Padrão DAO com interface genérica IDao<T>

PreparedStatement — proteção contra SQL Injection

try-with-resources — fechamento automático de conexões

IDs gerados pelo banco com AUTO_INCREMENT e recuperados via getGeneratedKeys()

Exceções customizadas no lugar de mensagens de console

Etapa 3 — Spring Boot + Spring Security + JWT
Repositório: Sistema-Biblioteca-API-Spring

Migração para API REST com autenticação e documentação interativa via Swagger.

Stack:

Java 21

Spring Boot 4.0.6

Spring Web MVC

Spring Data JPA + Hibernate

Spring Security

JWT (jjwt 0.12.6)

MySQL

JUnit 5 + Mockito

SpringDoc OpenAPI (Swagger UI)

Docker

O que foi implementado:

Models com mapeamento JPA — Livro, Usuario (abstrata), Aluno, Professor, Pagamento, Emprestimo

Herança com @Inheritance(SINGLE_TABLE) e @DiscriminatorColumn

Repositories com métodos customizados — existsByEmailIgnoreCaseAndIdNot, findByTituloContainingIgnoreCase, query JPQL com JOIN resolvendo N+1

Services — LivroService, UsuarioService, EmprestimoService, PagamentoService, ValidadorEmprestimo, CalculadoraMulta

DTOs separados por contexto — UsuarioCadastroDTO, UsuarioAtualizarDTO, UsuarioLoginDTO, EmprestimoRequestDTO, LivroRequestDTO, UsuarioResponseDTO, EmprestimoResponseDTO

Controllers REST completos com os quatro verbos HTTP

Exceções customizadas — NotFoundException (404), BusinessException (400), GlobalExceptionHandler centralizando o tratamento

@Transactional em empréstimo e devolução — atomicidade garantida

31 testes unitários — LivroServiceTest (8), UsuarioServiceTest (9), EmprestimoServiceTest (7), ValidadorEmprestimoTest (5), CalculadoraMultaTest (2)

Autenticação JWT — login, geração de token, validação por filtro em cada requisição

BCrypt — senhas nunca armazenadas em texto puro

Swagger UI — documentação interativa com suporte a Bearer Token via SwaggerConfig configurado com suporte multiplataforma (Local e Nuvem via HTTPS)

SecurityConfig — /auth/login e rotas do Swagger públicas, todos os demais endpoints protegidos com Bearer Token

Conceitos Aprendidos
Relacionamentos identificadores vs não-identificadores
No banco de dados, um relacionamento identificador (linha contínua no Workbench) significa que a FK compõe a PK da tabela filha — o filho não tem identidade própria sem o pai. Um relacionamento não-identificador (linha tracejada) significa que a FK existe na tabela filha mas ela tem seu próprio id como PK. Na prática, quase todos os relacionamentos devem ser não-identificadores, exceto tabelas intermediárias N:N como daily_production_employees, que existem apenas para intermediar duas entidades e cuja PK é a composição das duas FKs.

Docker
Docker resolve o problema de "funciona na minha máquina mas não no servidor". A aplicação e tudo que ela precisa para rodar é empacotado em uma imagem — um molde imutável definido no Dockerfile. Essa imagem é executada como um container — um processo isolado com seu próprio ambiente. O servidor só precisa ter Docker instalado, sem Java ou MySQL locais. O docker-compose.yml orquestra múltiplos containers (aplicação + banco), conectados em rede interna, com volumes para persistência dos dados além do ciclo de vida do container.

Swagger / OpenAPI
O SpringDoc OpenAPI lê as anotações @RestController, @GetMapping, @RequestBody etc. e gera automaticamente uma interface visual interativa acessível em /swagger-ui/index.html. Permite explorar e testar endpoints sem Postman. Para funcionar com Spring Security, as rotas /swagger-ui/ e /v3/api-docs/ precisam ser liberadas no SecurityConfig. O suporte a Bearer Token é configurado via SwaggerConfig com SecurityScheme do tipo HTTP Bearer.

Compatibilidade de dependências
NoSuchMethodError em runtime quase sempre indica incompatibilidade de versão entre bibliotecas. O método existia quando o código foi compilado mas não existe na versão carregada em execução. Solução: verificar a matriz de compatibilidade da dependência com a versão do framework principal.

Como Rodar Localmente
Pré-requisitos
Java 21

Maven

MySQL rodando localmente

1. Clone o repositório
Bash
git clone [https://github.com/juniorr742/Sistema-Biblioteca-API-Spring.git](https://github.com/juniorr742/Sistema-Biblioteca-API-Spring.git)
cd Sistema-Biblioteca-API-Spring
2. Crie o banco de dados
SQL
CREATE DATABASE biblioteca_API;
3. Configure o application.properties
Crie o arquivo em src/main/resources/application.properties:

Properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca_API
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=SUA_CHAVE_SECRETA_COM_PELO_MENOS_32_CARACTERES
⚠️ O application.properties está no .gitignore — nunca commite credenciais.

4. Rode a aplicação
Bash
./mvnw spring-boot:run
A API sobe em http://localhost:8080.

Acesse a documentação Swagger em http://localhost:8080/swagger-ui/index.html.

Decisões de Design
Cadastro de usuários restrito — apenas administradores autenticados podem cadastrar usuários

DTO como camada de segurança — o cliente só envia o que o sistema permite; IDs não são expostos no body

DTOs separados por contexto — Cadastro, Atualizar, Login e Response expõem só o necessário para cada operação

Histórico de empréstimos imutável — sem delete e sem update exposto; auditoria por design

Stack trace nunca exposto — GlobalExceptionHandler retorna mensagem genérica no 500

Validação dupla — no código (400 claro) e constraint no banco (fallback de integridade)

N+1 eliminado — query JPQL com JOIN explícito em RegistrosRepository

Testes
Bash
./mvnw test
31 testes unitários cobrindo as regras de negócio principais. Mockito isola as dependências — nenhum teste acessa banco de dados.
