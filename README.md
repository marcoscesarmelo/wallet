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

<img src="https://github.com/marcoscesarmelo/wallet/blob/main/uml-class.PNG"/>

# Como foi feito?
O Wallet Service possui uma API Restful, elaborada utilizando Java 11 e Spring Boot + Spring Data e Spring Security, com comunicação via mensagens (RabbitMQ) além de um BD relacional MySQL. Além da API, também há um outro micro serviço, que utiliza as mesmas tecnologia, mas se faz uso de Bibliotecas de Scheduling, para popular de forma assíncrona, uma linha do tempo com as transações realizadas.

### O que eu preciso para executar localmente em minha estação de trabalho?
* [Java 11](http://jdk.java.net/java-se-ri/11) Java JDK 
* [Spring Framework](http://spring.io/) - Framework utilizado (Spring Boot + Spring Data +  Spring Security)
* [Maven](https://maven.apache.org/) - Gerenciador de Dependências
* [Postman](https://www.getpostman.com/) - Um client de chamadas Restful, por exemplo, o Postman
* [MySQL](https://www.mysql.com/downloads/) - Gerenciador de Banco de Dados MySQL
* [RabbitMQ](https://www.rabbitmq.com/) - Broker de Mensagens

E um PC compatível para utilizar tais ferramentas.

A imagem abaixo mostra um esboço de arquitetura, mostrando como os serviços estão dispostos e algumas ferramentas utilizadas: 

<img src="https://github.com/marcoscesarmelo/wallet/blob/main/arquitetura.PNG"/>

## Instruções para uso local:

1. Certifique-se que o MySQL está devidamente instalado (a versão utilizada foi a 8.0) instalado. Ao iniciar o Wallet Service, o Banco de Dados walletdb será criado, bem como suas tabelas (DDL). Ao término desta etapa, o banco de dados já estará OK para uso!.

2. Certifique-se que o RabbitMQ está instalado corretamente em sua estação. Pode-se acessar o [RabbitMQ Console](http://localhost:15672/) local (credenciais de convidado <strong>"guest:guest"</strong>) e verificar o gerenciamento das filas criadas.

3. Utilize uma IDE de sua preferência, ou mesmo o maven para compilar os projeto 2 projetos
- wallet 
- timeline

Obs: as versões mencionadas no arquivo pom.xml estão como 0.0.1-SNAPSHOT por ser um draft
Para cada um dos dois projetos, navegue até a pasta raíz do mesmo e execute comando:

```DOS
java -jar wallet-0.0.1-SNAPSHOT.jar
```
```DOS
java -jar timeline-0.0.1-SNAPSHOT.jar
```
4. Feito isto, os serviços estarão rodando. Agora basta acessar ao Postman e criar as chamadas para a API


## Sobre o Autor:
[Marcos Cesar de Oliveira Melo](https://www.linkedin.com/in/marcoscesarmelo/)

