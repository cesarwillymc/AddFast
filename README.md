## Android Components Architecture in a Modular Word

![N|Solid](https://i.blogs.es/df41c1/kotlin_800x320/450_1000.png)

## Application mobile with theme dark and light

<img src="assets/app.gif" width="250" align="right" hspace="20">

| Mode| Light| Dark| 
| ----- | ---- |---- | 
| HOME | <img src="assets/light/home.jpeg" width="250"> | <img src="assets/dark/home.jpeg" width="250"> |
| CATEGORY | <img src="assets/light/category.jpeg" width="250"> | <img src="assets/dark/category.jpeg" width="250"> |
| DETAIL ADD | <img src="assets/light/adddetail.jpeg" width="250"> | <img src="assets/dark/adddetail.jpeg" width="250"> |
| PROFILE | <img src="assets/light/profile.jpeg" width="250"> | <img src="assets/dark/profile.jpeg" width="250"> |
| AUTH | <img src="assets/light/login.jpeg" width="250"> | <img src="assets/dark/login.jpeg" width="250"> |
| AUTH CODE | <img src="assets/light/code.jpeg" width="250"> | <img src="assets/dark/code.jpeg" width="250"> |
| POSTULATE | <img src="assets/light/postulate.jpeg" width="250"> | <img src="assets/dark/postulate.jpeg" width="250"> |
| MY ADDS | <img src="assets/light/myadd.jpeg" width="250"> | <img src="assets/dark/myadd.jpeg" width="250"> |




## Ejecucion del Proyecto

Primero debe sincronizar su proyecto con firebase y cambiar el nombre del paquete

descargar las librerias.

Enlazar Huawei identity para mensajes de texto.


## Estructura

La siguiente arquitectura a mostrar sigue los estándares de la arquitectura basada en capas:

![N|Solid](https://miro.medium.com/max/480/1*eIPadxXhSJicO6GLNR3b7A.png)


### App

Inicializamos la aplicacion con kodein

### Base

Base de las actividades y fragments

### Repo 
Es la conexcion de la aplicacion con los servicios Api que se usan en este caso Firebase y Huawei
#### Conexion
Estaran las funciones de conexcion con firebase
#### Local
Estara la conexcion la la base de datos local
#### Model
Estaran todos los POJOS del aplicativo

### UI
estaran todas las interfaces del  usuario separadas por los usos 

#### Auth
Vista de autentificacion para mensajes de texto

#### Camera
Vista  que gestioan la obtencion de imagenes por archivos o toma de fotos con camerax

#### Main 

Main contiene las vistas de Administrador, services, y usuario

### UTILS

Contiene todas las funciones de uso constante
 
 
 ## Imagenes
 
![1](assets/image1.jpeg)

![2](assets/image2.jpeg)

![3](assets/image3.jpeg)

![4](assets/image4.jpeg)

![5](assets/image5.jpeg)

![6](assets/image6.jpeg)

![7](assets/image7.jpeg)

![8](assets/image8.jpeg)

![9](assets/image9.jpeg)

![10](assets/image10.jpeg)

![11](assets/image11.jpeg)

![12](assets/image12.jpeg)

![13](assets/image13.jpeg)

  ## Redes
 LINKEDIN: CESARWILLYMC

