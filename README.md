<img src="https://eps.ujaen.es/sites/centro_eps/files/styles/news_photo_tablet/public/uploads/node_noticia/2020-03/unnamed.png?itok=gtl_-LKj" width="150" height="90" />

# TERCFIT
## Proyecto de prácticas de Desarrollo de Aplicaciones Web
### Realizado por Antonio Armenteros, David García y Hechun  Ouyang

Nuestra aplicación está diseñada para ayudar a los usuarios a planificar, 
personalizar y registrar sus entrenamientos, además de realizar 
un seguimiento de sus estadísticas corporales y de salud diaria. 
También permite la creación de ejercicios personalizados y la gestión de ejercicios favoritos. 
Para conocer todo el proceso de desarrollo y la información del proyecto, recomendamos leer la memoria final del mismo.

## Tecnologías y herramientas utilizadas

### Backend
- **Jakarta EE (Jakarta Enterprise Edition)**: Base tecnológica del proyecto, utilizada para implementar la lógica del lado del servidor.
- **JPA (Jakarta Persistence API)**: Para la persistencia de datos a través de entidades y DAOs JPA.
- **CDI (Contexts and Dependency Injection)**: Inyección de dependencias mediante anotaciones como `@Named`, `@Inject`, `@RequestScoped`.
- **Java 17**: Lenguaje principal de programación utilizado en la implementación.

### Frontend
- **Jakarta Faces (JSF)**: Utilizado para desarrollar las páginas web mediante archivos `.xhtml`.
- **PrimeFaces**: Librería de componentes JSF para mejorar la UI.
- **JavaScript y JQuery**: Para validación interactiva de formularios y manipulación del DOM.
- **HTMX**: Para la creación de contenidos en la página sin necesidad de hacer código JavaScript.
- **CSS3**: Personalización del diseño visual de las páginas.

### Persistencia y almacenamiento
- **DAO Pattern (DAOMap y DAOJpa)**: Implementación dual para el acceso a datos usando memoria y JPA.
- **Entidades JPA**: `Cliente`, `Biometria`, `Dni`, `GrupoMuscular`, etc.

### Seguridad
- **Hashing de contraseñas**: Implementación segura de contraseñas mediante una clase `PasswordUtils`.
- **Validaciones**: Validación en cliente y servidor para los formularios de login, registro y edición de usuario.

### Arquitectura REST
- **Servicios REST**: Expuestos en `ContactResource.java` y `RestApplication.java`.
- **@Path y @GET/POST**: Para definir endpoints RESTful y sus métodos de acceso.

### Gestión de Sesiones
- Controladores como `ClienteController` y `ClubAuthService` permiten la gestión de sesión de usuarios y administradores.

### Controladores JSF
- Beans como `fechaBean`, `dashboardBean` para gestionar interacciones entre el modelo y la vista.
  
### Requisitos funcionales del usuario
- Identificarse con unas credenciales correctas
- Ingresar datos biométricos
- Consultar registros de los datos biométricos realizados
- Buscar ejercicios
- Añadir ejercicios a favoritos
- Crear nuevos ejercicios
- Consultar ejercicios de recetas para gestionarlos
- Modificar las estadísticas de un ejercicio
- Crear nuevas recetas
- Ver detalles de las recetas
- Consultar registros de recetas para gestionarlas

### Requisitos funcionales del administrador
- Identificarse con unas credenciales correctas
- Acceder al panel de control del admin
- Ver las credenciales activas del sistema
- Acceder a la API de los clientes
- Acceder a la API de los ejercicios
- Acceder a la API de las recetas
- Acceder a la lista de usuarios en tiempo real
- Así como todas las acciones que puede realizar el usuario

![imagen Inicio](/images/home.png)
<sup>**Imagen 1.** Página de inicio de TERCFIT.</sup>






