# Professor Allocation API

Backend em Java com Spring Boot para gerenciar a alocação de professores em cursos e departamentos.

## Objetivo

A API permite:

- cadastrar e consultar departamentos;
- cadastrar e consultar cursos;
- cadastrar e consultar professores;
- registrar alocações de professor em curso por dia e horário;
- validar conflitos de horário e integridade dos dados;
- expor uma documentação interativa via Swagger/OpenAPI.

## Tecnologias

- Java 17
- Spring Boot 4
- Spring Data JPA
- Spring Web MVC
- MySQL (produção) e H2 (ambiente local)
- SpringDoc OpenAPI
- Lombok

## Estrutura do projeto

```text
src/
├── main/
│   ├── java/com/project/professor_allocation/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── exception/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── ProfessorAllocationApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/project/professor_allocation/
```

## Regras de negócio

- Cada professor pertence a um departamento.
- Cada alocação possui:
  - dia da semana;
  - hora inicial;
  - hora final;
  - professor;
  - curso.
- A hora final deve ser maior que a hora inicial.
- Não pode haver sobreposição de horários para o mesmo professor no mesmo dia.
- Os dados de entrada devem ser validados e retornados em DTOs para evitar exposição direta das entidades JPA.

## Endpoints principais

### Departamentos

- `GET /departments`
- `GET /departments/{department_id}`
- `POST /departments`
- `PUT /departments/{department_id}`
- `DELETE /departments/{department_id}`

### Cursos

- `GET /courses`
- `GET /courses/{course_id}`
- `POST /courses`
- `PUT /courses/{course_id}`
- `DELETE /courses/{course_id}`

### Professores

- `GET /professors`
- `GET /professors/{professor_id}`
- `GET /professors/department/{department_id}`
- `POST /professors`
- `PUT /professors/{professor_id}`
- `DELETE /professors/{professor_id}`

### Alocações

- `GET /allocations`
- `GET /allocations/{allocation_id}`
- `GET /allocations/professor/{professor_id}`
- `GET /allocations/course/{course_id}`
- `POST /allocations`
- `PUT /allocations/{allocation_id}`
- `DELETE /allocations/{allocation_id}`

## Documentação da API

A documentação Swagger/OpenAPI está disponível em:

```text
http://localhost:8080/swagger-ui/index.html
```

## Banco de dados

O projeto está configurado para usar H2 em memória por padrão:

- URL: `jdbc:h2:mem:professor_allocation`
- Usuário: `admin`
- Senha: `admin`
- Console H2: `http://localhost:8080/h2-console`

## Como executar

### 1. Pré-requisitos

- Java 17+
- Maven instalado

### 2. Rodar o projeto

```bash
mvn spring-boot:run
```

### 3. Verificar a aplicação

A API ficará disponível em:

```text
http://localhost:8080
```

## Exemplo de payload

### Professor

```json
{
  "name": "Ana Silva",
  "cpf": "12345678901",
  "departmentId": 1
}
```

### Curso

```json
{
  "name": "Algoritmos"
}
```

### Alocação

```json
{
  "dayOfWeek": "MONDAY",
  "startHour": "19:00:00",
  "endHour": "20:30:00",
  "professorId": 1,
  "courseId": 2
}
```

## Tratamento de erros

A aplicação padroniza respostas de erro em um objeto `ApiError`, contendo:

- timestamp;
- status;
- error;
- message.

Isso facilita a integração com frontend ou clientes externos.

## Observações

Este projeto foi estruturado com foco em:

- separação de responsabilidades;
- uso de DTOs para evitar serialização de entidades JPA;
- manutenção de regras de negócio no serviço;
- respostas padronizadas de erro.
