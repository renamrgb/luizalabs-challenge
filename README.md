# Luizalabs Challenge

## Requisitos

- Docker
- Java 17 (para executar testes localmente)
- Maven (para executar testes localmente)

## Rodando a Aplicação com Docker

Para rodar a aplicação, RabbitMQ e PostgreSQL utilizando Docker, siga os passos abaixo:

1. **Clone o Repositório**

   ```bash
   git clone https://github.com/renamrgb/luizalabs-challenge.git
   cd luizalabs-challenge
   docker compose up --build
    ```
## Rodando Tests
1. **Teste com maven e java local**
  ```bash
      mvn clean test
   ```
2. **Teste com wrapper do maven**
    ```bash
      ./mvnw clean test
   ```
### Coleção Postman

Você pode acessar a coleção do Postman para este projeto clicando [aqui](./postman/Luizalabs.postman_collection.json).

Ou, para importar no Postman, use o seguinte arquivo:

`postman/Luizalabs.postman_collection.json`
