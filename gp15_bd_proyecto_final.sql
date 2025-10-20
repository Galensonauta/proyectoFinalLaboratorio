-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-10-2025 a las 01:17:12
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_ticket`
--

CREATE TABLE `detalle_ticket` (
  `codD` int(11) NOT NULL,
  `idProyeccion` int(11) NOT NULL,
  `lugar` int(30) NOT NULL,
  `cantidad` int(30) NOT NULL,
  `subTotal` int(30) NOT NULL
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ticket_compra`
--

CREATE TABLE `ticket_compra` (
  `idTicket` int(11) NOT NULL,
  `fechCompra` date NOT NULL,
  `fechProyeccion` date NOT NULL,
  `monto` int(30) NOT NULL,
  `comprador` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  ADD PRIMARY KEY (`codD`),
  ADD KEY `lugar` (`lugar`),
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
-- AUTO_INCREMENT de la tabla `proyeccion`
--
ALTER TABLE `proyeccion`
  MODIFY `idProyeccion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ticket_compra`
--
ALTER TABLE `ticket_compra`
  MODIFY `idTicket` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_ticket`
--
ALTER TABLE `detalle_ticket`
  ADD CONSTRAINT `detalle_ticket_ibfk_1` FOREIGN KEY (`lugar`) REFERENCES `lugar_asiento` (`codLugar`),
  ADD CONSTRAINT `detalle_ticket_ibfk_2` FOREIGN KEY (`codD`) REFERENCES `ticket_compra` (`idTicket`),
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
