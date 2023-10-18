# [Book - Spring Security In Action 2020](https://github.com/havinhphu188/spring-security-in-action-source)

---

Repositorio original del libro
[spring-security-in-action-source](https://github.com/havinhphu188/spring-security-in-action-source.git)

---

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

## [Pág. 50] Estableciendo la configuración de diferentes maneras

Uno de los aspectos confusos de crear configuraciones con Spring Security es tener varias formas de
configurar lo mismo. A continuación aprenderemos **alternativas para configurar UserDetailsService y
PasswordEncoder**.

En la clase de configuración, **en lugar de definir estos dos objetos como @Bean**, los configuramos
a través del método **configure(AuthenticationManagerBuilder auth)**. Sobreescribimos este método de
la clase abstracta **WebSecurityConfigurerAdapter** y usamos su parámetro de tipo **AuthenticationManagerBuilder**
para configurar el **UserDetailsService y el PasswordEncoder**.

**Configuración de UserDetailsService y PasswordEncoder en configure()**

````
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    UserDetails userDetails = User.builder()
            .username("admin")
            .password("12345")
            .authorities("read")
            .build();
    InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
    inMemoryUserDetailsManager.createUser(userDetails);

    auth.userDetailsService(inMemoryUserDetailsManager)
            .passwordEncoder(NoOpPasswordEncoder.getInstance());
}
````

Cualquiera de las dos opciones de configuración mostradas en los pasos anteriores, es correcta.

````
[@Bean UserDetailsService y @Bean PasswordEncoder] vs [@Override configure(AuthenticationManagerBuilder auth)]
````

**La primera opción**, en la que agregamos los **beans** al contexto, le permite inyectar los valores
en otra clase donde podría necesitarlos. **Pero si en nuestro caso no necesitamos** eso, **la segunda
opción sería igualmente buena**. Sin embargo, se recomienda que evitemos mezclar configuraciones
porque podría crear confusión.

**Configuración de la gestión de usuarios en memoria**

Hasta ahora, hemos visto 2 opciones de configuración, la primera usando los @Bean y la segunda
sobreescribiendo el método configure(). Para esta segunda opción, existe otra manera de poder
configurar los usuarios en memoria:

````
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
            .withUser("admin")
            .password("12345")
            .authorities("read")
            .and()
            .passwordEncoder(NoOpPasswordEncoder.getInstance());
}
````

Para este último enfoque, el autor del libro **no lo recomienda**, ya que le parece
mejor separar y escribir responsabilidades lo más desacopladas posible en una aplicación.

En conclusión, podríamos decir que es **mejor usar los @Bean UserDetailsService y PasswordEncoder (será la opción que
dejaremos habilitada)** o si se opta por sobreescribir el método configure(AuthenticationManagerBuilder auth), definir
los objetos por separado y no hacer la concatenación que vemos en el código del ejemplo anterior.

## [Pág. 53] Anulando la implementación del AuthenticationProvider

De acuerdo a la Arquitectura de Spring Security que mostramos al inicio, el **Authentication Provider**, se encarga de
**implementar la lógica de autenticación**. Recibe la solicitud del Authentication Manager y delega la
búsqueda del usuario en un **UserDetailsService** y la verificación de la contraseña en un **PasswordEncoder**.

Por lo tanto, en este apartado veremos cómo sobreescribir el AuthenticationProvider para crear un
AuthenticationProvider personalizado.

Según el autor, **es importante respetar la Arquitectura de Spring Security,** ya que está diseñado con un bajo
acoplamiento y con responsabilidades detalladas. Ese diseño es una de las cosas que hace que Spring Security
sea flexible y fácil de usar. Ahora, dependiendo de cómo se haga uso de su flexibilidad, podríamos cambiar su
diseño, pero **¡CUIDADO!**, tengamos cuidado con los enfoques que le demos, porque podrían complicar la solución.
Por ejemplo, podríamos optar por anular el AuthenticationProvider de manera que, ya no necesitemos un
UserDetailsService o PasswordEncoder. Para ello, crearemos la siguiente clase implementando de la interfaz
AuthenticationProvider:

