
CREATE DATABASE IF NOT EXISTS BasePrueba;
USE BasePrueba;
-- baseprueba.persona definition

CREATE TABLE `persona` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `genero` varchar(255) DEFAULT NULL,
  `edad` int(11) NOT NULL,
  `identificacion` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) NOT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `identificacion` (`identificacion`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- baseprueba.cliente definition

CREATE TABLE `cliente` (
  `clienteid` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  `id_persona` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`clienteid`),
  UNIQUE KEY `id_persona` (`id_persona`),
  CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- baseprueba.cuenta definition

CREATE TABLE `cuenta` (
  `numero_cuenta` varchar(255) NOT NULL,
  `tipo_cuenta` varchar(255) DEFAULT NULL,
  `saldo_inicial` double DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  `clienteid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`numero_cuenta`),
  KEY `clienteid` (`clienteid`),
  CONSTRAINT `cuenta_ibfk_1` FOREIGN KEY (`clienteid`) REFERENCES `cliente` (`clienteid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- baseprueba.movimientos definition

CREATE TABLE `movimientos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL,
  `tipo_movimiento` varchar(255) DEFAULT NULL,
  `valor` double DEFAULT NULL,
  `saldo` double DEFAULT NULL,
  `numero_cuenta` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `numero_cuenta` (`numero_cuenta`),
  CONSTRAINT `movimientos_ibfk_1` FOREIGN KEY (`numero_cuenta`) REFERENCES `cuenta` (`numero_cuenta`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

