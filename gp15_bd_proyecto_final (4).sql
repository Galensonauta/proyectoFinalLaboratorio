-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 02-11-2025 a las 05:36:32
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
  `subTotal` decimal(30,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(8, 'A', 33, 1, 1);

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
('Pelicula de prueba 111111', 'Juanito', 'Varios', 'AR', 'Terror', '2025-03-20', 1);

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
(1, 1, 170, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ticket_compra`
--

CREATE TABLE `ticket_compra` (
  `idTicket` int(11) NOT NULL,
  `fechCompra` date NOT NULL,
  `monto` int(30) NOT NULL,
  `comprador` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ticket_compra`
--

INSERT INTO `ticket_compra` (`idTicket`, `fechCompra`, `monto`, `comprador`) VALUES
(1, '2025-11-01', 12000, 123456789);

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
  MODIFY `idDetalle` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `proyeccion`
--
ALTER TABLE `proyeccion`
  MODIFY `idProyeccion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `ticket_compra`
--
ALTER TABLE `ticket_compra`
  MODIFY `idTicket` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

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
