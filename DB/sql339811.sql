-- phpMyAdmin SQL Dump
-- version 3.5.5
-- http://www.phpmyadmin.net
--
-- Host: sql3.freemysqlhosting.net
-- Generation Time: May 30, 2014 at 08:27 PM
-- Server version: 5.5.35-0ubuntu0.12.04.2
-- PHP Version: 5.3.28

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sql339811`
--

-- --------------------------------------------------------

DROP TABLE IF EXISTS `RESERVATION`;

DROP TABLE IF EXISTS `USERT`;

DROP TABLE IF EXISTS `ROOM`;

DROP TABLE IF EXISTS `BUILDING`;

--
-- Table structure for table `BUILDING`
--

CREATE TABLE IF NOT EXISTS `BUILDING` (
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `BUILDING`
--

INSERT INTO `BUILDING` (`NAME`) VALUES
('EPS'),
('Main Library'),
('Rectorate Building');

-- --------------------------------------------------------

--
-- Table structure for table `RESERVATION`
--


CREATE TABLE IF NOT EXISTS `RESERVATION` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `DATE` datetime DEFAULT NULL,
  `USERT` varchar(255) DEFAULT NULL,
  `ROOM` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`IDENTIFIER`),
  KEY `FK_RESERVATION_ROOM` (`ROOM`),
  KEY `FK_RESERVATION_USERT` (`USERT`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `RESERVATION`
--

INSERT INTO `RESERVATION` (`IDENTIFIER`, `DATE`, `USERT`, `ROOM`) VALUES
(1, '2014-06-10 09:00:00', '12345678', 1),
(2, '2014-06-10 10:00:00', '12345678', 1),
(3, '2014-06-10 11:00:00', '12345678', 1),
(4, '2014-06-10 14:00:00', '9876544', 1),
(5, '2014-06-10 15:00:00', '9876544', 1),
(6, '2014-06-10 18:00:00', '12345678', 1),
(7, '2014-06-11 11:00:00', '9876544', 1),
(8, '2014-06-11 12:00:00', '9876544', 1),
(9, '2014-06-11 13:00:00', '9876544', 1),
(10, '2014-06-11 17:00:00', '12345678', 1),
(11, '2014-07-10 12:00:00', '12345678', 3),
(12, '2014-07-10 14:00:00', '12345678', 3),
(13, '2014-07-10 15:00:00', '9876544', 3),
(14, '2014-07-10 17:00:00', '12345678', 3),
(15, '2014-07-10 18:00:00', '12345678', 3),
(16, '2014-07-11 09:00:00', '9876544', 3),
(17, '2014-07-11 10:00:00', '9876544', 3),
(18, '2014-07-11 16:00:00', '12345678', 3),
(19, '2014-07-11 17:00:00', '12345678', 3),
(20, '2014-07-11 18:00:00', '12345678', 3),
(21, '2014-05-26 09:00:00', '55555', 20),
(22, '2014-05-28 09:00:00', '55555', 20),
(23, '2014-05-28 09:00:00', '9876544', 25),
(24, '2014-05-29 09:00:00', '12345678', 25),
(26, '2015-05-26 09:00:00', '12457638', 20),
(27, '2015-05-26 09:00:00', '9876544', 30);

-- --------------------------------------------------------

--
-- Table structure for table `ROOM`
--

CREATE TABLE IF NOT EXISTS `ROOM` (
  `ROOMID` bigint(20) NOT NULL,
  `ROOM_TYPE` varchar(5) DEFAULT NULL,
  `CAPACITY` int(11) DEFAULT NULL,
  `NUMBER` varchar(255) DEFAULT NULL,
  `BUILDING` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ROOMID`),
  KEY `FK_ROOM_BUILDING` (`BUILDING`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ROOM`
--

INSERT INTO `ROOM` (`ROOMID`, `ROOM_TYPE`, `CAPACITY`, `NUMBER`, `BUILDING`) VALUES
(1, 'MET_R', 40, '1.0', 'Main Library'),
(2, 'CLA_R', 50, '2.0', 'Main Library'),
(3, 'CLA_R', 50, '1.0', 'Rectorate Building'),
(4, 'LAB_R', 40, '2.0', 'Rectorate Building'),
(15, 'CLA_R', 15, '0.15', 'EPS'),
(20, 'CLA_R', 20, '0.20', 'EPS'),
(25, 'CLA_R', 25, '0.25', 'EPS'),
(30, 'LAB_R', 30, '1.30', 'EPS');

-- --------------------------------------------------------

--
-- Table structure for table `SEQUENCE`
--

DROP TABLE IF EXISTS `SEQUENCE`;
CREATE TABLE IF NOT EXISTS `SEQUENCE` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `SEQUENCE`
--

INSERT INTO `SEQUENCE` (`SEQ_NAME`, `SEQ_COUNT`) VALUES
('SEQ_GEN', '4850');

-- --------------------------------------------------------

--
-- Table structure for table `USERT`
--

CREATE TABLE IF NOT EXISTS `USERT` (
  `NIF` varchar(255) NOT NULL,
  `USER_TYPE` varchar(5) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`NIF`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `USERT`
--

INSERT INTO `USERT` (`NIF`, `USER_TYPE`, `email`, `name`) VALUES
('111', 'PPS_U', 'kopo@popo', 'popo'),
('11100099', 'PPS_U', 'kopo@popo', 'popo'),
('12345678', 'PPS_U', 'RalphPoteus@aus.com', 'Ralph Poteus'),
('12457638', 'STF_U', 'SophieSpedelung@aus.com', 'Sophie Spedelung'),
('45998872', 'SPC_U', 'NiklausWitmark@gmail.com', 'Niklaus Witmark'),
('55555', 'PPS_U', 'manganito@gmail.com', 'Manganito'),
('9876544', 'PPS_U', 'rspend@aus.edu', 'Robert Spendelung');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `RESERVATION`
--
ALTER TABLE `RESERVATION`
  ADD CONSTRAINT `FK_RESERVATION_ROOM` FOREIGN KEY (`ROOM`) REFERENCES `ROOM` (`ROOMID`),
  ADD CONSTRAINT `FK_RESERVATION_USERT` FOREIGN KEY (`USERT`) REFERENCES `USERT` (`NIF`);

--
-- Constraints for table `ROOM`
--
ALTER TABLE `ROOM`
  ADD CONSTRAINT `FK_ROOM_BUILDING` FOREIGN KEY (`BUILDING`) REFERENCES `BUILDING` (`NAME`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
