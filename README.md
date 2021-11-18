# Wallet - Serviço de Carteira Digital

# O que é?
O Wallet Service é um serviço que disponibiliza um conjunto de funcionalidades referentes à carteira digital, onde por intermédio de uma conta com acesso restrito, se pode fazer operações financeiras protegidas.

# Como funciona?
## Em nível de negócio, foram mapeadas as seguintes estórias:
```
Como cliente autenticado na plataforma
Quero efetuar depósitos em minha conta 
Para que eu tenha saldo disponível para operações financeiras
```
```
Como cliente autenticado na plataforma
Quero efetuar saques em minha conta
Para aproveitar o saldo disponível na mesma
```
```
Como cliente autenticado na plataforma
Quero realizar pagamentos via carteira digital
Para agilizar pagamentos com meu saldo disponível em conta
```
```
Como cliente autenticado na plataforma
Quero realizar transferências de valores entre contas
Para sanar dívidas utilizando minha carteira digital
```
Conceitualmente, as entidades mapeadas foram dispostas da seguinte maneira na solução:

<img src="https://github.com/marcoscesarmelo/wallet/blob/main/files/uml-class.PNG"/>

# Como foi feito?
O Wallet Service possui uma API Restful, elaborada utilizando Java 11 e Spring Boot + Spring Data e Spring Security, com comunicação via mensagens (RabbitMQ) além de um BD relacional MySQL. Além da API, também há um outro micro serviço, que utiliza as mesmas tecnologias, mas se faz uso de Bibliotecas de Scheduling, para popular de forma assíncrona, uma linha do tempo com as transações realizadas.

