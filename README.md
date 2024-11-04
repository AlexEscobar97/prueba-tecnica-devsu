# prueba-tecnica-devsu

El proyecto esta confortmado por 2 microservicios haciendo uso de Kafka alojados en los repositorios que se indican a continuacion
Microservicio de persona - cliente ModuloClientePersona.git. Codigo fuente en el directorio ModuloClientePersona
Microservicio cuentas - movimientos ModuloCuentaMovimiento.git. Codigo fuente en el directorio ModuloCuentaMovimiento
Se crearon dos servicios mas los cuales son
Micoreservicio de Registro - sm-eureka-service
Micoreservicio Acceso y control a las peticiones - sm-api-gateway

# Base de datos
Se adjunta scrips de base de datos para la funcionalidad delos microservicio, se utilizó MySql como manejador de base de datos para ambos MS. Los scrips los podemos conseguir en el directorio InformacionConfiguracion.
El nombre del Script
BasePrueba_Script.sql
Script_PoblarTablas.sql -- Insert para poblar las tablas

# Postman
Se adjuntan collecciones de postman utilizada de los microservicio

PruebaTecnica.postman_collection
Se adjunta documentacion del funcionamiento de cada servicio el documento tiene como nombre -->  Guia Sobre pruebas.doc

# Ejecutar en docker
Antes de ejecutar el proyecto en contenedores docker, es necesario primero generar los jar a ser desplegados. Para ello en en cada proyecto de debe de ejecutar el comando maven en cada proyecto
mvn clean package 
Realizar el proceso para cada uno de los servicios:
 ModuloClientePersona
 ModuloCuentaMovimiento
 sm-api-gateway
 sm-eureka-service
 
Una vez ejecutado los jar correctamente.Rezaremos el proceso con el comando docker-compose up para ejecutar el archivo docker-compose.yml
Se adjunta en la carpeta InformacionConfiguracion la guia de instalacion de los componentes para docker el proceso se realizo con docker destok para windows.
Tambien se detalla el uso de las clases para la comunicacion Kafka 

# Pruebas Unitarias Mockito
Se realizo el sed de prueba de cada metodos de los servicios que se ejecutan en los controladores
Micoreservicio ModuloClientePersona -> ClienteControllerTest.class
Micoreservicio ModuloCuentaMovimiento -> (MovimientoServiceImplTest.class y ReporteServiceImplTest.class)






