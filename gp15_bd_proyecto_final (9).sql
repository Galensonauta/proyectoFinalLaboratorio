-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-11-2025 a las 17:17:17
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gp15_bd_proyecto_final`
--
CREATE DATABASE IF NOT EXISTS `gp15_bd_proyecto_final` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gp15_bd_proyecto_final`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comprador`
--

CREATE TABLE `comprador` (
  `dni` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `fechaNac` date NOT NULL,
  `pass` varchar(30) NOT NULL,
  `medioPago` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `comprador`
--

INSERT INTO `comprador` (`dni`, `nombre`, `fechaNac`, `pass`, `medioPago`) VALUES
(123456785, 'Tomás Puw', '1990-07-23', '12345', 'Debito'),
(123456786, 'Evelyn Cetera', '1990-07-23', '12345', 'Debito'),
(123456787, 'Santiago Girardi Correa', '1990-07-23', '12345', 'Debito'),
(123456788, 'Enzo Fornes', '1990-07-23', '12345', 'Debito'),
(123456789, 'Matias Correa', '1990-07-23', '12345677', 'efectivo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_ticket`
--

CREATE TABLE `detalle_ticket` (
  `idDetalle` int(11) NOT NULL,
  `codD` int(11) NOT NULL,
  `idProyeccion` int(11) NOT NULL,
  `cantidad` int(30) NOT NULL,
  `subTotal` decimal(10,2) NOT NULL,
  `fechProyeccion` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle_ticket`
--

INSERT INTO `detalle_ticket` (`idDetalle`, `codD`, `idProyeccion`, `cantidad`, `subTotal`, `fechProyeccion`) VALUES
(1, 9, 1, 3, 36000.00, '2025-11-10'),
(2, 10, 1, 3, 36000.00, '2025-11-10'),
(3, 11, 1, 3, 36000.00, '2025-11-10'),
(4, 12, 1, 3, 36000.00, '2025-11-10'),
(5, 13, 1, 3, 36000.00, '2025-11-10'),
(6, 34, 1, 2, 24000.00, '2025-10-30'),
(7, 35, 1, 2, 24000.00, '2025-10-30'),
(8, 36, 1, 2, 24000.00, '2025-10-30'),
(9, 37, 1, 2, 24000.00, '2025-10-30'),
(10, 38, 1, 3, 36000.00, '2025-10-30');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_ticket_lugares`
--

CREATE TABLE `detalle_ticket_lugares` (
  `idDetalleTicket` int(11) NOT NULL,
  `codLugar` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle_ticket_lugares`
--

INSERT INTO `detalle_ticket_lugares` (`idDetalleTicket`, `codLugar`) VALUES
(5, 8),
(5, 9),
(5, 10),
(10, 11),
(10, 12),
(10, 13);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar_asiento`
--

CREATE TABLE `lugar_asiento` (
  `codLugar` int(11) NOT NULL,
  `filaAsiento` varchar(11) NOT NULL,
  `numeroAsiento` int(11) NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `proyeccion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `lugar_asiento`
--

INSERT INTO `lugar_asiento` (`codLugar`, `filaAsiento`, `numeroAsiento`, `estado`, `proyeccion`) VALUES
(8, 'A', 7, 1, 1),
(9, 'A', 8, 1, 1),
(10, 'A', 9, 1, 1),
(11, 'A', 4, 1, 1),
(12, 'A', 5, 1, 1),
(13, 'A', 6, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pelicula`
--

CREATE TABLE `pelicula` (
  `titulo` varchar(30) NOT NULL,
  `director` varchar(30) NOT NULL,
  `actores` varchar(80) NOT NULL,
  `origen` varchar(30) NOT NULL,
  `genero` varchar(30) NOT NULL,
  `estreno` date NOT NULL,
  `enCartelera` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pelicula`
--

INSERT INTO `pelicula` (`titulo`, `director`, `actores`, `origen`, `genero`, `estreno`, `enCartelera`) VALUES
('Pelicula de prueba 111111', 'Juanito', 'Varios', 'ES', 'COMEDIA', '2025-03-20', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proyeccion`
--

CREATE TABLE `proyeccion` (
  `idProyeccion` int(11) NOT NULL,
  `pelicula` varchar(30) NOT NULL,
  `idioma` varchar(30) NOT NULL,
  `es3D` tinyint(1) NOT NULL,
  `subtitulada` tinyint(1) NOT NULL,
  `horaInicio` datetime NOT NULL,
  `horaFin` datetime NOT NULL,
  `lugaresDisponibles` int(11) NOT NULL,
  `sala` int(11) NOT NULL,
  `precioLugar` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proyeccion`
--

INSERT INTO `proyeccion` (`idProyeccion`, `pelicula`, `idioma`, `es3D`, `subtitulada`, `horaInicio`, `horaFin`, `lugaresDisponibles`, `sala`, `precioLugar`) VALUES
(1, 'Pelicula de prueba 111111', 'ES', 1, 0, '2025-10-30 20:00:00', '2025-10-30 22:30:00', 170, 1, 12000),
(2, 'Pelicula de prueba 111111', 'ES', 1, 0, '2025-10-30 20:00:00', '2025-10-30 22:30:00', 170, 1, 12000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sala`
--

CREATE TABLE `sala` (
  `nroSala` int(11) NOT NULL,
  `apta3D` tinyint(1) NOT NULL,
  `capacidad` int(11) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `sala`
--

INSERT INTO `sala` (`nroSala`, `apta3D`, `capacidad`, `estado`) VALUES
(1, 1, 230, 1),
(2, 0, 150, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ticket_compra`
--

CREATE TABLE `ticket_compra` (
  `idTicket` int(11) NOT NULL,
  `fechCompra` date NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `comprador` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ticket_compra`
--

INSERT INTO `ticket_compra` (`idTicket`, `fechCompra`, `monto`, `comprador`) VALUES
(7, '2025-11-02', 36000.00, 123456789),
(8, '2025-11-02', 36000.00, 123456789),
(9, '2025-11-02', 36000.00, 123456789),
(10, '2025-11-02', 36000.00, 123456789),
(11, '2025-11-02', 36000.00, 123456789),
(12, '2025-11-02', 36000.00, 123456789),
(13, '2025-11-02', 36000.00, 123456789),
(15, '2025-11-19', 0.00, 123456785),
(16, '2025-11-19', 0.00, 123456785),
(17, '2025-11-19', 0.00, 123456785),
(18, '2025-11-19', 0.00, 123456785),
(19, '2025-11-19', 43560.00, 123456785),
(20, '2025-11-19', 43560.00, 123456785),
(21, '2025-11-19', 29040.00, 123456785),
(22, '2025-11-19', 29040.00, 123456785),
(23, '2025-11-19', 43560.00, 123456785),
(24, '2025-11-19', 29040.00, 123456785),
(25, '2025-11-19', 29040.00, 123456785),
(26, '2025-11-19', 14520.00, 123456785),
(27, '2025-11-19', 29040.00, 123456785),
(28, '2025-11-19', 14520.00, 123456785),
(29, '2025-11-19', 29040.00, 123456785),
(30, '2025-11-19', 14520.00, 123456785),
(31, '2025-11-19', 29040.00, 123456785),
(32, '2025-11-19', 29040.00, 123456785),
(33, '2025-11-19', 14520.00, 123456785),
(34, '2025-11-19', 29040.00, 123456785),
(35, '2025-11-19', 29040.00, 123456785),
(36, '2025-11-19', 29040.00, 123456785),
(37, '2025-11-19', 29040.00, 123456785),
(38, '2025-11-19', 43560.00, 123456785);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `comprador`
--
ALTER TABLE `comprador`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `detalle_ticket`
--
ALTER TABLE `detalle_ticket`
  ADD PRIMARY KEY (`idDetalle`),
  ADD UNIQUE KEY `codD` (`codD`) USING BTREE,
  ADD KEY `idProyeccion` (`idProyeccion`);

--
-- Indices de la tabla `detalle_ticket_lugares`
--
ALTER TABLE `detalle_ticket_lugares`
  ADD KEY `detalle_ticket_lugares_ibfk_1` (`idDetalleTicket`),
  ADD KEY `detalle_ticket_lugares_ibfk_2` (`codLugar`);

--
-- Indices de la tabla `lugar_asiento`
--
ALTER TABLE `lugar_asiento`
  ADD PRIMARY KEY (`codLugar`),
  ADD KEY `proyeccion` (`proyeccion`);

--
-- Indices de la tabla `pelicula`
--
ALTER TABLE `pelicula`
  ADD PRIMARY KEY (`titulo`);

--
-- Indices de la tabla `proyeccion`
--
ALTER TABLE `proyeccion`
  ADD PRIMARY KEY (`idProyeccion`),
  ADD KEY `pelicula` (`pelicula`),
  ADD KEY `sala` (`sala`);

--
-- Indices de la tabla `sala`
--
ALTER TABLE `sala`
  ADD PRIMARY KEY (`nroSala`);

--
-- Indices de la tabla `ticket_compra`
--
ALTER TABLE `ticket_compra`
  ADD PRIMARY KEY (`idTicket`),
  ADD KEY `comprador` (`comprador`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `detalle_ticket`
--
ALTER TABLE `detalle_ticket`
  MODIFY `idDetalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `lugar_asiento`
--
ALTER TABLE `lugar_asiento`
  MODIFY `codLugar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `proyeccion`
--
ALTER TABLE `proyeccion`
  MODIFY `idProyeccion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `ticket_compra`
--
ALTER TABLE `ticket_compra`
  MODIFY `idTicket` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_ticket`
--
ALTER TABLE `detalle_ticket`
  ADD CONSTRAINT `detalle_ticket_ibfk_2` FOREIGN KEY (`codD`) REFERENCES `ticket_compra` (`idTicket`) ON DELETE CASCADE,
  ADD CONSTRAINT `detalle_ticket_ibfk_3` FOREIGN KEY (`idProyeccion`) REFERENCES `proyeccion` (`idProyeccion`);

--
-- Filtros para la tabla `detalle_ticket_lugares`
--
ALTER TABLE `detalle_ticket_lugares`
  ADD CONSTRAINT `detalle_ticket_lugares_ibfk_1` FOREIGN KEY (`idDetalleTicket`) REFERENCES `detalle_ticket` (`idDetalle`) ON DELETE CASCADE,
  ADD CONSTRAINT `detalle_ticket_lugares_ibfk_2` FOREIGN KEY (`codLugar`) REFERENCES `lugar_asiento` (`codLugar`) ON DELETE CASCADE;

--
-- Filtros para la tabla `lugar_asiento`
--
ALTER TABLE `lugar_asiento`
  ADD CONSTRAINT `lugar_asiento_ibfk_1` FOREIGN KEY (`proyeccion`) REFERENCES `proyeccion` (`idProyeccion`);

--
-- Filtros para la tabla `proyeccion`
--
ALTER TABLE `proyeccion`
  ADD CONSTRAINT `proyeccion_ibfk_1` FOREIGN KEY (`pelicula`) REFERENCES `pelicula` (`titulo`),
  ADD CONSTRAINT `proyeccion_ibfk_2` FOREIGN KEY (`sala`) REFERENCES `sala` (`nroSala`);

--
-- Filtros para la tabla `ticket_compra`
--
ALTER TABLE `ticket_compra`
  ADD CONSTRAINT `ticket_compra_ibfk_1` FOREIGN KEY (`comprador`) REFERENCES `comprador` (`dni`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
