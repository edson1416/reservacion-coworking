# API de Gestión de Reservas para Coworking

API RESTful desarrollada en Spring Boot para la gestión de salas y reservaciones en un entorno de Coworking. 

### El sistema incluye:
* Autenticación JWT 
* Mantenimiento de Salas
* Gestion de reservaciones
* Cálculo de ocupación porcentual de las salas en un periodo de tiempo con caché
* Despliegue contenerizado preparado para entornos de Desarrollo y Producción.

## Instrucciones de Ejecución

El proyecto está completamente contenerizado usando **Docker** y **Docker Compose** incluyendo sus bases de datos PostgreSQL.

### Requisitos previos
* [Docker](https://docs.docker.com/get-docker/) y Docker Compose instalados.
* Asegurarse de tener libres los puertos `8080`, `5443` y `5444` en su máquina local.

### Paso 1: Levantar la infraestructura
En la raíz del proyecto (donde se encuentra el archivo `docker-compose.yml`), abra una terminal y ejecute:

```bash
docker compose up --build -d
```
Este comando realizará lo siguiente de forma automática:

* Compilará el código fuente usando Maven dentro de un contenedor
* Levantará dos instancias de PostgreSQL una para Desarrollo y otra para Produccion
* Levantará el microservicio en Spring Boot conectado a la base de datos de Desarrollo por defecto.


### Paso 2: Verificar el despliegue

```bash
docker compose logs -f app
```

## Accesos y Credenciales
### API
* URL Base: http://localhost:8080
* La API arranca por defecto con el perfil dev y las tablas se crean automáticamente (ddl-auto: update)

### Base de Datos de Desarrollo
* Host: localhost | Puerto: 5443
* Usuario: postgres | Password: password
* DB Name: db_coworking

### Base de Datos de Producción
* Host: localhost | Puerto: 5444
* Usuario: postgres | Password: password
* DB Name: db_coworking_prod

## Decisiones y Trade-offs

## Decisiones
Dadas las restricciones de tiempo para esta prueba, tome las siguientes desiciones arquitectónicas para priorizar en la entrega el valor, 
la estabilidad y las buenas prácticas:

### Spring Data JPA
* Me permite abstraer la capa de persistencia mediante interfaces, lo que me permitio enfocarme en la logica de negocio y no escribir
muchas Querys

### Spring Data JPA
* Es una de las mejores opciones en lo que respecta a una aquitectura Stateless y asi reducimos la carga en el servidor y una autenticación distribuida

### @ControllerAdvice
* Gracias al @ControllerAdvice me pude ahorrar infinida de bloques de "try-catch" dispersos por todo el codigo, asi como tener un mejor control
con respecto a las excepciones que se muestran (ej: el choque de horarios al momento de reservar o el banco no proceso el pago).

### Configuración de perfiles (dev/prod)
* Las razones principales son seguridad y buenas practicas, no podemos tener las credenciales de la DB de producción dentro del codigo.
Tener esta configuración de perfiles permite inyenctar configuraciones externas para garantizar que el entorno de desarrollo sea agil en Dev
y en producción sea robusto y seguro.

### @Cacheable
* Permite la optimización de recursos al momento de hacer la consulta de reportes, podria llegar a ser muy costosa en tiempo de ejecución, pero 
al cachear estos datos se reduce el numero de llamdas a la DB.

### Procesamiento asíncrono con @Async
* Mejora mucho la experiencia de usuario, al enviar o en este caso simular el envio de un correo puede ser un proceso lento si se hace de manera
sincrona ya que nos tocaria esperar la respuesta del SMTP para poder ver la confirmación. Usando @Async se libera el hilo principal y asi el usuario
recibe una respuesta inmediata.

### @Transactional
* Integridad de los datos, si en dado caso una de las reservaciones falla a la mitad @Transactional hace el rollback automático, evitando asi datos corruptos.

### OpenAPI / Swagger
* Mejora mi experiencia como desarrollador y en el dado caso de pasar este proyecto a frontend ayuda a interactuar con el sistema sin tener que
ver el codigo java.

### Testing: Unitarios + Integración
* Test unitarios por ser la maenera mas practica de validar la logica de negocio aislada, mientras que los de integración aseguran que las piezas se ensamblan correctamente con la BD,
Asi evitamos que se rompa algo en producción.

### Patrón de Diseño State (Ciclo de vida de la reserva)
* Tener un codigo ordenada y escalable, este patron de diseño me evito el uso de "if-else" uno tras otro, el ciclo de vida de las reservaiones
tiene estados claros PENDIENTE, CONFIRMADA, CANCELADA, el patron de diseño encapsulo el comportamiento de cada estado en su propia clase,
haciendo asi que añadir nuevos estados sea mas sencillo y no rompa la logica que ya existe.

## Trade-offs
* Catalogo de estados de reservación.
* Catalogo de tipo de sala.
* Uso de arquitectura Strategy para calcular tarifas de pago.
* Validación personalizada al experirar el token.


## Que se podria hacer si se contara con mas tiempo
## 1. Exportación de Reportes
El endpoint de ocupación actualmente retorna JSON. Se agregaría la funcionalidad para exportar estos datos dinámicamente a formatos Excel 
o PDF utilizando librerías como Apache POI o JasperReports.

## 2. Notificaciones y Asincronía
Implementar un sistema de notificaciones por correo (JavaMailSender) o webhooks que se dispare de forma asíncrona

## 3. Soft Deletes y Auditoría
Implementar borrado lógico para no perder histórico de reservaciones anuladas, y usar @EntityListeners para registrar automáticamente 
quién y cuándo creó o modificó cada registro.

## Que se dejó fuera de alcance
* Circuit Breaker con Resilience4j (spring-cloud-starter-circuitbreaker-resilience4j) sobre la
llamada al servicio externo de validación de pago.