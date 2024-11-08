-- schema.sql para H2 Database
-- Crear tablas base

CREATE TABLE IF NOT EXISTS CATEGORIA_PRODUCTO (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(1000) NOT NULL,
    estado CHAR(1) NOT NULL
);

CREATE TABLE IF NOT EXISTS CIUDADES (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo_postal VARCHAR(10),
    estado CHAR(1) DEFAULT 'A'
);

ALTER TABLE CIUDADES ALTER COLUMN id RESTART WITH 1;

CREATE TABLE IF NOT EXISTS GENEROS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(1000) NOT NULL,
    estado CHAR(1) NOT NULL
);

CREATE TABLE IF NOT EXISTS TIPO_DOCUMENTO (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(1000) NOT NULL,
    estado CHAR(1) NOT NULL
);

CREATE TABLE IF NOT EXISTS ROLES (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(1000) NOT NULL,
    estado CHAR(1) NOT NULL
);

CREATE TABLE IF NOT EXISTS USUARIOS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idCiudad INT NOT NULL,
    idTipoDocumento INT NOT NULL,
    idGenero INT NOT NULL,
    numeroDocumento VARCHAR(100) NOT NULL,
    nombreUsuario VARCHAR(100),
    contrasena VARCHAR(255) NOT NULL,
    estado CHAR(1) NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP,
    correoElectronico VARCHAR(100) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    Nombres VARCHAR(100) NOT NULL,
    Apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(100) NOT NULL,
    CONSTRAINT FK_USUARIOS_CIUDAD FOREIGN KEY (idCiudad) REFERENCES CIUDADES(id),
    CONSTRAINT FK_USUARIOS_TIPO_DOCUMENTO FOREIGN KEY (idTipoDocumento) REFERENCES TIPO_DOCUMENTO(id),
    CONSTRAINT FK_USUARIOS_GENERO FOREIGN KEY (idGenero) REFERENCES GENEROS(id),
    CONSTRAINT uniqueUser UNIQUE (nombreUsuario)
);

CREATE TABLE IF NOT EXISTS USUARIOS_ROLES (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idUsuario INT NOT NULL,
    idRol INT NOT NULL,
    CONSTRAINT FK_USUARIOS_ROLES_USUARIO FOREIGN KEY (idUsuario) REFERENCES USUARIOS(id),
    CONSTRAINT FK_USUARIOS_ROLES_ROL FOREIGN KEY (idRol) REFERENCES ROLES(id)
);

CREATE TABLE IF NOT EXISTS PRODUCTOS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(1000) NOT NULL,
    precioUnidad DECIMAL(10,2) NOT NULL,
    estado CHAR(1) NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP,
    url_img VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS PRODUCTOS_CATEGORIAS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idCategoriaProducto INT NOT NULL,
    idProducto INT NOT NULL,
    CONSTRAINT FK_PRODUCTOS_CATEGORIAS_CATEGORIA FOREIGN KEY (idCategoriaProducto) REFERENCES CATEGORIA_PRODUCTO(id),
    CONSTRAINT FK_PRODUCTOS_CATEGORIAS_PRODUCTO FOREIGN KEY (idProducto) REFERENCES PRODUCTOS(id)
);

CREATE TABLE IF NOT EXISTS INVENTARIO_PRODUCTOS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idProducto INT NOT NULL,
    cantidadDisponible INT NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP,
    estado CHAR(1) NOT NULL,
    CONSTRAINT FK_INVENTARIO_PRODUCTOS_PRODUCTO FOREIGN KEY (idProducto) REFERENCES PRODUCTOS(id)
);

CREATE TABLE IF NOT EXISTS ORDEN_COMPRA (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idUsuario INT NOT NULL,
    subTotalCompra DECIMAL(10,2) NOT NULL,
    totalCompra DECIMAL(10,2) NOT NULL,
    estado CHAR(1) NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP,
    descuento DECIMAL(10,2),
    CONSTRAINT FK_ORDEN_COMPRA_USUARIO FOREIGN KEY (idUsuario) REFERENCES USUARIOS(id)
);

CREATE TABLE IF NOT EXISTS DETALLES_ORDEN (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idOrdenCompra INT NOT NULL,
    idProducto INT NOT NULL,
    cantidadProducto INT NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP,
    unidadPrecio DECIMAL(10,2) NOT NULL,
    subTotal DECIMAL(10,2) NOT NULL,
    estado CHAR(1) NOT NULL,
    CONSTRAINT FK_DETALLES_ORDEN_ORDEN FOREIGN KEY (idOrdenCompra) REFERENCES ORDEN_COMPRA(id),
    CONSTRAINT FK_DETALLES_ORDEN_PRODUCTO FOREIGN KEY (idProducto) REFERENCES PRODUCTOS(id)
);

CREATE TABLE IF NOT EXISTS PAGOS_ORDEN (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idOrdenCompra INT NOT NULL,
    JsonRespuesta CLOB NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP,
    estado VARCHAR(1) NOT NULL,
    CONSTRAINT PAGOS_ORDEN_ORDEN_COMPRA FOREIGN KEY (idOrdenCompra) REFERENCES ORDEN_COMPRA(id)
);

-- Datos iniciales básicos
-- En H2, usamos INSERT con ON DUPLICATE KEY UPDATE en lugar de MERGE

-- Para CATEGORIA_PRODUCTO
MERGE INTO CATEGORIA_PRODUCTO (id, nombre, descripcion, estado)
KEY(id)
VALUES
(1, 'Tecnología', 'Productos tecnológicos y electrónicos', 'A'),
(2, 'Hogar', 'Artículos para el hogar y decoración', 'A'),
(3, 'Deportes', 'Equipamiento y ropa deportiva', 'A');

