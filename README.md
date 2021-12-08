# star-wars-network
May the force be with you

# Informações
Java JDK 11

Spring 2.4.11

Swagger open API V3

Banco de dados em memoria H2

IDE IntelliJ

# Comando para compilar e gerar o jar de execução da API
#### mvn clean install

#### Rodar dentro do diretorio raiz

# Comando para iniciar a aplicação
#### java -jar .\star-wars-network-0.0.1-SNAPSHOT.jar


# URL de acesso ao Swagger, documentação da API
#### http://localhost:8083/swagger-ui.html

# URL de acesso a console do banco em memoria H2
#### http://localhost:8083/h2-console/login.jsp
#### URL.: jdbc:h2:mem:star-wars-network

#### USER: sa 

#### PASS: password


# DICAS
#### O EndPoint Inicializador de dados possui um metodo post chamado /initializer/init

#### que permite criar uma massa de dados para utilizar o sistema, 

#### se achar util, aproveite!


