# Parcial arep primer corte: Busqueda de temperatura pro ciudad.

## Introduccion.

Para este laboratorio se requieren conocimientos de tres herramientas para su desarrollo estas son:
1. MVN
2. GIT
3. Heroku

cuando estos requerimientos son cumplidos, y se empieza a desarrollar este proyecto. se busca implementar un
recurso web que permita obtener la información del clima dependiendo de la ciudad que se desea.

Se busca implementar una API que traiga los datos JSON de la página que proporciona el Api de los datos de
el clima de las ciudades del mundo. Se busca implementar sin utilizar ningún framework como spark o springboot,
utilizando.

Implementando el back-end y el front-end para que funcione como se requiere.



### Tenologias


* [Maven](https://maven.apache.org/) - Maven
* [Java ](https://www.oracle.com/co/java/technologies/javase/javase-jdk8-downloads.html)  Java
* [Git](https://git-scm.com/) - Git


### Despliege local:

En esta seccion se daran intrucciones de como descargar y correr localmente la aplicacion y la API de conversion de tempraturascon el proposito
de correrlo en la maquina de cada uno:

##### Despliegue local API

para esto se siguen los siguientes pasos:

1) clonar el repositorio, ya se a traves de cmd o de GIT:

![](https://i.postimg.cc/5954CWBZ/Capture3.png)

2) ingresamos al proyecto clonado y desde cmd hacer uso de mvn para clean y package.

```maven
mvn clean package
```
![](https://i.postimg.cc/y81kdY2Q/Capture2.png)

ó

```maven
mvn clean install
```


3) Ejecutamos el proyecto utilizando los comandos en el cmd o corremos directamente desde la ide.

```heroku
heroku local web
```

![](https://i.postimg.cc/Ss2XhTHm/1.png)


4) abrirlo en el navegador utilizando localhost con la ruta del api localhost:5000/Clima o Localhost:5000/consultas?lugar=

![](https://i.postimg.cc/bryKnW8h/Capture4.png)

lo unico que toca tener cuidado es con la rutas adicionales para llegar a el API:

- /Clima
- /consultar?lugar="Lugar"

### Desplegando de forma remota.

Ya se encuentra desplguegado de forma remota, utilizando el recurso de heroku se esta cumpliendo esta funcion por lo que paracorrer el API o el recurso web
se puede lograr ingresando la url:

**https://climaarep-p.herokuapp.com/**

![](https://i.postimg.cc/8c7mxmmv/Capture5.png)

lo unico que toca tener cuidado es con la rutas adicionales para llegar a el API:

- /Clima
![](https://i.postimg.cc/xqrMhDCr/Capture6.png)

- /consultar?lugar="Lugar"
![](https://i.postimg.cc/nzCdtxHM/Capture7.png)

![](https://i.postimg.cc/hj62DYjs/Capture8.png)

![](https://i.postimg.cc/zvh0VkHr/Capture9.png)

## Licencia

los detalles se encuentran dentro de (LICENSE.txt).