-- Para CIUDADES
MERGE INTO CIUDADES (id, nombre, codigo_postal, estado)
KEY(id)
VALUES
(1, 'Bogotá', '110111', 'A'),
(2, 'Medellín', '050001', 'A'),
(3, 'Cali', '760001', 'A');

-- Para GENEROS
MERGE INTO GENEROS (id, nombre, descripcion, estado)
KEY(id)
VALUES
(1, 'M', 'Masculino', 'A'),
(2, 'F', 'Femenino', 'A'),
(3, 'O', 'Otro', 'A');

-- Para TIPO_DOCUMENTO
MERGE INTO TIPO_DOCUMENTO (id, nombre, descripcion, estado)
KEY(id)
VALUES
(1, 'CC', 'Cédula de Ciudadanía', 'A'),
(2, 'CE', 'Cédula de Extranjería', 'A'),
(3, 'PA', 'Pasaporte', 'A');

-- Para ROLES
MERGE INTO ROLES (id, nombre, descripcion, estado)
KEY(id)
VALUES
(1, 'ADMINISTRADOR', 'Usuario con permisos administrativos completos', 'A'),
(2, 'CLIENTE', 'Usuario que puede realizar compras y efectuar pagos', 'A'),
(3, 'VISUALIZADOR', 'Usuario con permisos de solo lectura de registros', 'A');

-- Para PRODUCTOS
MERGE INTO PRODUCTOS (id, nombre, descripcion, precioUnidad, estado, create_at, url_img)
KEY(id)
VALUES
(1, 'Smart TV 55"', 'Televisor LED 4K con Android TV', 899.00, 'A', CURRENT_TIMESTAMP, 'assets/Productos/smart_tv.webp'),
(2, 'ROPA DE MOP', 'ASDFASFD', 500.00, 'A', CURRENT_TIMESTAMP, 'assets/Productos/licuadora.webp'),
(3, 'Set de Ollas', 'Juego de 6 ollas antiadherentes', 159.00, 'A', CURRENT_TIMESTAMP, 'assets/Productos/set-ollas.webp'),
(4, 'Bicicleta Montaña', 'Bicicleta todo terreno, 21 velocidades', 499.00, 'I', CURRENT_TIMESTAMP, 'assets/Productos/bicicleta.webp'),
(5, 'Tablet 10"', 'Tablet Android con 128GB almacenamiento', 299.00, 'A', CURRENT_TIMESTAMP, 'assets/Productos/tablet.webp'),
(6, 'Zapatillas Running', 'Zapatillas profesionales para correr', 95.00, 'A', CURRENT_TIMESTAMP, 'assets/Productos/zapatilla-deportiva.webp'),
(7, 'PRUEBA123', 'Máquina multifuncional ejercicios', 799.00, 'A', CURRENT_TIMESTAMP, 'assets/Productos/maquina-ejercisio.webp');

-- Para PRODUCTOS_CATEGORIAS
MERGE INTO PRODUCTOS_CATEGORIAS (idCategoriaProducto, idProducto)
KEY(idCategoriaProducto, idProducto)
VALUES
(1, 1), (2, 2), (2, 3), (3, 4), (1, 5), (3, 6), (3, 7);

-- Para INVENTARIO_PRODUCTOS
MERGE INTO INVENTARIO_PRODUCTOS (idProducto, cantidadDisponible, create_at, estado)
KEY(idProducto)
VALUES
(1, 45, CURRENT_TIMESTAMP, 'A'),
(2, 30, CURRENT_TIMESTAMP, 'A'),
(3, 10, CURRENT_TIMESTAMP, 'A'),
(4, 40, CURRENT_TIMESTAMP, 'A'),
(5, 15, CURRENT_TIMESTAMP, 'A'),
(6, 45, CURRENT_TIMESTAMP, 'A'),
(7, 60, CURRENT_TIMESTAMP, 'A');

-- Para USUARIOS
MERGE INTO USUARIOS (id, idCiudad, idTipoDocumento, idGenero, numeroDocumento, nombreUsuario, contrasena, estado, correoElectronico, direccion, Nombres, Apellidos, telefono)
KEY(id)
VALUES
(1, 1, 1, 1, '1081827159', 'usuarioprueba', 'QU5ERVJTT05TMTk5NnR1U2FsdEZpam8xMjM=', 'A', 'test@test.com', 'Direccion de prueba 123', 'Usuario', 'De Prueba', '3001234567');

-- Para USUARIOS_ROLES
MERGE INTO USUARIOS_ROLES (idUsuario, idRol)
KEY(idUsuario, idRol)
VALUES
(1, 2);

-- Para ORDEN_COMPRA
MERGE INTO ORDEN_COMPRA (id, idUsuario, subTotalCompra, totalCompra, estado, descuento)
KEY(id)
VALUES
(1, 1, 1500.00, 1425.00, 'A', 75.00);

-- Para DETALLES_ORDEN
MERGE INTO DETALLES_ORDEN (idOrdenCompra, idProducto, cantidadProducto, unidadPrecio, subTotal, estado)
KEY(idOrdenCompra, idProducto)
VALUES
(1, 1, 1, 899.00, 899.00, 'A'),
(1, 2, 2, 300.50, 601.00, 'A');

-- Para PAGOS_ORDEN
MERGE INTO PAGOS_ORDEN (idOrdenCompra, JsonRespuesta, estado)
KEY(idOrdenCompra)
VALUES
(1, '{"success": true, "data": {"ORDEN": {"ordenId": "ORD-2024-001", "estado": "COMPLETADO", "mensaje": "Compra realizada exitosamente", "fechaCompra": "2024-03-20T10:30:00", "total": 1500.00}}, "message": "Orden procesada correctamente"}', 'A');