````
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        if (username.equals("martin") && password.equals("12345")) {
            return new UsernamePasswordAuthenticationToken(username, password, List.of());
        }
        throw new AuthenticationCredentialsNotFoundException("Error in authentication!!!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
````

**DONDE:**

- El método **authentication.getName();** lo hereda la autenticación de la interfaz Principal.
- La condición de la cláusula **if-else** reemplaza las responsabilidades de **UserDetailsService y PasswordEncoder**.
  Por lo tanto, **aquí no es necesario que use los dos beans**, por lo que procederemos a eliminarlos
  del archivo de configuración.

Como observamos en el código anterior, el método **authenticate(Authentication authentication)** representa
toda la lógica para la autenticación. Explicaré el uso del método **supports()** en detalle en el capítulo 5.
Por el momento, te recomiendo que des por sentado su implementación. No es esencial para el ejemplo actual.

**IMPORTANTE:**

> Como se mencionó anteriormente, con esa forma de anular el AuthenticationProvider, ya no es necesario usar los dos
> beans (UserDetailsService y PasswordEncoder). **¡PERO!**, si trabajamos con usuarios y contraseñas para la
> autenticación, el autor sugiere que ```separemos la lógica de su administración, es decir, que lo apliquemos
tal como fue diseñado la arquitectura de Spring Security, eso significa que deberíamos usar el UserDetailsService
y PasswordEncoder, incluso cuando anulemos la implementación de la autenticación.```

**Ahora, por motivos del ejemplo, continuaremos con la anulación del AuthenticationProvider sin la necesidad de usar
el UserDetailsService y el PasswordEncoder.**

**REGISTRANDO LA NUEVA IMPLEMENTACIÓN DEL AUTHENTICATION PROVIDER**

En la clase de configuración, podemos registrar nuestro AuthenticationProvider personalizado en el método
**configure(AuthenticationManagerBuilder auth)** que será sobreescrito de la clase abstracta
WebSecurityConfigurerAdapter.

````
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProvider customAuthenticationProvider;

    public ProjectConfig(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
    }
}
````

Probamos con curl enviándole las credenciales definidas de forma estática en el método **authenticate(Authentication
authentication)** y como respuesta nos mostrará el mensaje **hello!**.  
**NOTA**, en el método **configure(HttpSecurity http)** cambiamos el .permitAll() por .authenticated() para que nos
pida las credenciales.

````
curl -v -u martin:12345 http://localhost:8080/greetings/hello
````

## [Pág. 56] Usando múltiples clases de configuración en su proyecto

En los ejemplos implementados anteriormente, solo usamos una clase de configuración. Sin embargo, es una buena práctica
separar las responsabilidades incluso para las clases de configuración. Necesitamos esta separación porque la
configuración empieza a ser más compleja.

Siempre es una buena práctica tener solo una clase por cada responsabilidad. Para este ejemplo, podemos separar la
**configuración de administración de usuarios de la configuración de autorización**. Hacemos eso definiendo dos clases
de configuración: **UserManagementConfig** y **WebAuthorizationConfig**:

**UserManagementConfig**

````
@Configuration
public class UserManagementConfig {
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
````

En este caso, la clase UserManagementConfig solo contiene los dos beans que se encargan de la gestión de usuarios:
UserDetailsService y PasswordEncoder.

Configuraremos los dos objetos como beans porque esta clase no puede extender WebSecurityConfigurerAdapter,
ya que esta clase abstracta ya fue extendida en la otra clase WebAuthorizationConfig.

**WebAuthorizationConfig**

````
@Configuration
public class WebAuthorizationConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
    }
}
````

Aquí, la clase WebAuthorizationConfig necesita extender WebSecurityConfigurerAdapter y anular el método configure (
HttpSecurity http).

> **NOTA**  
> En este caso, no puede tener ambas clases extendiendo WebSecurityConfigurerAdapter. Si lo hace, la inyección de
> dependencia fallará.

Sobre las otras clases que teníamos **ProjectConfig** y **CustomAuthenticationProvider** las eliminamos, ya que ahora
las configuraciones las definimos por separado y además estamos haciendo uso de los @Bean UserDetailsService y
PasswordEncoder.
