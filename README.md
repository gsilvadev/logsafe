# logsafe
API rest para geração e consulta de logs com autenticação por token.

**Setup inicial:**

 --- As configurações de conexão ao banco estão como abaixo:
```
 # DataSource Configuration
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.url=jdbc:mysql://localhost:3306/logsafe?createDatabaseIfNotExist=true
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=true
```
Para modificar as configurações de conexão edite o arquivo ```src\main\resources\application.properties```.

--- Ao subir o sistema o banco de dados e as tabelas serão criadas automaticamente. Se o banco que estiver usando não for o MySql provavelmente terá que remover o parâmetro "?createDatabaseIfNotExist=true" e criar o banco manualmente, mas não se preocupe com as tabelas. O parâmetro "spring.jpa.hibernate.ddl-auto=update" garantirá que elas sejam criadas.

--- Quando o sistema estiver em execução já deverá ter sido feito uma carga de dados iniciais. Isso inclui os produtos, categorias e os clientes válidos. Também será criado um usuário com login "admin" e senha "admin" realização de testes.

--- Para utilizar os serviços de log primeiro deve-se efetuar o login através do método ```POST``` para a uri ```/api/auth``` com os parâmetros ```login=admin``` e ```senha=admin``` com o content-type ```x-www-form-urlencoded```. Você receberá como resposta o token que será necessário para as próximas requisições.
--- Com o token em mãos já é possível acessar a uri ```/api/private/logs``` com os métodos ```GET``` ou ```POST```.
--- Com o método ```POST``` (usado para criar um novo recurso no servidor) você precisará adicionar no header da requisição o parâmetro ```Authorization``` com o valor ```Bearer {token}``` (Substitua {token} pelo token real, não esqueça do espaço entre os dois) e no corpo da requisição com o content-type ```application/json```um json similar a este:
```
{
	"cliente":"Alagoas",
	"categoria":"permissão",
	"produto":"OAB",
	"registros":{
		"Teste1":"Teste1",
		"Teste2":"Teste2"
	}
}
```
Se correr tudo bem você terá uma resposta como a seguinte:
```
{
    "id": 3,
    "produto": "OAB",
    "cliente": "Alagoas",
    "dataHora": "2017-06-15 20:35:09",
    "categoria": "permissão",
    "registros": {
        "Teste1": "Teste1",
        "Teste2": "Teste2"
    },
    "usuarioResponsavel": "admin"
}
```

Se algo der errado será exibida uma lista de mensagem explicando o que não foi bem. As mensagens possíveis são:
```
{
    "messages": [
        {
            "title": "NotEmpty",
            "message": "O campo produto é obrigatório."
        },
        {
            "title": "NotEmpty",
            "message": "O campo cliente é obrigatório."
        },
        {
            "title": "NotEmpty",
            "message": "O campo categoria é obrigatório."
        },
        {
            "title": "Produto inválido",
            "message": "O produto null não é válido."
        },
        {
            "title": "Categoria inválida",
            "message": "A categoria null não é válida."
        },
        {
            "title": "Cliente inválido",
            "message": "O cliente null não é válido."
        },
        {
            "title": "Registros inexistentes",
            "message": "Deve haver ao menos um registro de log."
        }
    ]
}
```
Específicamente para o caso de "Token expirado" terá o seguinte retorno:
```
{
    "timestamp": 1497563965981,
    "status": 401,
    "error": "Unauthorized",
    "message": "The Token has expired on Thu Jun 15 18:57:52 GMT-03:00 2017.",
    "path": "/api/private/logs"
}
```
--- Com o método ```GET``` para a uri ```/api/private/logs``` deverá seguir a mesma estratégia com o parâmetro ```Authorization``` do ```POST```. Esse serviço suporta que sejam passados parâmetros via url depois do caractere ```?```. Os parâmetros suportados são:

/api/private/logs?```produto=OAB```
/api/private/logs?```categoria=permissão```
/api/private/logs?```cliente=Alagoas```
/api/private/logs?```from=2017-06-15```  Obs: consulta logs a partir de...
/api/private/logs?```to=2017-06-16```  Obs: consulta logs até...
/api/private/logs?```usuario=admin```

Você também pode usar uma combinação desses parâmetros para refinar ainda mais o resultado. Pode combinar todos eles se quiser separando cada um com ```&```. Exemplo: ```/api/private/logs?produto=OAB&categoria=permissão```, etc...

--- A validade do token é de 10 minutos.
--- Mais uma coisa, para executar a listagem dos logs é necessário criar alguns antes com o método ```POST```.


Atenciosamente,

**Gilson da S. Oliveira Jr.**
