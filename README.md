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
