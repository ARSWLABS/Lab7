# 🏗️ Blueprints API

**Autores:** Diego Hernando Chicuazuque Castiblanco y Juan Esteban Cancelado Sánchez

## 📌 Descripción

**Blueprints API** es una aplicación **RESTful** diseñada para la gestión eficiente de planos (blueprints). Permite realizar operaciones **CRUD** (Crear, Leer, Actualizar, Eliminar) y está optimizada para entornos concurrentes.

---

## 📖 Tabla de Contenidos

- [✨ Características](#-características)
- [🛠️ Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [🚀 Instalación](#-instalación)
- [📌 Uso](#-uso)
- [🔗 Endpoints](#-endpoints)
- [⚡ Análisis de Concurrencia](#-análisis-de-concurrencia)
- [🧪 Pruebas](#-pruebas)
- [🤝 Contribuciones](#-contribuciones)
- [📜 Licencia](#-licencia)

---

## ✨ Características

✔️ Gestión de planos con operaciones **CRUD**.
✔️ Soporte para **múltiples autores** y planos.
✔️ Manejo de **concurrencia** para garantizar la integridad de los datos.
✔️ Respuestas en formato **JSON**.

---

## 🛠️ Tecnologías Utilizadas

🔹 **Java 1.8**
🔹 **Spring Boot**
🔹 **Maven**
🔹 **JUnit** (para pruebas)
🔹 **Mockito** (para pruebas unitarias)
🔹 **Concurrent Collections** (para manejo de concurrencia)

---

## 🚀 Instalación

### 📥 Clonar el repositorio:
```bash
git clone https://github.com/ARSWLABS/Lab5.git
```

### 📂 Navegar al directorio del proyecto:
```bash
cd blueprints-api
```

### 🔧 Compilar el proyecto con Maven:
```bash
mvn clean install
```

### ▶️ Ejecutar la aplicación:
```bash
mvn spring-boot:run
```
ESTA CORRIENDO PUERTO 8080
---

## 📌 Uso

Una vez que la aplicación esté en ejecución, puedes interactuar con la API utilizando herramientas como **Postman** o `curl`.

### 📍 Ejemplo de Petición **POST**
```bash
curl -i -X POST -H "Content-Type: application/json" -H "Accept: application/json" \
http://localhost:8080/blueprints -d '{
    "name": "Blueprint Example",
    "author": "Juan",
    "points": [
        {"x": 1, "y": 2},
        {"x": 3, "y": 4}
    ]
}'
```

---

## 🔗 Endpoints

📍 **Obtener todos los planos**
```
GET /blueprints
```
![image](https://github.com/user-attachments/assets/ccc1f1f2-24ef-4994-a30e-8e668e3ec591)

📍 **Obtener todos los planos de un autor específico**
```
GET /blueprints/{author}
```
![image](https://github.com/user-attachments/assets/9d2bd076-fa09-473d-a3f5-d9eecfd9151e)

📍 **Obtener un plano específico de un autor**
```
GET /blueprints/{author}/{plano}
```
![image](https://github.com/user-attachments/assets/2744df5b-bf1d-42d1-bfc6-e07bbbe7eb4a)

📍 **Agregar un nuevo plano**
```
POST /blueprints
```
![image](https://github.com/user-attachments/assets/86a205a2-5d5c-4c8d-bd54-9f0144402fc2)
![image](https://github.com/user-attachments/assets/17260818-74d6-443d-9690-fa428b55f8e7)
![image](https://github.com/user-attachments/assets/1f5e31e4-ead7-40d6-8647-b190b32cfcc9)

---

## ⚡ Análisis de Concurrencia

El componente **BlueprintsRESTAPI** está diseñado para manejar múltiples peticiones simultáneamente. Se han implementado:

✅ **Colecciones seguras para hilos**
✅ **Métodos atómicos** para evitar condiciones de carrera

Para más detalles, consulta el archivo [ANALISIS_CONCURRENCIA.txt](ANALISIS_CONCURRENCIA.txt).

---

## 🧪 Pruebas

Las pruebas unitarias están implementadas utilizando **JUnit 5** y **Mockito**.

Para ejecutar las pruebas, usa:
```bash
mvn test
```

---

## 🤝 Contribuciones

Las contribuciones son **bienvenidas**. Sigue estos pasos para contribuir:

1️⃣ Haz un **fork** del proyecto.
2️⃣ Crea una nueva rama (`git checkout -b feature/nueva-caracteristica`).
3️⃣ Realiza tus cambios y haz **commit** (`git commit -m 'Agregada nueva característica'`).
4️⃣ Haz **push** a la rama (`git push origin feature/nueva-caracteristica`).
5️⃣ Abre un **Pull Request**.

---

## 📜 Licencia

Este proyecto está bajo la **Licencia MIT**. Para más detalles, consulta el archivo [LICENSE](LICENSE).

