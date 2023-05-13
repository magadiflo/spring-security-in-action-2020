# Book - Spring Security In Action 2020

## [Pág. 36] Configuración por defecto se Spring Security

Una vez agregada la dependencia de Spring Security:

````
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
````

Automáticamente, establece una configuración por defecto, asegurando la aplicación.
Con esta configuración por defecto, en la consola muestra un **password** generado aleatoriamente cada vez que se inicia
la aplicación, mientras que el usuario por defecto es **user**.

````
Using generated security password: a3bfd5ea-79e0-4a76-8668-08a19871c818
````

## [Pág. 38] LLamando al endpoint con HTTP Basic Authentication

Podemos usar postman, SOAP UI, curl, etc., para poder acceder a nuestro endpoint que
tenemos de prueba, pero ahora como ya tenemos la dependencia de Spring Security y este
securiza nuestra aplicación por defecto, para poder acceder al endpoint necesitamos
mandarle las credenciales que nos generó:

````
 curl -v -u user:a3bfd5ea-79e0-4a76-8668-08a19871c818 http://localhost:8080/greetings/hello
````

Como vemos, estamos utilizando curl para hacer la petición http a nuestro controlador. Usamos
la bandea -u para establecerle las credenciales que nos generó la aplicación. Por debajo de escena,
**curl codifica en base64** las credenciales y lo envía como el valor del encabezado **Authorization**
con el prefijo **Basic**:

````
Credenciales sin codificar: user:a3bfd5ea-79e0-4a76-8668-08a19871c818
Credenciales codificadas en base64: dXNlcjphM2JmZDVlYS03OWUwLTRhNzYtODY2OC0wOGExOTg3MWM4MTg=
````

Realmente así sucede la petición:

````
curl -v -H "Authorization: Basic dXNlcjphM2JmZDVlYS03OWUwLTRhNzYtODY2OC0wOGExOTg3MWM4MTg=" http://localhost:8080/greetings/hello
````

Como vemos, aquí debemos colocar el Authorization.... y luego codificarlo en base64, etc.
pero es así como realmente es la petición real, pero si queremos tener algo de ayuda podemos usar
la bandea -u de curl.

Hasta este punto, la llamada nos devuelve **Hello!**. De esta forma podemos estar seguros que tenemos
la dependencia de Spring Security instalado correctamente en nuestro proyecto.
