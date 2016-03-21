/*
Navicat MariaDB Data Transfer

Source Server         : LocalMariaDB
Source Server Version : 100111
Source Host           : localhost:3306
Source Database       : depo

Target Server Type    : MariaDB
Target Server Version : 100111
File Encoding         : 65001

Date: 2016-02-19 17:14:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_turkish_ci DEFAULT NULL,
  `surname` varchar(255) COLLATE utf8_turkish_ci DEFAULT NULL,
  `unvan` varchar(255) COLLATE utf8_turkish_ci DEFAULT NULL,
  `type` enum('') COLLATE utf8_turkish_ci DEFAULT NULL,
  `phoneNumber` varchar(255) COLLATE utf8_turkish_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_turkish_ci DEFAULT NULL,
  `tcKimlik` bigint(255) DEFAULT NULL,
  `vergiKimlik` varchar(255) COLLATE utf8_turkish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- ----------------------------
-- Records of customer
-- ----------------------------

-- ----------------------------
-- Table structure for customertypes
-- ----------------------------
DROP TABLE IF EXISTS `customertypes`;
CREATE TABLE `customertypes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerType` int(11) DEFAULT NULL,
  `info` varchar(255) COLLATE utf8_turkish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- ----------------------------
-- Records of customertypes
-- ----------------------------
INSERT INTO `customertypes` VALUES ('1', '1', 'Şahıs');
INSERT INTO `customertypes` VALUES ('2', '2', 'Firma');
INSERT INTO `customertypes` VALUES ('3', '3', 'Esnaf');
INSERT INTO `customertypes` VALUES ('4', '4', 'Diğer');

-- ----------------------------
-- Table structure for depots
-- ----------------------------
DROP TABLE IF EXISTS `depots`;
CREATE TABLE `depots` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `depotId` int(11) DEFAULT NULL,
  `depotName` varchar(255) COLLATE utf8_turkish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- ----------------------------
-- Records of depots
-- ----------------------------
INSERT INTO `depots` VALUES ('1', '1', '-18 1A ');
INSERT INTO `depots` VALUES ('2', '2', '-18 1B');
INSERT INTO `depots` VALUES ('3', '3', '-18 2\r\n');
INSERT INTO `depots` VALUES ('4', '4', '-18 3\r\n');
INSERT INTO `depots` VALUES ('5', '5', 'Şok Odası 8\r\n');
INSERT INTO `depots` VALUES ('6', '6', 'Şok Odası 9\r\n');
INSERT INTO `depots` VALUES ('7', '7', 'Şok Odası 10\r\n');
INSERT INTO `depots` VALUES ('8', '8', 'Şok Odası 11\r\n');
INSERT INTO `depots` VALUES ('9', '9', 'Şok Odası 12\r\n');
INSERT INTO `depots` VALUES ('10', '10', 'Taze Muhafaza Çipura-Levrek\r\n');
INSERT INTO `depots` VALUES ('11', '11', 'Taze Muhafaza Maria\r\n');
INSERT INTO `depots` VALUES ('12', '12', 'Buz Deposu\r\n');

-- ----------------------------
-- Table structure for process
-- ----------------------------
DROP TABLE IF EXISTS `process`;
CREATE TABLE `process` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productId` int(255) DEFAULT NULL,
  `depotId` int(11) DEFAULT NULL,
  `processType` enum('') COLLATE utf8_turkish_ci DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `processAmount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- ----------------------------
-- Records of process
-- ----------------------------

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productName` varchar(255) COLLATE utf8_turkish_ci DEFAULT NULL,
  `measureType` enum('') COLLATE utf8_turkish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- ----------------------------
-- Records of products
-- ----------------------------
