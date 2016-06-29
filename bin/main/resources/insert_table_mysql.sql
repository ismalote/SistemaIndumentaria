INSERT INTO `cliente` (`codigoCliente`, `dni`, `nombre`, `estado`) VALUES
(1, '1', 'Perez', 'HA'),
(2, '2', 'Maria', 'HA'),
(3, '3', 'Pedro', 'HA'),
(4, '4', 'Juan', 'HA');

INSERT INTO `proveedor` (`cuit`, `razonSocial`, `estado`) VALUES
('1000', 'Almacen Pablo', 'HA'),
('2000', 'Almacen Jorge', 'HA'),
('3000', 'Almacen Jose', 'HA');

INSERT INTO `temporada` (`codigoTemporada`, `nombreTemporada`, `fechaFinalizacion`) VALUES
(1, 'Primavera', '2016-02-29 03:00:00'),
(2, 'Verano', '2016-05-31 03:00:00'),
(3, 'Otoño', '2016-08-31 03:00:00'),
(4, 'Invierno', '2016-05-13 08:53:11');

INSERT INTO `material` (`codigoMaterial`, `nombreMaterial`, `estado`, `stock`, `puntoPedido`, `costo`, `PROVEEDOR_cuit`) VALUES
(2, 'Agujas', 'HA', 100, 20, 1, '1000'),
(3, 'Botones', 'HA', 50, 100, 1, '1000'),
(4, 'Hilo', 'HA', 1000, 200, 0.5, '2000'),
(5, 'Tela Lisa', 'HA', 20, 100, 0.5, '2000'),
(6, 'Cuero', 'HA', 100, 30, 2, '3000'),
(7, 'Tiza', 'HA', 10, 50, 1, '3000'),
(8, 'Cierre', 'HA', 200, 500, 1, '3000'),
(9, 'Dobles', 'HA', 20, 100, 3, '3000'),
(10, 'Tela Dura', 'HA', 200, 20, 5, '2000');

INSERT INTO `prenda` (`codigoPrenda`, `nombrePrenda`, `stock`, `estado`) VALUES
(4, 'Prenda1', 1, 'HA'),
(5, 'Prenda2', 1, 'HA'),
(6, 'Prenda3', 2, 'HA'),
(7, 'Prenda4', 12, 'HA'),
(8, 'Prenda5', 1, 'HA'),
(9, 'Prenda6', 5, 'HA'),
(10, 'Prenda7', 1, 'HA');

INSERT INTO `prenda_temporada` (`PRENDA_codigoPrenda`, `porcentajeIncremento`, `TEMPORADA_codigoTemporada`) VALUES
(5, 1, 1),
(6, 2, 4),
(7, 5, 3),
(8, 2, 1),
(9, 5, 1),
(10, 1, 2);

INSERT INTO `material_item` (`PRENDA_codigoPrenda`, `MATERIAL_codigoMaterial`, `cantidad`) VALUES
(7, 2, 1),
(8, 2, 1),
(9, 2, 5),
(10, 2, 1),
(10, 3, 1),
(10, 6, 1),
(10, 7, 1);