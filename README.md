# Task_management_system

Plataforma para gerenciamento de tarefas com autenticação JWT, notificações por e-mail, busca avançada e documentação via Swagger.

## Sumário

- [Funcionalidades Principais](#funcionalidades-principais)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Como Executar](#como-executar)
- [Documentação da API](#documentação-da-api-swagger)
- [Testes](#testes-e-segurança)
- [Notificações](#notificações-por-e-mail)
- [CI/CD e Deploy](#cicd-e-deploy-automatizado)
- [Docker](#docker)
- [Contribuição](#secção-de-contribuição)

## Funcionalidades Principais

- Gerenciamento completo de tarefas (buscar, criar, atualizar, excluir).
- **Autenticação JWT** para endpoints protegidos.
- **Notificações por e-mail** (via JavaMailSender) ao finalizar uma tarefa.
- **Busca avançada**:
  - Por data de criação
  - Entre datas específicas
  - Por status (ex: "Pendente", "Concluída")
- **Documentação da API** via Swagger/OpenAPI.
- Testes unitários com **JUnit 5 + Mockito**.
- **Docker** (Deploy em containers)
- **CI/CD com GitHub Actions**
- **Análise de Código**: SonarQube e Semgrep

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Security (JWT)
- Swagger/OpenAPI
- JUnit 5 + Mockito (Testes)
- JavaMailSender (Notificações)
- DTOs para separação de camadas
- Exceções customizadas (ex: `TaskNotFoundException`)

## Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/TiagoFerreirago/Task_management_system.git
   ```
2. Configure as variáveis de ambiente no `application.properties`:
   ```properties
   spring:
     mail:
       username: seu-email@gmail.com
       password: sua-senha
   # Configurações do JWT, banco de dados, etc.
   ```
3. Execute o projeto com:
   ```bash
   ./mvnw spring-boot:run  # Para Maven
   # Ou
   ./gradlew bootRun        # Para Gradle
   ```
   
## Documentação da API (Swagger)
   
Acesse `http://localhost:8080/swagger-ui.html` para explorar endpoints como:
- `POST /tasks` (Criar tarefa)
- `GET /tasks?status=CONCLUIDA` (Buscar por status)
- `GET /tasks?startDate=2024-01-01&endDate=2024-01-31` (Busca entre datas)

## Testes e Segurança

- **Testes Unitários**:  
  Execute `./mvnw test` para rodar testes com JUnit 5 e Mockito.
- **Exceções Customizadas**:  
  Exemplo: `TaskNotFoundException` é lançado ao buscar uma tarefa inexistente.
- **Autenticação JWT**:  
  Use o endpoint `POST /auth` para obter um token JWT e inclua-o no header `Authorization: Bearer <token>`.

## Notificações por E-mail

Quando uma tarefa é marcada como concluída, um e-mail é enviado automaticamente para o responsável.  
Configure as credenciais do e-mail no `application.properties ou application.yml`.

## CI/CD e Deploy

## CI/CD e Deploy Automatizado
O projeto utiliza **GitHub Actions** para:
1. Executar testes unitários e integração.
2. Realizar **análises de código** com **SonarQube** e **Semgrep**.
3. Gerar imagem Docker e publicá-la em um repositório (ex: Docker Hub).
4. Deploy automático em ambiente de produção/staging.

#### **Docker**  
```markdown
## Docker
Para executar a aplicação em um container Docker:

```bash
# Construir a imagem
docker build -t task-management-system .

# Executar a aplicação
docker run -p 8080:8080 task-management-system
```

#### **Arquivo Dockerfile**

#### Exemplo (se existir no repositório):

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/task-management-system.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```






