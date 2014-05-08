-- MySQL dump 10.13  Distrib 5.6.13, for debian6.0 (x86_64)
--
-- Host: localhost    Database: wiring
-- ------------------------------------------------------
-- Server version	5.6.13-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `building` (
  `buildingId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `campusId` int(10) unsigned NOT NULL,
  `version` int(10) unsigned NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`buildingId`),
  KEY `building_fk1` (`campusId`),
  CONSTRAINT `building_ibfk_1` FOREIGN KEY (`campusId`) REFERENCES `campus` (`campusId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campus`
--

DROP TABLE IF EXISTS `campus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campus` (
  `campusId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `version` int(10) unsigned NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`campusId`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `connectedPort`
--

DROP TABLE IF EXISTS `connectedPort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `connectedPort` (
  `connectionId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `deployedId` int(10) unsigned NOT NULL,
  `portId` int(10) unsigned NOT NULL,
  `connectedDeployedId` int(10) unsigned NOT NULL,
  `connectedPortId` int(10) unsigned NOT NULL,
  `version` int(10) unsigned NOT NULL,
  `cableLabel` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`connectionId`),
  KEY `deployedId` (`deployedId`),
  KEY `portId` (`portId`),
  KEY `connectedDeployedId` (`connectedDeployedId`),
  KEY `connectedPortId` (`connectedPortId`),
  CONSTRAINT `connectedPort_ibfk_1` FOREIGN KEY (`deployedId`) REFERENCES `deployedItem` (`deployedId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `connectedPort_ibfk_2` FOREIGN KEY (`portId`) REFERENCES `port` (`portId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `connectedPort_ibfk_3` FOREIGN KEY (`connectedDeployedId`) REFERENCES `deployedItem` (`deployedId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `connectedPort_ibfk_4` FOREIGN KEY (`connectedPortId`) REFERENCES `port` (`portId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deployedDevice`
--

DROP TABLE IF EXISTS `deployedDevice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deployedDevice` (
  `deployedDeviceId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `deviceId` int(10) unsigned NOT NULL,
  `roomId` int(10) unsigned DEFAULT NULL,
  `rackId` int(10) unsigned DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `description` int(11) DEFAULT NULL,
  PRIMARY KEY (`deployedDeviceId`),
  KEY `deployedDevice_fk1` (`deviceId`),
  KEY `deployedDevice_fk2` (`roomId`),
  CONSTRAINT `deployedDevice_fk1` FOREIGN KEY (`deviceId`) REFERENCES `device` (`deviceId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `deployedDevice_fk2` FOREIGN KEY (`roomId`) REFERENCES `room` (`roomId`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deployedItem`
--

DROP TABLE IF EXISTS `deployedItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deployedItem` (
  `deployedId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `itemId` int(10) unsigned NOT NULL,
  `parentId` int(10) unsigned DEFAULT NULL,
  `roomId` int(10) unsigned DEFAULT NULL,
  `discriminator` char(1) NOT NULL,
  `version` int(10) unsigned NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `pos1` mediumint(8) unsigned DEFAULT NULL,
  `pos2` mediumint(8) unsigned DEFAULT NULL,
  PRIMARY KEY (`deployedId`),
  KEY `itemId` (`itemId`),
  KEY `discriminator` (`discriminator`),
  CONSTRAINT `deployedItem_ibfk_1` FOREIGN KEY (`itemId`) REFERENCES `item` (`itemId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `deviceId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `discriminator` char(1) NOT NULL,
  `version` int(10) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `size` tinyint(3) unsigned DEFAULT NULL,
  `stackable` tinyint(3) unsigned NOT NULL,
  `ports` tinyint(3) unsigned NOT NULL,
  `portSpeed` mediumint(8) unsigned DEFAULT NULL,
  `uplinks` tinyint(3) unsigned DEFAULT NULL,
  `uplinkSpeed` mediumint(8) unsigned DEFAULT NULL,
  `namingScheme` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`deviceId`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deviceContainer`
--

DROP TABLE IF EXISTS `deviceContainer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deviceContainer` (
  `containerId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `floorId` int(10) unsigned DEFAULT NULL,
  `discriminator` char(1) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`containerId`),
  KEY `deviceContainer_fk1` (`floorId`),
  CONSTRAINT `deviceContainer_fk1` FOREIGN KEY (`floorId`) REFERENCES `floor` (`floorId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `floor`
--

DROP TABLE IF EXISTS `floor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `floor` (
  `floorId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `buildingId` int(10) unsigned NOT NULL,
  `version` int(10) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `drawingUri` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`floorId`),
  KEY `floor_fk1` (`buildingId`),
  CONSTRAINT `floor_ibfk_1` FOREIGN KEY (`buildingId`) REFERENCES `building` (`buildingId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `itemId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parentId` int(10) unsigned DEFAULT NULL,
  `discriminator` char(1) NOT NULL,
  `version` int(10) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `anonymous` tinyint(4) unsigned DEFAULT NULL,
  `size` tinyint(3) unsigned DEFAULT NULL,
  `uplink` tinyint(3) unsigned DEFAULT NULL,
  `naming` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`itemId`),
  KEY `parentId` (`parentId`),
  CONSTRAINT `item_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `item` (`itemId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jackPositioning`
--

DROP TABLE IF EXISTS `jackPositioning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jackPositioning` (
  `deployedDeviceId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `x` mediumint(8) unsigned NOT NULL,
  `y` mediumint(8) unsigned DEFAULT NULL,
  UNIQUE KEY `deployedDeviceId_UNIQUE` (`deployedDeviceId`),
  KEY `jackPositioning_fk1` (`deployedDeviceId`),
  CONSTRAINT `jackPositioning_fk1` FOREIGN KEY (`deployedDeviceId`) REFERENCES `deployedDevice` (`deployedDeviceId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `port`
--

DROP TABLE IF EXISTS `port`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `port` (
  `portId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `itemId` int(10) unsigned NOT NULL,
  `discriminator` char(1) NOT NULL,
  `version` int(10) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `abbrev` varchar(10) DEFAULT NULL,
  `speed` mediumint(9) DEFAULT NULL,
  PRIMARY KEY (`portId`),
  KEY `discriminator` (`discriminator`),
  KEY `itemId` (`itemId`) USING BTREE,
  CONSTRAINT `port_ibfk_1` FOREIGN KEY (`itemId`) REFERENCES `item` (`itemId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=311 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rackPositioning`
--

DROP TABLE IF EXISTS `rackPositioning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rackPositioning` (
  `descriptorId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `deviceId` int(10) unsigned NOT NULL,
  `rackId` int(10) unsigned DEFAULT NULL,
  `position` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`descriptorId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room` (
  `roomId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `floorId` int(10) unsigned NOT NULL,
  `version` int(10) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`roomId`),
  KEY `room_fk1` (`floorId`),
  CONSTRAINT `room_ibfk_1` FOREIGN KEY (`floorId`) REFERENCES `floor` (`floorId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-07 20:58:13
