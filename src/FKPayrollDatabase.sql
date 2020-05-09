-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 09, 2020 at 08:19 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `FKPayrollDatabase`
--

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeHourlySalaryTable`
--

CREATE TABLE `EmployeeHourlySalaryTable` (
  `EmployeeId` varchar(20) NOT NULL,
  `HourlyRate` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeMonthlySalaryTable`
--

CREATE TABLE `EmployeeMonthlySalaryTable` (
  `EmployeeId` varchar(20) NOT NULL,
  `MonthlySalary` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeTable`
--

CREATE TABLE `EmployeeTable` (
  `Name` varchar(100) NOT NULL,
  `EmployeeID` varchar(20) NOT NULL,
  `JoiningDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `PaymentMode` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `SalesReceiptTable`
--

CREATE TABLE `SalesReceiptTable` (
  `EmployeeId` varchar(20) NOT NULL,
  `saleDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `amountOfSale` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `TimeCardTable`
--

CREATE TABLE `TimeCardTable` (
  `EmployeeId` varchar(20) NOT NULL,
  `submitDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `startTimeStamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `endTimeStamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
