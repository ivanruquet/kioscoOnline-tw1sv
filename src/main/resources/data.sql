INSERT INTO Usuario
    (id,nombre,apellido,telefono, email, password, rol, activo)
VALUES(null, 'Pepe','Sujeto',1112341234,'test@unlam.edu.ar', 'test', 'ADMIN', true);

-- Insert de Categorías
INSERT INTO CategoriaProductos (id, nombreCategoria)
VALUES (1, 'Golosina');

INSERT INTO CategoriaProductos (id, nombreCategoria)
VALUES (2, 'Bebidas');

INSERT INTO CategoriaProductos (id, nombreCategoria)
VALUES (3, 'Librería');

INSERT INTO CategoriaProductos (id, nombreCategoria)
VALUES (4, 'Buffet');

INSERT INTO CategoriaProductos (id, nombreCategoria)
VALUES (5, 'Varios');

-- Insert de productos
INSERT INTO Producto (id, nombre, descripcion, precio,categoria_id,imagen)
VALUES (null, 'Mogul', 'Caramelos gomitas de frutas surtidas', 1200.00,1,'/img_Productos/GOMITAS-MOGUL-ROLLO-FRUTALES-X35GR-1-145.webp');

INSERT INTO Producto (id, nombre, descripcion, precio,categoria_id,imagen)
VALUES (null, 'Alfajor Jorgito', 'Alfajor de chocolate relleno con dulce de leche', 1500.00,1,'/img_Productos/alf-jorgito-negro.jpg');

INSERT INTO Producto (id, nombre, descripcion, precio,categoria_id,imagen)
VALUES (null, 'Chupetín Pico Dulce', 'Chupetín duro sabores frutales', 500.00,1,'/img_Productos/pico-dulce.jpg');



INSERT INTO Producto (id, nombre, descripcion, precio, categoria_id,imagen)
VALUES (null, 'Jugo Baggio Chico', 'Jugo de fruta listo para tomar de 200ml', 800.00, 2,'/img_Productos/jugo-baggio-multi.jpg');


INSERT INTO Producto (id, nombre, descripcion, precio, categoria_id,imagen)
VALUES (null, 'Cuaderno Éxito N°1', 'Cuaderno tapa dura de 48 hojas rayadas', 3500.00, 3,'/img_Productos/cuad-exito-n1.jpg');