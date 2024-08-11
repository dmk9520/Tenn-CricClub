-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 17, 2018 at 01:45 AM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id5216565_tenncricclub`
--

-- --------------------------------------------------------

--
-- Table structure for table `gallery_data`
--

CREATE TABLE `gallery_data` (
  `imgurl` varchar(500) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `gallery_data`
--

INSERT INTO `gallery_data` (`imgurl`) VALUES
('https://tenncricclub.000webhostapp.com/gallery/tccimg0.png'),
('https://tenncricclub.000webhostapp.com/gallery/tccimg1.png'),
('https://tenncricclub.000webhostapp.com/gallery/tccimg0.png');

-- --------------------------------------------------------

--
-- Table structure for table `match_data`
--

CREATE TABLE `match_data` (
  `gname` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `venue` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `date` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `time` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `mobile` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `teamone` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `teamtwo` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `match_data`
--

INSERT INTO `match_data` (`gname`, `venue`, `date`, `time`, `mobile`, `teamone`, `teamtwo`) VALUES
('surat', 'surat', '7/4/2018', '0 : 21', '7096865292', 'jeet', 'dmk'),
('Surat Premier League', 'surat', '25/5/2018', '10 : 7', '7096865292', 'Team surat', 'Team Vadodara');

-- --------------------------------------------------------

--
-- Table structure for table `playerteaminfo`
--

CREATE TABLE `playerteaminfo` (
  `gname` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `teamname` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `player1` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `totalplayer` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `playerteaminfo`
--

INSERT INTO `playerteaminfo` (`gname`, `teamname`, `player1`, `totalplayer`) VALUES
('Surat Premier League', 'Team surat', 'dmk9520', 1),
('surat', 'dmk', 'dmk9520', 1),
('Surat Premier League', 'Team surat', 'dmk9520', 1),
('surat', 'jeet', 'jeet1111', 1),
('Surat Premier League', 'Team surat', 'jeet1111', 1),
('Surat Premier League', 'Team surat', 'jeet4690', 1);

-- --------------------------------------------------------

--
-- Table structure for table `proimg`
--

CREATE TABLE `proimg` (
  `Username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `image` varchar(400) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `proimg`
--

INSERT INTO `proimg` (`Username`, `image`) VALUES
('ddmmkk', 'https://tenncricclub.000webhostapp.com/images/ddmmkk.png'),
('dmk9520', 'https://tenncricclub.000webhostapp.com/images/dmk9520.png'),
('jeet1999', 'https://tenncricclub.000webhostapp.com/images/jeet1999.png'),
('jeet4690', 'https://tenncricclub.000webhostapp.com/images/jeet4690.png');

-- --------------------------------------------------------

--
-- Table structure for table `scorecard`
--

CREATE TABLE `scorecard` (
  `gname` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `imgurl` varchar(500) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `scorecard`
--

INSERT INTO `scorecard` (`gname`, `imgurl`) VALUES
('surat', 'https://tenncricclub.000webhostapp.com/scorecard/surat.png');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `Firstname` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Lastname` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Address` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `Username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `DOB` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Mobilenumber` bigint(12) NOT NULL,
  `Password` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Type` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`Firstname`, `Lastname`, `Address`, `Username`, `DOB`, `Mobilenumber`, `Password`, `Type`) VALUES
('d', 'k', '123', '123456', '7/4/2018', 9999999999, '4329ed06', 'Player'),
('abc', 'def', 'surat', 'abcbhai', '6/4/2018', 8469573124, 'c73481b5', 'Player'),
('Dhruvit', 'Katrodiya', 'India ', 'ddmmkk', '10/12/1999', 9825808040, 'c73481b5', 'Organizer'),
('nxnd', 'dhdh', 'dhrhr', 'djfbfd', '7/4/2018', 9494653867, '82131a5', 'Player'),
('a', 'k', 'A', 'dmk', '30/3/2018', 9033826175, '3696e407', 'Player'),
('Dhruvit', 'Katrodiya', 'Surat ', 'dmk9520', '10/12/1999', 9825808040, 'c73481b5', 'Player'),
('DMK_Kk', 'ambaliya', 'bardoli', 'faltu', '31/3/2018', 9876543210, 'c63c7923', 'Organizer'),
('Harsh', 'Chaklasiya', 'Surat', 'Harsh', '29/3/2018', 9601631140, 'd7bc6df1', 'Player'),
('Jetetd', 'dhdhrh', 'hdhdhhd', 'jee', '2/4/2018', 7096865292, '82131a5', 'Player'),
('Jeet', 'Ambaliya', 'surat', 'jeet', '28/4/2018', 7096865292, '60212ac1', 'Player'),
('jeet', 'ambaliya', 'surat', 'jeet1111', '20/9/1999', 7096865292, '87c897ed', 'Player'),
('Jeet', 'Ambaliya', 'Surat', 'jeet1999', '20/9/1999', 7096865292, '60212ac1', 'Organizer'),
('Ambaliya', 'Jeet', 'surat', 'jeet4690', '29/3/2018', 7096865292, '60212ac1', 'Player'),
('f', 'g', 'g', 'v', '7/4/2018', 9565555555, 'c73481b5', 'Player');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `match_data`
--
ALTER TABLE `match_data`
  ADD PRIMARY KEY (`gname`),
  ADD UNIQUE KEY `teamone` (`teamone`,`teamtwo`);

--
-- Indexes for table `proimg`
--
ALTER TABLE `proimg`
  ADD UNIQUE KEY `Username` (`Username`);

--
-- Indexes for table `scorecard`
--
ALTER TABLE `scorecard`
  ADD PRIMARY KEY (`gname`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
