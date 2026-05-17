INSERT INTO Usuario
    (id,nombre,apellido,telefono, email, password, rol, activo)
VALUES(null, 'Pepe','Sujeto',1112341234,'test@unlam.edu.ar', 'test', 'ADMIN', true);

-- Insert de Categorías
INSERT INTO CategoriaProductos (id, nombreCategoria)
VALUES (null, 'Golosina'); -- Probablemente tome el ID 1

INSERT INTO CategoriaProductos (id, nombreCategoria)
VALUES (null, 'Bebidas');  -- Probestamps el ID 2

INSERT INTO CategoriaProductos (id, nombreCategoria)
VALUES (null, 'Librería'); -- Probablemente tome el ID 3

INSERT INTO CategoriaProductos (id, nombreCategoria)
VALUES (null, 'Buffet');   -- Probablemente tome el ID 4

-- Insert de productos
INSERT INTO Producto (id, nombre, descripcion, precio)
VALUES (null, 'Mogul', 'Caramelos gomitas de frutas surtidas', 1200.00);


INSERT INTO Producto (id, nombre, descripcion, precio)
VALUES (null, 'Alfajor Jorgito', 'Alfajor de chocolate relleno con dulce de leche', 1500.00);

INSERT INTO Producto (id, nombre, descripcion, precio)
VALUES (null, 'Chupetín Pico Dulce', 'Chupetín duro sabores frutales', 500.00);

UPDATE Producto
SET categoria_id = 1
WHERE nombre IN ('Mogul', 'Alfajor Jorgito', 'Chupetín Pico Dulce');

-- Insert de una Bebida (Asumiendo que 'Bebidas' tiene el ID 2)
INSERT INTO Producto (id, nombre, descripcion, precio, categoria_id)
VALUES (null, 'Jugo Baggio Chico', 'Jugo de fruta listo para tomar de 200ml', 800.00, 2);

-- Insert de un artículo de Librería (Asumiendo que 'Librería' tiene el ID 3)
INSERT INTO Producto (id, nombre, descripcion, precio, categoria_id)
VALUES (null, 'Cuaderno Éxito N°1', 'Cuaderno tapa dura de 48 hojas rayadas', 3500.00, 3);