INSERT INTO CLIENTE VALUES
( '1', 'Perez', 'HA'),
( '2', 'Maria', 'HA'),
( '3', 'Pedro', 'HA'),
( '4', 'Juan', 'HA');

INSERT INTO PROVEEDOR VALUES
('1000', 'Almacen Pablo', 'HA'),
('2000', 'Almacen Jorge', 'HA'),
('3000', 'Almacen Jose', 'HA');

INSERT INTO TEMPORADA VALUES
( 'Primavera 2016', '2016-01-01'),
( 'Verano 2016', '2016-03-31'),
( 'Otoño 2016', '2016-06-30'), 
('Invierno 2016', '2016-09-30');

INSERT INTO MATERIAL VALUES
( 'Agujas', 'HA', 100, 20, 1, '1000'),
( 'Botones', 'HA', 50, 100, 1, '1000'),
( 'Hilo', 'HA', 1000, 200, 0.5, '2000'),
( 'Tela Lisa', 'HA', 20, 100, 0.5, '2000'),
( 'Cuero', 'HA', 100, 30, 2, '3000'),
( 'Tiza', 'HA', 10, 50, 1, '3000'),
( 'Cierre', 'HA', 200, 500, 1, '3000'),
( 'Dobles', 'HA', 20, 100, 3, '3000'),
( 'Tela Dura', 'HA', 200, 20, 5, '2000');

INSERT INTO PRENDA VALUES
( 'Prenda1', 1, 'HA'),
( 'Prenda2', 1, 'HA'),
( 'Prenda3', 2, 'HA'),
( 'Prenda4', 12, 'HA'),
( 'Prenda5', 1, 'HA'),
( 'Prenda6', 5, 'HA'),
( 'Prenda7', 1, 'HA');

INSERT INTO PRENDA_TEMPORADA VALUES
(1, 1, 1),
(2, 2, 4),
(3, 5, 3),
(4, 2, 1),
(5, 5, 1),
(6, 1, 2);

INSERT INTO MATERIAL_ITEM VALUES
(1, 2, 1),
(1, 3, 1),
(2, 1, 1),
(2, 2, 1),
(3, 1, 5),
(3, 2, 5),
(3, 3, 5),
(4, 1, 1),
(4, 2, 1),
(4, 4, 1),
(5, 3, 1),
(5, 7, 1),
(6, 1, 1),
(6, 2, 1),
(6, 6, 1);