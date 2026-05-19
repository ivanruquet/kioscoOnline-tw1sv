INSERT INTO Usuario
    (id,nombre,apellido,celular, email, password, rol, activo)
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
INSERT INTO Producto (id, nombre, descripcion, precio,categoria_id,imagen,cantidad)
VALUES (null, 'Rollo Mogul', 'Gomitas de frutas surtidas', 600.00,1,
        'imagenes/img_Productos/GOMITAS-MOGUL-ROLLO-FRUTALES-X35GR-1-145.webp',5);

INSERT INTO Producto (id, nombre, descripcion, precio,categoria_id,imagen,cantidad)
VALUES (null, 'Alfajor Jorgito Negro', 'Alfajor de chocolate relleno con dulce de leche', 1200.00,1,
        'imagenes/img_Productos/alf-jorgito-negro.jpg',5);

INSERT INTO Producto (id, nombre, descripcion, precio,categoria_id,imagen,cantidad)
VALUES (null, 'Chupetín Pico Dulce', 'Chupetín duro sabor frutal', 500.00,1,
        'imagenes/img_Productos/pico-dulce.jpg',5);



INSERT INTO Producto (id, nombre, descripcion, precio, categoria_id,imagen,cantidad)
VALUES (null, 'Jugo Baggio 200ml Multifruta', 'Jugo de fruta listo para tomar de 200ml', 800.00, 2,
        'imagenes/img_Productos/jugo-baggio-multi.jpg',5);


INSERT INTO Producto (id, nombre, descripcion, precio, categoria_id,imagen,cantidad)
VALUES (null, 'Cuaderno Éxito N°1', 'Cuaderno tapa dura de 48 hojas rayadas', 8000.00, 3,
        'imagenes/img_Productos/cuad-exito-n1.jpg',5);