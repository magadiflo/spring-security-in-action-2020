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

## [Pág. 39-40] Actores principales en la Arquitectura de Spring Security

- El filtro de autenticación delega la solicitud de autenticación al administrador de autenticación y, en función de la
  respuesta, configura el contexto de seguridad.
- El administrador de autenticación utiliza el proveedor de autenticación para procesar la autenticación.
- El proveedor de autenticación implementa la lógica de autenticación.
- El servicio de detalles de usuario implementa la responsabilidad de administración de usuarios, que el proveedor de
  autenticación usa en la lógica de autenticación.
- El codificador de contraseñas implementa la administración de contraseñas, que el proveedor de autenticación usa en la
  lógica de autenticación.
- El contexto de seguridad mantiene los datos de autenticación después del proceso de autenticación
  ![Arquitectura principal](./assets/Main-components-authentication-spring-security.png)

---

# Anulación de configuraciones predeterminadas

## [Pág. 44] Anulando el componente UserDetailsService

Como vimos, la aplicación usa este componente en el proceso de autenticación. Para poder sobreescribir el que viene por
defecto en Spring Security, crearemos un @Bean de esta interfaz que retorna una implementación. Podemos crear nuestra
propia implementación, pero por ahora usaremos una implementación propia de las muchas que tiene Spring Security
**InMemoryUserDetailsManager**:

````
@Bean
public UserDetailsService userDetailsService() {
    UserDetails userDetails = User.builder()
            .username("admin")
            .password("12345")
            .authorities("read")
            .build();
    InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
    inMemoryUserDetailsManager.createUser(userDetails);
    return inMemoryUserDetailsManager;
}
````

Como observamos en el código anterior, se crea un usuario del tipo de la interfaz **UserDetails** a partir de la
implementación concreta **User** de Spring Security. Quien implemente UserDetails, será el tipo de usuario
que será conocido por Spring Security dentro de su arquitectura. Por ahora, solo digamos que estamos creando
un usuario que Spring Security va a conocerlo como tal dentro de su arquitectura. Al usuario creado lo
agregamos dentro de la implementación del UserDetailsService.

**IMPORTANTE**, cuando se utiliza el **UserDetailsService predeterminado**, también **se configura automáticamente** un
**PasswordEncoder**. Debido a que anulamos UserDetailsService, también tenemos que declarar un codificador de
contraseñas. Si no lo hacemos, nos marcará un error, ya que tratará de buscar el codificador y no lo encontrará.

````
@Bean
public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
}
````

En el código anterior estamos usando una implementación de PasswordEncoder deprecado y no solo eso, sino que esa
implementación no codifica el password y la comparación para verificar si las contraseñas coinciden la hace en
texto plano, por eso mismo lo marcaron como deprecado, para que eviten su uso, pero para temas de pruebas,
como ahora, sí nos viene bien usarlo momentáneamente, así que por ahora usaremos las contraseñas así, sin encriptarlas.

Ahora hacemos la prueba enviando nuestro usuario configurado y como resultado
debe mostrarnos el mensaje devuelto por el endpoint: **hello!**.

````
curl -v -u admin:12345 http://localhost:8080/greetings/hello
````

## [Pág. 48] Anulando la configuración de autorización del endpoint

Si queremos personalizar la seguridad de los endpoints, como definir qué tipo de usuarios
pueden acceder a un endpoint determinado o qué endpoint está permitido para el acceso de
todos los usuarios, o personalizar el tipo de autenticación **(por defecto es el HTTP Basic)**,
o realizar otras configuraciones relacionadas, debemos sobreescribir el método **configure(HttpSecurity http)**
de la clase abstracta **WebSecurityConfigurerAdapter** la cual debemos extenderla en nuestra clase de configuración.

````
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    /* other code */
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().anyRequest().permitAll();
    }
}
````

Realizamos la configuración para que cualquier request que se envíe a
nuestra aplicación no requiera autenticarse, así que probamos con curl sin enviarle
credencial alguno y como respuesta nos mostrará el mensaje **hello!**.

````
 curl -v http://localhost:8080/greetings/hello
````