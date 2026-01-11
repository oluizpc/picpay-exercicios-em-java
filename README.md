# Encurtador de URLs — Spring Boot

API REST para encurtamento de URLs, com redirecionamento HTTP, persistência em banco de dados e arquitetura em camadas.

---

## Visão Geral

Este projeto permite a criação de URLs encurtadas a partir de links longos.  
Ao acessar a URL encurtada, o sistema realiza o redirecionamento automático para a URL original.

---

## Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok
- Maven

---

## Arquitetura

O projeto segue o padrão de arquitetura em camadas:

Model → DTO → Repository → Service → Controller + Exceptions globais
- **Controller**: Exposição dos endpoints REST
- **Service**: Regras de negócio
- **Repository**: Acesso ao banco de dados
- **DTOs**: Comunicação entre camadas

---

## Funcionalidades

- Criar URLs encurtadas
- Geração automática de short code
- Redirecionamento HTTP (302)
- Persistência em banco de dados
- Geração de QR Code (em desenvolvimento)

---

## Endpoints

### Criar URL encurtada

**POST** `/api/v1/urls`

#### Request Body
```json
{
  "originalUrl": "https://www.google.com",
  "generateQrCode": false,
  "customShortCode": false
}

{
  "id": 1,
  "originalUrl": "https://www.google.com",
  "shortCode": "1dda8e57",
  "shortUrl": "http://localhost:8080/r/1dda8e57",
  "createdAt": "2026-01-11T16:50:12.123-03:00",
  "qrCodeUrl": null,
  "active": true
}
```

#### Redirecionar URL

```
GET /r/{shortCode}
```

Exemplo: http://localhost:8080/r/1dda8e57


## Banco de Dados

Banco em memória utilizando H2.

H2 Console
```
http://localhost:8080/h2-console
```

#### Configuração padrão
```
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: (vazio)
```

### Configuração

Arquivo application.properties:

```
git clone https://github.com/oluizpc/encurtadorUrl.git
cd encurtadorUrl
mvn spring-boot:run
```

Aplicação disponível em:
http://localhost:8080


## Próximas Evoluções

Exceções customizadas

Expiração de URLs

Contador de acessos

## QR Code

Integração com AWS S3

PostgreSQL em produção

## Autor

Luiz Paullo
Estudante de Sistemas de Informação

