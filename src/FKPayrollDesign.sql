-- MySQL dump 10.13  Distrib 8.0.19, for Linux (x86_64)
--
-- Host: localhost    Database: FKPayrollDesign
-- ------------------------------------------------------
-- Server version	8.0.19-0ubuntu5

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `EmployeeHourlySalaryTable`
--

DROP TABLE IF EXISTS `EmployeeHourlySalaryTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `EmployeeHourlySalaryTable` (
  `EmployeeId` varchar(20) NOT NULL,
  `HourlyRate` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EmployeeHourlySalaryTable`
--

LOCK TABLES `EmployeeHourlySalaryTable` WRITE;
/*!40000 ALTER TABLE `EmployeeHourlySalaryTable` DISABLE KEYS */;
INSERT INTO `EmployeeHourlySalaryTable` VALUES ('Hvivek',100);
/*!40000 ALTER TABLE `EmployeeHourlySalaryTable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EmployeeMonthlySalaryTable`
--

DROP TABLE IF EXISTS `EmployeeMonthlySalaryTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `EmployeeMonthlySalaryTable` (
  `EmployeeId` varchar(20) NOT NULL,
  `MonthlySalary` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EmployeeMonthlySalaryTable`
--

LOCK TABLES `EmployeeMonthlySalaryTable` WRITE;
/*!40000 ALTER TABLE `EmployeeMonthlySalaryTable` DISABLE KEYS */;
INSERT INTO `EmployeeMonthlySalaryTable` VALUES ('SRitika',100);
/*!40000 ALTER TABLE `EmployeeMonthlySalaryTable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EmployeeTable`
--

DROP TABLE IF EXISTS `EmployeeTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `EmployeeTable` (
  `Name` varchar(100) NOT NULL,
  `EmployeeID` varchar(20) NOT NULL,
  `JoiningDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PaymentMode` int NOT NULL,
  `DueCharges` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EmployeeTable`
--

LOCK TABLES `EmployeeTable` WRITE;
/*!40000 ALTER TABLE `EmployeeTable` DISABLE KEYS */;
INSERT INTO `EmployeeTable` VALUES ('Vivek','Hvivek','2020-04-27 03:30:00',1,10),('Ritika','SRitika','2020-04-28 03:30:00',1,0);
/*!40000 ALTER TABLE `EmployeeTable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SalesReceiptTable`
--

DROP TABLE IF EXISTS `SalesReceiptTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SalesReceiptTable` (
  `EmployeeId` varchar(20) NOT NULL,
  `saleDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `amountOfSale` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SalesReceiptTable`
--

LOCK TABLES `SalesReceiptTable` WRITE;
/*!40000 ALTER TABLE `SalesReceiptTable` DISABLE KEYS */;
INSERT INTO `SalesReceiptTable` VALUES ('SRitika','2020-05-10 06:30:00',1000000);
/*!40000 ALTER TABLE `SalesReceiptTable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TimeCardTable`
--

DROP TABLE IF EXISTS `TimeCardTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TimeCardTable` (
  `EmployeeId` varchar(20) NOT NULL,
  `submitDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `startTimeStamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `endTimeStamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TimeCardTable`
--

LOCK TABLES `TimeCardTable` WRITE;
/*!40000 ALTER TABLE `TimeCardTable` DISABLE KEYS */;
INSERT INTO `TimeCardTable` VALUES ('Hvivek','2020-05-09 18:30:00','2020-05-09 03:30:00','2020-05-09 12:30:00');
/*!40000 ALTER TABLE `TimeCardTable` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-10 22:45:41