### O que eu preciso para executar localmente em minha estação de trabalho?
* [Java 11](http://jdk.java.net/java-se-ri/11) Java JDK 
* [Spring Framework](http://spring.io/) - Framework utilizado (Spring Boot + Spring Data +  Spring Security)
* [Maven](https://maven.apache.org/) - Gerenciador de Dependências
* [Postman](https://www.getpostman.com/) - Um client de chamadas Restful, por exemplo, o Postman
* [MySQL](https://www.mysql.com/downloads/) - Gerenciador de Banco de Dados MySQL
* [RabbitMQ](https://www.rabbitmq.com/) - Broker de Mensagens

E um PC compatível para utilizar tais ferramentas.

A imagem abaixo mostra um esboço de arquitetura, mostrando como os serviços estão dispostos e algumas ferramentas utilizadas: 

<img src="https://github.com/marcoscesarmelo/wallet/blob/main/files/arquitetura.PNG"/>

## Instruções para uso local:

1. Certifique-se que o MySQL está devidamente instalado (a versão utilizada foi a 8.0). Ao iniciar o Wallet Service, o Banco de Dados walletdb será criado, bem como suas tabelas (DDL). Ao término desta etapa, o banco de dados já estará OK para uso!.

2. Certifique-se que o RabbitMQ está instalado corretamente em sua estação. Pode-se acessar o [RabbitMQ Console](http://localhost:15672/) local (credenciais de convidado <strong>"guest:guest"</strong>) e verificar o gerenciamento das filas criadas.

3. Utilize uma IDE de sua preferência, ou mesmo o maven para compilar os projeto 2 projetos
- wallet 
- timeline

Obs: as versões mencionadas no arquivo pom.xml estão como 0.0.1-SNAPSHOT por ser um draft \
Para cada um dos dois projetos, navegue até a pasta raíz do mesmo e execute comando:

```DOS
java -jar wallet-0.0.1-SNAPSHOT.jar
```
```DOS
java -jar timeline-0.0.1-SNAPSHOT.jar
```
Ambos os comandos podem ser substituídos por:
```DOS
mvnw spring-boot:run
```
4. Feito isto, os serviços estarão rodando. Agora basta acessar ao Postman e criar as chamadas para a API


### O que a API me disponibiliza?
Segue a lista dos principais Serviços e exemplos de resultados:

## Registar - Incluir uma conta:
(*) POST na URL http://localhost:8080/register \
Exemplo de Body:
```json
{
"username":"greenlight",
"password":"redlight"
}
```
Parâmetros de Header: 
Content-Type: application/json 

Exemplo de Resposta: 
```json
{
    "username": "greenlight",
    "accountId": 3
}
```

## Autenticar - Efetuar Login:
(*) POST na URL http://localhost:8080/authenticate \
Exemplo de Body:
```json
{
"username":"greenlight",
"password":"redlight"
}
```
Parâmetros de Header: 
Content-Type: application/json 

Exemplo de Resposta: 
```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJncmVlbmxpZ2h0IiwiZXhwIjoxNjM3MjY1NDI4LCJpYXQiOjE2MzcyNDc0Mjh9.UuqEYDVGKr-H5gusB18J86RcK3ut1Pcwz-JXMLtr2fvxkKHWcsecP0Ilqu7IkhNJq8CQVXe3MKUpKDISHfVrng"
}
```
-Obs.: O token passado se refere a um usuário autenticado. Os demais serviços listados abaixo, precisarão inicialmente deste token para garantir usuário logado.

## Deposit - Depositar em Conta:
(*) PUT na URL localhost:8080/account/deposit/{id-conta} \
Parâmetros de Header: \
Content-Type: application/json \
amount: {valor numerico} \
Authorization: "Bearer " + &lt;token&gt; \
Exemplo de Resposta: 
```json
{
    "id": 3,
    "amount": 1000.0
}
```

## Withdraw - Saque em Conta:
(*) PUT na URL localhost:8080/account/withdwaw/{id-conta} \
Parâmetros de Header: \
Content-Type: application/json \
amount: {valor numerico} \
Authorization: "Bearer " + &lt;token&gt; \
Exemplo de Resposta: 
```json
{
    "id": 3,
    "amount": 800.0
}
```

## Payment - Pagamentos pela Conta:
(*) PUT na URL localhost:8080/account/payment/{id-conta} \
Parâmetros de Header: \
Content-Type: application/json \
amount: {valor numerico} \
Authorization: "Bearer " + &lt;token&gt; \
Exemplo de Resposta: 
```json
{
    "id": 3,
    "amount": 766.67
}
```

## Transfer - Transferência de valores entre Contas:
(*) PUT na URL localhost:8080/account/transfer/{id-conta} \
Parâmetros de Header: \
Content-Type: application/json \
amount: {valor numerico} \
destinyAccount: {Id Conta Destino} \
Authorization: "Bearer " + &lt;token&gt; \
Exemplo de Resposta: 
```json
{
    "id": 3,
    "amount": 466.66
}
```

Obviamente, ambas as contas precisam estar cadastradas e a conta origem, autenticada (logada).

## Timeline - Movimentações Financeiras de uma conta
(*) GET na URL localhost:8080/operation/timeline/{id-conta} \
Parâmetros de Header: \
Content-Type: application/json \
Authorization: "Bearer " + &lt;token&gt; \
Exemplo de Resposta: 
```json
[
    {
        "amount": 1000.0,
        "type": "DEPOSIT",
        "description": "Deposit into account: 3, amount: 1000.0, at: Thu Nov 18 12:00:32 BRT 2021",
        "moment": "2021-11-18T15:00:32.542+00:00",
        "accountId": 3
    },
    {
        "amount": 200.0,
        "type": "WITHDRAW",
        "description": "Withdraw from account: 3, amount: 200.0, at: Thu Nov 18 12:12:33 BRT 2021",
        "moment": "2021-11-18T15:12:33.763+00:00",
        "accountId": 3
    },
    {
        "amount": 33.33,
        "type": "PAYMENT",
        "description": "Debit for payment from account: 3, amount: 33.33, at: Thu Nov 18 12:13:33 BRT 2021",
        "moment": "2021-11-18T15:13:33.554+00:00",
        "accountId": 3
    },
    {
        "amount": 300.0,
        "type": "TRANSFER",
        "description": "Debit for Transfer from account: 3, amount: 300.0, at: Thu Nov 18 12:14:42 BRT 2021",
        "moment": "2021-11-18T15:14:42.032+00:00",
        "accountId": 3
    }
]
```

# E o Timeline Service nesta história?
De maneira assíncrona, cada transação feita é incluída em uma fila (publisher - serviço wallet) e há um serviço batch que obtém estas mensagens e persiste no banco de dados, para posterior consulta na endpoint /timeline.
Por isto, além da API, se fez tão importante o serviço batch para gerar insumos à timeline.

# Alguns Testes Integrados:
Utilizando-se de um framework nativo para o Spring, o MockMVC, foram desenvolvidos três cenários (2 positivos e 1 negativo). 
- Criação de Conta (positivo). 
- Consulta à Timeline (positivo). 
- Pagamento para Conta inexistente (negativo). 

Para invocar, por exemplo, via Maven, no diretório raíz do projeto wallet:
```DOS
mvnw clean test
```
Espera-se um resultado parecido com o abaixo: 
```DOS
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  51.621 s
[INFO] Finished at: 2021-11-18T13:33:37-03:00
[INFO] ------------------------------------------------------------------------
```
Obs: detalhes de suas implementações estão no código.

## Próximas versões:
Conforme mostrado acima, o serviço foi desenvolvido para ser testado inicialmente no ambiente local.
O intuito de uma nova versão é a inclusão dos mesmos em um container Docker.
Inclusive na solução constam arquivos: 
- Dockerfile
- docker-compose.yml

Como um início de configuração.

## Sobre o Autor:
[Marcos Cesar de Oliveira Melo](https://www.linkedin.com/in/marcoscesarmelo/)

