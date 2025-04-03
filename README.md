# Livraria API - Sistema de Microserviços

## 📚 Sobre o Projeto

Este é um sistema de livraria baseado em microserviços, desenvolvido com Spring Boot e utilizando Apache Kafka para comunicação assíncrona entre os serviços. O projeto implementa uma arquitetura moderna e escalável para gerenciar operações de uma livraria online.

## 🏗️ Arquitetura

O sistema é composto pelos seguintes microserviços:

- **Auth Service**: Gerenciamento de autenticação e autorização
- **Book Service**: Gerenciamento do catálogo de livros
- **Order Service**: Processamento de pedidos
- **Payment Service**: Processamento de pagamentos
- **Address Service**: Gerenciamento de endereços
- **Orchestrator Service**: Orquestração do fluxo entre serviços

## 🛠️ Tecnologias Utilizadas

- **Spring Boot**: Framework para desenvolvimento dos microserviços
- **Apache Kafka**: Mensageria para comunicação assíncrona
- **Docker**: Containerização dos serviços
- **Swagger/OpenAPI**: Documentação das APIs
- **Spring Security**: Segurança e autenticação

## 🚀 Como Executar

### Pré-requisitos

- Docker e Docker Compose
- Java 17+
- Maven

### Passos para Execução

1. Clone o repositório:
```bash
git clone https://github.com/HenriqueFrancoA/Livraria-Api-Kafka.git
cd Livraria-Api-Kafka
```

2. Execute o Docker Compose:
```bash
docker-compose up -d
```

3. Os serviços estarão disponíveis nos seguintes endereços:
   - Auth Service: http://localhost:8080
   - Book Service: http://localhost:8081
   - Order Service: http://localhost:8082
   - Payment Service: http://localhost:8083
   - Address Service: http://localhost:8084
   - Orchestrator Service: http://localhost:8085

## 📝 Documentação da API

Cada serviço possui sua própria documentação Swagger/OpenAPI, acessível através do endpoint `/swagger-ui.html`.

Exemplo: http://localhost:8082/swagger-ui.html para o Order Service

## 🔄 Fluxo de Funcionamento

1. O cliente se autentica através do Auth Service
2. Pode consultar livros disponíveis no Book Service
3. Realiza um pedido através do Order Service
4. O Orchestrator Service coordena o fluxo entre:
   - Validação do endereço (Address Service)
   - Processamento do pagamento (Payment Service)
   - Atualização do estoque (Book Service)

## 🔒 Segurança

O sistema utiliza JWT (JSON Web Tokens) para autenticação e autorização entre os serviços.

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT.
