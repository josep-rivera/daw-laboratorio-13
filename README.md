# 🚀 Microservicios con Spring Boot, Eureka y API Gateway

Sistema de microservicios para gestión de productos y categorías utilizando Spring Cloud.

## 📦 Microservicios

| Servicio          | Puerto | Base de Datos | Descripción                            |
|-------------------|--------|---------------|----------------------------------------|
| Eureka Server     | 8761   | -             | Registro y descubrimiento de servicios |
| API Gateway       | 8080   | -             | Punto de entrada único                 |
| Categoria Service | 8081   | db_categoria  | Gestión de categorías                  |
| Producto Service  | 8082   | db_producto   | Gestión de productos                   |

## 🗄️ Configuración de Base de Datos

### 1. Crear las bases de datos en MySQL:

```sql
CREATE DATABASE db_categoria;
CREATE DATABASE db_producto;
```

### 2. Configuración de acceso:
- **Usuario:** root
- **Contraseña:** 12345
- **Puerto:** 3306

## 🚀 Orden de Inicio

**IMPORTANTE:** Inicia los servicios en este orden exacto:

### 1️⃣ Eureka Server
```bash
cd eureka-server
mvn spring-boot:run
```
- Espera 10-15 segundos
- Verifica en: http://localhost:8761

### 2️⃣ Categoria Service
```bash
cd categoria-service
mvn spring-boot:run
```
- Espera que aparezca en Eureka

### 3️⃣ Producto Service
```bash
cd producto-service
mvn spring-boot:run
```
- Espera que aparezca en Eureka

### 4️⃣ API Gateway
```bash
cd api-gateway
mvn spring-boot:run
```

## ✅ Verificación

1. Abre http://localhost:8761
2. Verifica que aparezcan registrados:
    - **CATEGORIA-SERVICE**
    - **PRODUCTO-SERVICE**
    - **API-GATEWAY**

## 📡 Endpoints - Categorías

**Base URL:** `http://localhost:8080/api/categorias`

### Listar todas las categorías
```http
GET http://localhost:8080/api/categorias
```

### Obtener categoría por ID
```http
GET http://localhost:8080/api/categorias/1
```

### Crear categoría
```http
POST http://localhost:8080/api/categorias
Content-Type: application/json

{
    "nombre": "Electrónica"
}
```

### Actualizar categoría
```http
PUT http://localhost:8080/api/categorias/1
Content-Type: application/json

{
    "nombre": "Electrónica y Tecnología"
}
```

### Eliminar categoría
```http
DELETE http://localhost:8080/api/categorias/1
```

## 📡 Endpoints - Productos

**Base URL:** `http://localhost:8080/api/productos`

### Listar todos los productos
```http
GET http://localhost:8080/api/productos
```

### Obtener producto por ID
```http
GET http://localhost:8080/api/productos/1
```

### Obtener producto con categoría (Comunicación entre microservicios)
```http
GET http://localhost:8080/api/productos/1/con-categoria
```
**Respuesta esperada:**
```json
{
    "producto": {
        "id": 1,
        "nombre": "Laptop HP",
        "precio": 899.99,
        "categoriaId": 1
    },
    "categoria": {
        "id": 1,
        "nombre": "Electrónica"
    }
}
```

### Crear producto
```http
POST http://localhost:8080/api/productos
Content-Type: application/json

{
    "nombre": "Laptop HP",
    "precio": 899.99,
    "categoriaId": 1
}
```

### Actualizar producto
```http
PUT http://localhost:8080/api/productos/1
Content-Type: application/json

{
    "nombre": "Laptop HP Pavilion",
    "precio": 799.99,
    "categoriaId": 1
}
```

### Eliminar producto
```http
DELETE http://localhost:8080/api/productos/1
```

## 📝 Datos de Ejemplo

### Categorías
```json
{"nombre": "Electrónica"}
{"nombre": "Ropa"}
{"nombre": "Alimentos"}
{"nombre": "Hogar"}
{"nombre": "Deportes"}
```

### Productos
```json
{"nombre": "Laptop HP", "precio": 899.99, "categoriaId": 1}
{"nombre": "Mouse Logitech", "precio": 25.50, "categoriaId": 1}
{"nombre": "Teclado Mecánico", "precio": 89.99, "categoriaId": 1}
{"nombre": "Camiseta Nike", "precio": 35.00, "categoriaId": 2}
{"nombre": "Pantalón Adidas", "precio": 55.00, "categoriaId": 2}
{"nombre": "Arroz 1kg", "precio": 3.50, "categoriaId": 3}
{"nombre": "Aceite 1L", "precio": 5.99, "categoriaId": 3}
```

## 🧪 Flujo de Pruebas en Postman

### Paso 1: Crear Categorías
1. POST `/api/categorias` - Crear "Electrónica"
2. POST `/api/categorias` - Crear "Ropa"
3. POST `/api/categorias` - Crear "Alimentos"
4. GET `/api/categorias` - Verificar lista

### Paso 2: Crear Productos
1. POST `/api/productos` - Crear "Laptop HP" (categoriaId: 1)
2. POST `/api/productos` - Crear "Mouse Logitech" (categoriaId: 1)
3. POST `/api/productos` - Crear "Camiseta Nike" (categoriaId: 2)
4. GET `/api/productos` - Verificar lista

### Paso 3: Probar Comunicación entre Microservicios
1. GET `/api/productos/1/con-categoria` - Ver producto con su categoría
2. Verifica que la respuesta incluya tanto el producto como la categoría completa

### Paso 4: Actualizar y Eliminar
1. PUT `/api/categorias/1` - Actualizar nombre
2. PUT `/api/productos/1` - Actualizar precio
3. DELETE `/api/productos/3` - Eliminar un producto
4. DELETE `/api/categorias/3` - Eliminar una categoría

## 📂 Estructura del Proyecto

```
microservicios-app/
├── eureka-server/
│   └── src/main/
│       ├── java/
│       └── resources/
│           └── application.properties
├── api-gateway/
│   └── src/main/
│       ├── java/
│       └── resources/
│           └── application.properties
├── categoria-service/
│   └── src/main/
│       ├── java/
│       │   └── com/microservices/
│       │       ├── controller/
│       │       ├── model/
│       │       └── repository/
│       └── resources/
│           └── application.properties
└── producto-service/
    └── src/main/
        ├── java/
        │   └── com/microservices/
        │       ├── client/
        │       ├── controller/
        │       ├── dto/
        │       ├── model/
        │       ├── repository/
        │       └── service/
        └── resources/
            └── application.properties
```
