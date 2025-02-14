# MS-Sankhya-OrderMessage-Consumer Application

## Descrição

A aplicação MS-Sankhya-OrderMessage-Consumer é um sistema de consumo de menssagem via kafka para atualizar os status do pedidos. Esta aplicação utiliza Kafka para o gerenciamento de mensagens entre serviços.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **Spring Data JPA**: Para interação com o banco de dados MySQL.
- **Spring Security**: Para autenticação e autorização.
- **Kafka**: Para mensageria e comunicação entre serviços.

## Requisitos

- Java 17
- MySQL (pode ser executado via Docker)
- Kafka e Zookeeper (pode ser executado via Docker)
- Maven

## Instalação

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/emersonoliveira01/ms-sankhya-order-consumer.git
   cd ms-sankhya-order-consumer

2. **Executar o MySQL, Kafka e Zookeeper usando Docker:**
   ```bash
   docker-compose up -d

3. **Executar a aplicação:**
   ```bash
   mvn spring-boot:run

