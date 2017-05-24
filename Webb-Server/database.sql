-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 24, 2017 at 01:12 AM
-- Server version: 10.1.20-MariaDB
-- PHP Version: 7.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id1452038_qwalkbase`
--
CREATE DATABASE IF NOT EXISTS `id1452038_qwalkbase` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id1452038_qwalkbase`;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `username` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `regdate` datetime NOT NULL,
  `password` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `accountid` int(11) NOT NULL,
  `level` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`username`, `regdate`, `password`, `accountid`, `level`) VALUES
('Nightingale', '2016-01-13 11:19:28', '29IcQRHMK2PXM', 1, 1),
('c', '2017-05-23 20:00:38', '29BWNfDGgnxOI', 110, 0),
(' ', '2017-05-23 16:10:21', '29h23.gtvV0rw', 109, 0),
('det är', '2017-05-22 06:29:31', '29LM0pwscaaME', 108, 0),
('a', '2017-05-10 11:38:07', '29Iqg34CL2vqs', 100, 0),
('tetigen', '2017-05-21 20:14:20', '29KJ7SJOmsuok', 107, 0),
('lol', '2017-05-08 20:39:46', '291xSphWm0Bss', 98, 0),
('testtt', '2017-05-21 20:10:14', '291rySmWTuKhg', 106, 0),
('kraft', '2017-05-21 19:35:14', '29Iqg34CL2vqs', 105, 0),
('aa', '2017-05-18 07:07:06', '29ufFn3TQRe3k', 103, 0),
('abc', '2017-05-21 19:29:50', '29ay9WyRkIbqA', 104, 0),
('b', '2017-05-17 06:55:56', '29Cs9fAhYPTAk', 102, 0),
('q', '2017-05-11 17:20:58', '29e1CEREYIuJw', 101, 0);

-- --------------------------------------------------------

--
-- Table structure for table `accountrelation`
--

CREATE TABLE `accountrelation` (
  `quizid` int(11) NOT NULL,
  `accountid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `accountrelation`
--

INSERT INTO `accountrelation` (`quizid`, `accountid`) VALUES
(15, 105),
(18, 105),
(24, 98),
(26, 102),
(28, 98);

-- --------------------------------------------------------

--
-- Table structure for table `friendrelation`
--

CREATE TABLE `friendrelation` (
  `accountid` int(11) NOT NULL,
  `friendid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `friendrelation`
--

INSERT INTO `friendrelation` (`accountid`, `friendid`) VALUES
(98, 1),
(98, 100),
(98, 101),
(100, 1),
(100, 101),
(103, 100),
(102, 100),
(104, 100),
(100, 105),
(102, 105),
(98, 102),
(98, 105),
(109, 105),
(109, 100),
(109, 102),
(105, 100);

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `questionid` int(11) NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `option1` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option2` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option3` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option4` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `correctanswer` int(11) NOT NULL,
  `questiontype` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`questionid`, `description`, `option1`, `option2`, `option3`, `option4`, `longitude`, `latitude`, `correctanswer`, `questiontype`) VALUES
(5, 'vad heter jag?', 'jonathan?', 'katten?', '', '', 11.979287, 57.6882866, 1, 0),
(19, 'hejhej', 'a', 'b', 'c', '', 11.9981576, 57.6839534, 1, 0),
(20, 'två', 'a', 'b', '', '', 11.9982718, 57.6839661, 0, 0),
(21, 'Vilken låt i Skam delar artistnamn med de som gjorde Rosa Helikopter?', 'ha det!', 'cool girls in street', 'marcus o martinus', 'Dick in the air', 11.979108564555643, 57.68862830520989, 3, 0),
(22, 'Vilken karaktärs skådespelare går på Hartvig Nissen i verkligheten?', 'Nooras', 'Isaks', 'Jonas', 'Sana', 11.978763565421103, 57.68904156533146, 1, 0),
(23, 'Vilket år kom Skam för första gången?', '2010', '2017', '', '', 11.977473422884941, 57.689700334514185, 2015, 1),
(27, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(28, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(29, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(30, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(36, 'adsadas', 'dadwa', 'sdasd', '', '', -122.084, 40, 1, 0),
(39, 'djur Ektorp luftfuktighet 2', 'västvärldens Lucy', 'Hedin Leo den GT', 'rubin', '', 57.6881151, 11.978511, 1, 0),
(40, 'jag har fick riv Wii ej', '68', '69', '', '', 14.126341938972471, 58.47024393047411, 69, 1),
(42, 'sdfsdf', 'sdfsdfsdf', 'xsdfdsffs', '', '', -122.084, 40, 0, 0),
(46, 'tyck guug har du g', 'tyck', 'du', '', '', 11.9774184, 57.6897327, 1, 0),
(47, 'tyck', '0', '10660', '', '', 11.9774201, 57.6897259, 5820, 1),
(48, 'tyck', 'tyck', 'du', '', '', 11.9774184, 57.6897327, 1, 0),
(49, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(50, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(51, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(52, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(53, 'det är en del', '50', '100', '', '', 11.9774219, 57.689735, 100, 1),
(54, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(55, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(56, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(57, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(58, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(59, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(60, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(61, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(62, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1);

-- --------------------------------------------------------

--
-- Table structure for table `quiz`
--

CREATE TABLE `quiz` (
  `quizid` int(11) NOT NULL,
  `title` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `quiz`
--

INSERT INTO `quiz` (`quizid`, `title`, `description`) VALUES
(15, 'SKAM', 'En qwalk om skam!'),
(18, 'Runt EDIT', 'En kortpromenad!'),
(24, 'aasdasd', 'sadsadasd'),
(26, 'bbbbb', 'redigera mig'),
(28, 'dfgdfg', 'addadaw');

-- --------------------------------------------------------

--
-- Table structure for table `quizrelation`
--

CREATE TABLE `quizrelation` (
  `quizid` int(11) NOT NULL,
  `questionid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `quizrelation`
--

INSERT INTO `quizrelation` (`quizid`, `questionid`) VALUES
(15, 21),
(15, 22),
(15, 23),
(18, 27),
(18, 28),
(18, 29),
(18, 30),
(24, 36),
(26, 39),
(26, 3840),
(28, 42);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`accountid`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`questionid`);

--
-- Indexes for table `quiz`
--
ALTER TABLE `quiz`
  ADD PRIMARY KEY (`quizid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `accountid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;
--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `questionid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;
--
-- AUTO_INCREMENT for table `quiz`
--
ALTER TABLE `quiz`
  MODIFY `quizid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;--
-- Database: `id1452038_qwalkbase`
--
CREATE DATABASE IF NOT EXISTS `id1452038_qwalkbase` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id1452038_qwalkbase`;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `username` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `regdate` datetime NOT NULL,
  `password` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `accountid` int(11) NOT NULL,
  `level` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`username`, `regdate`, `password`, `accountid`, `level`) VALUES
('Nightingale', '2016-01-13 11:19:28', '29IcQRHMK2PXM', 1, 1),
('c', '2017-05-23 20:00:38', '29BWNfDGgnxOI', 110, 0),
(' ', '2017-05-23 16:10:21', '29h23.gtvV0rw', 109, 0),
('det är', '2017-05-22 06:29:31', '29LM0pwscaaME', 108, 0),
('a', '2017-05-10 11:38:07', '29Iqg34CL2vqs', 100, 0),
('tetigen', '2017-05-21 20:14:20', '29KJ7SJOmsuok', 107, 0),
('lol', '2017-05-08 20:39:46', '291xSphWm0Bss', 98, 0),
('testtt', '2017-05-21 20:10:14', '291rySmWTuKhg', 106, 0),
('kraft', '2017-05-21 19:35:14', '29Iqg34CL2vqs', 105, 0),
('aa', '2017-05-18 07:07:06', '29ufFn3TQRe3k', 103, 0),
('abc', '2017-05-21 19:29:50', '29ay9WyRkIbqA', 104, 0),
('b', '2017-05-17 06:55:56', '29Cs9fAhYPTAk', 102, 0),
('q', '2017-05-11 17:20:58', '29e1CEREYIuJw', 101, 0);

-- --------------------------------------------------------

--
-- Table structure for table `accountrelation`
--

CREATE TABLE `accountrelation` (
  `quizid` int(11) NOT NULL,
  `accountid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `accountrelation`
--

INSERT INTO `accountrelation` (`quizid`, `accountid`) VALUES
(15, 105),
(18, 105),
(24, 98),
(26, 102),
(28, 98);

-- --------------------------------------------------------

--
-- Table structure for table `friendrelation`
--

CREATE TABLE `friendrelation` (
  `accountid` int(11) NOT NULL,
  `friendid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `friendrelation`
--

INSERT INTO `friendrelation` (`accountid`, `friendid`) VALUES
(98, 1),
(98, 100),
(98, 101),
(100, 1),
(100, 101),
(103, 100),
(102, 100),
(104, 100),
(100, 105),
(102, 105),
(98, 102),
(98, 105),
(109, 105),
(109, 100),
(109, 102),
(105, 100);

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `questionid` int(11) NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `option1` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option2` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option3` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option4` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `correctanswer` int(11) NOT NULL,
  `questiontype` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`questionid`, `description`, `option1`, `option2`, `option3`, `option4`, `longitude`, `latitude`, `correctanswer`, `questiontype`) VALUES
(5, 'vad heter jag?', 'jonathan?', 'katten?', '', '', 11.979287, 57.6882866, 1, 0),
(19, 'hejhej', 'a', 'b', 'c', '', 11.9981576, 57.6839534, 1, 0),
(20, 'två', 'a', 'b', '', '', 11.9982718, 57.6839661, 0, 0),
(21, 'Vilken låt i Skam delar artistnamn med de som gjorde Rosa Helikopter?', 'ha det!', 'cool girls in street', 'marcus o martinus', 'Dick in the air', 11.979108564555643, 57.68862830520989, 3, 0),
(22, 'Vilken karaktärs skådespelare går på Hartvig Nissen i verkligheten?', 'Nooras', 'Isaks', 'Jonas', 'Sana', 11.978763565421103, 57.68904156533146, 1, 0),
(23, 'Vilket år kom Skam för första gången?', '2010', '2017', '', '', 11.977473422884941, 57.689700334514185, 2015, 1),
(27, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(28, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(29, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(30, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(36, 'adsadas', 'dadwa', 'sdasd', '', '', -122.084, 40, 1, 0),
(39, 'djur Ektorp luftfuktighet 2', 'västvärldens Lucy', 'Hedin Leo den GT', 'rubin', '', 57.6881151, 11.978511, 1, 0),
(40, 'jag har fick riv Wii ej', '68', '69', '', '', 14.126341938972471, 58.47024393047411, 69, 1),
(42, 'sdfsdf', 'sdfsdfsdf', 'xsdfdsffs', '', '', -122.084, 40, 0, 0),
(46, 'tyck guug har du g', 'tyck', 'du', '', '', 11.9774184, 57.6897327, 1, 0),
(47, 'tyck', '0', '10660', '', '', 11.9774201, 57.6897259, 5820, 1),
(48, 'tyck', 'tyck', 'du', '', '', 11.9774184, 57.6897327, 1, 0),
(49, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(50, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(51, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(52, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(53, 'det är en del', '50', '100', '', '', 11.9774219, 57.689735, 100, 1),
(54, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(55, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(56, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(57, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(58, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(59, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(60, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(61, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(62, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1);

-- --------------------------------------------------------

--
-- Table structure for table `quiz`
--

CREATE TABLE `quiz` (
  `quizid` int(11) NOT NULL,
  `title` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `quiz`
--

INSERT INTO `quiz` (`quizid`, `title`, `description`) VALUES
(15, 'SKAM', 'En qwalk om skam!'),
(18, 'Runt EDIT', 'En kortpromenad!'),
(24, 'aasdasd', 'sadsadasd'),
(26, 'bbbbb', 'redigera mig'),
(28, 'dfgdfg', 'addadaw');

-- --------------------------------------------------------

--
-- Table structure for table `quizrelation`
--

CREATE TABLE `quizrelation` (
  `quizid` int(11) NOT NULL,
  `questionid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `quizrelation`
--

INSERT INTO `quizrelation` (`quizid`, `questionid`) VALUES
(15, 21),
(15, 22),
(15, 23),
(18, 27),
(18, 28),
(18, 29),
(18, 30),
(24, 36),
(26, 39),
(26, 3840),
(28, 42);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`accountid`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`questionid`);

--
-- Indexes for table `quiz`
--
ALTER TABLE `quiz`
  ADD PRIMARY KEY (`quizid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `accountid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;
--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `questionid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;
--
-- AUTO_INCREMENT for table `quiz`
--
ALTER TABLE `quiz`
  MODIFY `quizid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;--
-- Database: `id1452038_qwalkbase`
--
CREATE DATABASE IF NOT EXISTS `id1452038_qwalkbase` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id1452038_qwalkbase`;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `username` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `regdate` datetime NOT NULL,
  `password` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `accountid` int(11) NOT NULL,
  `level` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`username`, `regdate`, `password`, `accountid`, `level`) VALUES
('Nightingale', '2016-01-13 11:19:28', '29IcQRHMK2PXM', 1, 1),
('c', '2017-05-23 20:00:38', '29BWNfDGgnxOI', 110, 0),
(' ', '2017-05-23 16:10:21', '29h23.gtvV0rw', 109, 0),
('det är', '2017-05-22 06:29:31', '29LM0pwscaaME', 108, 0),
('a', '2017-05-10 11:38:07', '29Iqg34CL2vqs', 100, 0),
('tetigen', '2017-05-21 20:14:20', '29KJ7SJOmsuok', 107, 0),
('lol', '2017-05-08 20:39:46', '291xSphWm0Bss', 98, 0),
('testtt', '2017-05-21 20:10:14', '291rySmWTuKhg', 106, 0),
('kraft', '2017-05-21 19:35:14', '29Iqg34CL2vqs', 105, 0),
('aa', '2017-05-18 07:07:06', '29ufFn3TQRe3k', 103, 0),
('abc', '2017-05-21 19:29:50', '29ay9WyRkIbqA', 104, 0),
('b', '2017-05-17 06:55:56', '29Cs9fAhYPTAk', 102, 0),
('q', '2017-05-11 17:20:58', '29e1CEREYIuJw', 101, 0);

-- --------------------------------------------------------

--
-- Table structure for table `accountrelation`
--

CREATE TABLE `accountrelation` (
  `quizid` int(11) NOT NULL,
  `accountid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `accountrelation`
--

INSERT INTO `accountrelation` (`quizid`, `accountid`) VALUES
(15, 105),
(18, 105),
(24, 98),
(26, 102),
(28, 98);

-- --------------------------------------------------------

--
-- Table structure for table `friendrelation`
--

CREATE TABLE `friendrelation` (
  `accountid` int(11) NOT NULL,
  `friendid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `friendrelation`
--

INSERT INTO `friendrelation` (`accountid`, `friendid`) VALUES
(98, 1),
(98, 100),
(98, 101),
(100, 1),
(100, 101),
(103, 100),
(102, 100),
(104, 100),
(100, 105),
(102, 105),
(98, 102),
(98, 105),
(109, 105),
(109, 100),
(109, 102),
(105, 100);

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `questionid` int(11) NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `option1` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option2` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option3` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option4` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `correctanswer` int(11) NOT NULL,
  `questiontype` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`questionid`, `description`, `option1`, `option2`, `option3`, `option4`, `longitude`, `latitude`, `correctanswer`, `questiontype`) VALUES
(5, 'vad heter jag?', 'jonathan?', 'katten?', '', '', 11.979287, 57.6882866, 1, 0),
(19, 'hejhej', 'a', 'b', 'c', '', 11.9981576, 57.6839534, 1, 0),
(20, 'två', 'a', 'b', '', '', 11.9982718, 57.6839661, 0, 0),
(21, 'Vilken låt i Skam delar artistnamn med de som gjorde Rosa Helikopter?', 'ha det!', 'cool girls in street', 'marcus o martinus', 'Dick in the air', 11.979108564555643, 57.68862830520989, 3, 0),
(22, 'Vilken karaktärs skådespelare går på Hartvig Nissen i verkligheten?', 'Nooras', 'Isaks', 'Jonas', 'Sana', 11.978763565421103, 57.68904156533146, 1, 0),
(23, 'Vilket år kom Skam för första gången?', '2010', '2017', '', '', 11.977473422884941, 57.689700334514185, 2015, 1),
(27, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(28, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(29, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(30, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(36, 'adsadas', 'dadwa', 'sdasd', '', '', -122.084, 40, 1, 0),
(39, 'djur Ektorp luftfuktighet 2', 'västvärldens Lucy', 'Hedin Leo den GT', 'rubin', '', 57.6881151, 11.978511, 1, 0),
(40, 'jag har fick riv Wii ej', '68', '69', '', '', 14.126341938972471, 58.47024393047411, 69, 1),
(42, 'sdfsdf', 'sdfsdfsdf', 'xsdfdsffs', '', '', -122.084, 40, 0, 0),
(46, 'tyck guug har du g', 'tyck', 'du', '', '', 11.9774184, 57.6897327, 1, 0),
(47, 'tyck', '0', '10660', '', '', 11.9774201, 57.6897259, 5820, 1),
(48, 'tyck', 'tyck', 'du', '', '', 11.9774184, 57.6897327, 1, 0),
(49, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(50, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(51, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(52, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(53, 'det är en del', '50', '100', '', '', 11.9774219, 57.689735, 100, 1),
(54, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(55, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(56, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(57, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(58, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(59, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(60, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(61, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(62, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1);

-- --------------------------------------------------------

--
-- Table structure for table `quiz`
--

CREATE TABLE `quiz` (
  `quizid` int(11) NOT NULL,
  `title` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `quiz`
--

INSERT INTO `quiz` (`quizid`, `title`, `description`) VALUES
(15, 'SKAM', 'En qwalk om skam!'),
(18, 'Runt EDIT', 'En kortpromenad!'),
(24, 'aasdasd', 'sadsadasd'),
(26, 'bbbbb', 'redigera mig'),
(28, 'dfgdfg', 'addadaw');

-- --------------------------------------------------------

--
-- Table structure for table `quizrelation`
--

CREATE TABLE `quizrelation` (
  `quizid` int(11) NOT NULL,
  `questionid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `quizrelation`
--

INSERT INTO `quizrelation` (`quizid`, `questionid`) VALUES
(15, 21),
(15, 22),
(15, 23),
(18, 27),
(18, 28),
(18, 29),
(18, 30),
(24, 36),
(26, 39),
(26, 3840),
(28, 42);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`accountid`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`questionid`);

--
-- Indexes for table `quiz`
--
ALTER TABLE `quiz`
  ADD PRIMARY KEY (`quizid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `accountid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;
--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `questionid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;
--
-- AUTO_INCREMENT for table `quiz`
--
ALTER TABLE `quiz`
  MODIFY `quizid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;--
-- Database: `id1452038_qwalkbase`
--
CREATE DATABASE IF NOT EXISTS `id1452038_qwalkbase` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id1452038_qwalkbase`;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `username` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `regdate` datetime NOT NULL,
  `password` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `accountid` int(11) NOT NULL,
  `level` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`username`, `regdate`, `password`, `accountid`, `level`) VALUES
('Nightingale', '2016-01-13 11:19:28', '29IcQRHMK2PXM', 1, 1),
('c', '2017-05-23 20:00:38', '29BWNfDGgnxOI', 110, 0),
(' ', '2017-05-23 16:10:21', '29h23.gtvV0rw', 109, 0),
('det är', '2017-05-22 06:29:31', '29LM0pwscaaME', 108, 0),
('a', '2017-05-10 11:38:07', '29Iqg34CL2vqs', 100, 0),
('tetigen', '2017-05-21 20:14:20', '29KJ7SJOmsuok', 107, 0),
('lol', '2017-05-08 20:39:46', '291xSphWm0Bss', 98, 0),
('testtt', '2017-05-21 20:10:14', '291rySmWTuKhg', 106, 0),
('kraft', '2017-05-21 19:35:14', '29Iqg34CL2vqs', 105, 0),
('aa', '2017-05-18 07:07:06', '29ufFn3TQRe3k', 103, 0),
('abc', '2017-05-21 19:29:50', '29ay9WyRkIbqA', 104, 0),
('b', '2017-05-17 06:55:56', '29Cs9fAhYPTAk', 102, 0),
('q', '2017-05-11 17:20:58', '29e1CEREYIuJw', 101, 0);

-- --------------------------------------------------------

--
-- Table structure for table `accountrelation`
--

CREATE TABLE `accountrelation` (
  `quizid` int(11) NOT NULL,
  `accountid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `accountrelation`
--

INSERT INTO `accountrelation` (`quizid`, `accountid`) VALUES
(15, 105),
(18, 105),
(24, 98),
(26, 102),
(28, 98);

-- --------------------------------------------------------

--
-- Table structure for table `friendrelation`
--

CREATE TABLE `friendrelation` (
  `accountid` int(11) NOT NULL,
  `friendid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `friendrelation`
--

INSERT INTO `friendrelation` (`accountid`, `friendid`) VALUES
(98, 1),
(98, 100),
(98, 101),
(100, 1),
(100, 101),
(103, 100),
(102, 100),
(104, 100),
(100, 105),
(102, 105),
(98, 102),
(98, 105),
(109, 105),
(109, 100),
(109, 102),
(105, 100);

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `questionid` int(11) NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `option1` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option2` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option3` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option4` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `correctanswer` int(11) NOT NULL,
  `questiontype` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`questionid`, `description`, `option1`, `option2`, `option3`, `option4`, `longitude`, `latitude`, `correctanswer`, `questiontype`) VALUES
(5, 'vad heter jag?', 'jonathan?', 'katten?', '', '', 11.979287, 57.6882866, 1, 0),
(19, 'hejhej', 'a', 'b', 'c', '', 11.9981576, 57.6839534, 1, 0),
(20, 'två', 'a', 'b', '', '', 11.9982718, 57.6839661, 0, 0),
(21, 'Vilken låt i Skam delar artistnamn med de som gjorde Rosa Helikopter?', 'ha det!', 'cool girls in street', 'marcus o martinus', 'Dick in the air', 11.979108564555643, 57.68862830520989, 3, 0),
(22, 'Vilken karaktärs skådespelare går på Hartvig Nissen i verkligheten?', 'Nooras', 'Isaks', 'Jonas', 'Sana', 11.978763565421103, 57.68904156533146, 1, 0),
(23, 'Vilket år kom Skam för första gången?', '2010', '2017', '', '', 11.977473422884941, 57.689700334514185, 2015, 1),
(27, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(28, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(29, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(30, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(36, 'adsadas', 'dadwa', 'sdasd', '', '', -122.084, 40, 1, 0),
(39, 'djur Ektorp luftfuktighet 2', 'västvärldens Lucy', 'Hedin Leo den GT', 'rubin', '', 57.6881151, 11.978511, 1, 0),
(40, 'jag har fick riv Wii ej', '68', '69', '', '', 14.126341938972471, 58.47024393047411, 69, 1),
(42, 'sdfsdf', 'sdfsdfsdf', 'xsdfdsffs', '', '', -122.084, 40, 0, 0),
(46, 'tyck guug har du g', 'tyck', 'du', '', '', 11.9774184, 57.6897327, 1, 0),
(47, 'tyck', '0', '10660', '', '', 11.9774201, 57.6897259, 5820, 1),
(48, 'tyck', 'tyck', 'du', '', '', 11.9774184, 57.6897327, 1, 0),
(49, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(50, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(51, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(52, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(53, 'det är en del', '50', '100', '', '', 11.9774219, 57.689735, 100, 1),
(54, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(55, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(56, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(57, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(58, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(59, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(60, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(61, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(62, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1);

-- --------------------------------------------------------

--
-- Table structure for table `quiz`
--

CREATE TABLE `quiz` (
  `quizid` int(11) NOT NULL,
  `title` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `quiz`
--

INSERT INTO `quiz` (`quizid`, `title`, `description`) VALUES
(15, 'SKAM', 'En qwalk om skam!'),
(18, 'Runt EDIT', 'En kortpromenad!'),
(24, 'aasdasd', 'sadsadasd'),
(26, 'bbbbb', 'redigera mig'),
(28, 'dfgdfg', 'addadaw');

-- --------------------------------------------------------

--
-- Table structure for table `quizrelation`
--

CREATE TABLE `quizrelation` (
  `quizid` int(11) NOT NULL,
  `questionid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `quizrelation`
--

INSERT INTO `quizrelation` (`quizid`, `questionid`) VALUES
(15, 21),
(15, 22),
(15, 23),
(18, 27),
(18, 28),
(18, 29),
(18, 30),
(24, 36),
(26, 39),
(26, 3840),
(28, 42);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`accountid`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`questionid`);

--
-- Indexes for table `quiz`
--
ALTER TABLE `quiz`
  ADD PRIMARY KEY (`quizid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `accountid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;
--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `questionid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;
--
-- AUTO_INCREMENT for table `quiz`
--
ALTER TABLE `quiz`
  MODIFY `quizid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;--
-- Database: `id1452038_qwalkbase`
--
CREATE DATABASE IF NOT EXISTS `id1452038_qwalkbase` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id1452038_qwalkbase`;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `username` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `regdate` datetime NOT NULL,
  `password` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `accountid` int(11) NOT NULL,
  `level` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`username`, `regdate`, `password`, `accountid`, `level`) VALUES
('Nightingale', '2016-01-13 11:19:28', '29IcQRHMK2PXM', 1, 1),
('c', '2017-05-23 20:00:38', '29BWNfDGgnxOI', 110, 0),
(' ', '2017-05-23 16:10:21', '29h23.gtvV0rw', 109, 0),
('det är', '2017-05-22 06:29:31', '29LM0pwscaaME', 108, 0),
('a', '2017-05-10 11:38:07', '29Iqg34CL2vqs', 100, 0),
('tetigen', '2017-05-21 20:14:20', '29KJ7SJOmsuok', 107, 0),
('lol', '2017-05-08 20:39:46', '291xSphWm0Bss', 98, 0),
('testtt', '2017-05-21 20:10:14', '291rySmWTuKhg', 106, 0),
('kraft', '2017-05-21 19:35:14', '29Iqg34CL2vqs', 105, 0),
('aa', '2017-05-18 07:07:06', '29ufFn3TQRe3k', 103, 0),
('abc', '2017-05-21 19:29:50', '29ay9WyRkIbqA', 104, 0),
('b', '2017-05-17 06:55:56', '29Cs9fAhYPTAk', 102, 0),
('q', '2017-05-11 17:20:58', '29e1CEREYIuJw', 101, 0);

-- --------------------------------------------------------

--
-- Table structure for table `accountrelation`
--

CREATE TABLE `accountrelation` (
  `quizid` int(11) NOT NULL,
  `accountid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `accountrelation`
--

INSERT INTO `accountrelation` (`quizid`, `accountid`) VALUES
(15, 105),
(18, 105),
(24, 98),
(26, 102),
(28, 98);

-- --------------------------------------------------------

--
-- Table structure for table `friendrelation`
--

CREATE TABLE `friendrelation` (
  `accountid` int(11) NOT NULL,
  `friendid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `friendrelation`
--

INSERT INTO `friendrelation` (`accountid`, `friendid`) VALUES
(98, 1),
(98, 100),
(98, 101),
(100, 1),
(100, 101),
(103, 100),
(102, 100),
(104, 100),
(100, 105),
(102, 105),
(98, 102),
(98, 105),
(109, 105),
(109, 100),
(109, 102),
(105, 100);

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `questionid` int(11) NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `option1` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option2` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option3` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option4` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `correctanswer` int(11) NOT NULL,
  `questiontype` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`questionid`, `description`, `option1`, `option2`, `option3`, `option4`, `longitude`, `latitude`, `correctanswer`, `questiontype`) VALUES
(5, 'vad heter jag?', 'jonathan?', 'katten?', '', '', 11.979287, 57.6882866, 1, 0),
(19, 'hejhej', 'a', 'b', 'c', '', 11.9981576, 57.6839534, 1, 0),
(20, 'två', 'a', 'b', '', '', 11.9982718, 57.6839661, 0, 0),
(21, 'Vilken låt i Skam delar artistnamn med de som gjorde Rosa Helikopter?', 'ha det!', 'cool girls in street', 'marcus o martinus', 'Dick in the air', 11.979108564555643, 57.68862830520989, 3, 0),
(22, 'Vilken karaktärs skådespelare går på Hartvig Nissen i verkligheten?', 'Nooras', 'Isaks', 'Jonas', 'Sana', 11.978763565421103, 57.68904156533146, 1, 0),
(23, 'Vilket år kom Skam för första gången?', '2010', '2017', '', '', 11.977473422884941, 57.689700334514185, 2015, 1),
(27, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(28, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(29, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(30, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(36, 'adsadas', 'dadwa', 'sdasd', '', '', -122.084, 40, 1, 0),
(39, 'djur Ektorp luftfuktighet 2', 'västvärldens Lucy', 'Hedin Leo den GT', 'rubin', '', 57.6881151, 11.978511, 1, 0),
(40, 'jag har fick riv Wii ej', '68', '69', '', '', 14.126341938972471, 58.47024393047411, 69, 1),
(42, 'sdfsdf', 'sdfsdfsdf', 'xsdfdsffs', '', '', -122.084, 40, 0, 0),
(46, 'tyck guug har du g', 'tyck', 'du', '', '', 11.9774184, 57.6897327, 1, 0),
(47, 'tyck', '0', '10660', '', '', 11.9774201, 57.6897259, 5820, 1),
(48, 'tyck', 'tyck', 'du', '', '', 11.9774184, 57.6897327, 1, 0),
(49, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(50, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(51, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(52, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(53, 'det är en del', '50', '100', '', '', 11.9774219, 57.689735, 100, 1),
(54, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(55, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(56, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(57, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(58, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(59, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(60, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(61, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(62, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1);

-- --------------------------------------------------------

--
-- Table structure for table `quiz`
--

CREATE TABLE `quiz` (
  `quizid` int(11) NOT NULL,
  `title` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `quiz`
--

INSERT INTO `quiz` (`quizid`, `title`, `description`) VALUES
(15, 'SKAM', 'En qwalk om skam!'),
(18, 'Runt EDIT', 'En kortpromenad!'),
(24, 'aasdasd', 'sadsadasd'),
(26, 'bbbbb', 'redigera mig'),
(28, 'dfgdfg', 'addadaw');

-- --------------------------------------------------------

--
-- Table structure for table `quizrelation`
--

CREATE TABLE `quizrelation` (
  `quizid` int(11) NOT NULL,
  `questionid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `quizrelation`
--

INSERT INTO `quizrelation` (`quizid`, `questionid`) VALUES
(15, 21),
(15, 22),
(15, 23),
(18, 27),
(18, 28),
(18, 29),
(18, 30),
(24, 36),
(26, 39),
(26, 3840),
(28, 42);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`accountid`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`questionid`);

--
-- Indexes for table `quiz`
--
ALTER TABLE `quiz`
  ADD PRIMARY KEY (`quizid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `accountid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;
--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `questionid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;
--
-- AUTO_INCREMENT for table `quiz`
--
ALTER TABLE `quiz`
  MODIFY `quizid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;--
-- Database: `id1452038_qwalkbase`
--
CREATE DATABASE IF NOT EXISTS `id1452038_qwalkbase` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id1452038_qwalkbase`;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `username` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `regdate` datetime NOT NULL,
  `password` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `accountid` int(11) NOT NULL,
  `level` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`username`, `regdate`, `password`, `accountid`, `level`) VALUES
('Nightingale', '2016-01-13 11:19:28', '29IcQRHMK2PXM', 1, 1),
('c', '2017-05-23 20:00:38', '29BWNfDGgnxOI', 110, 0),
(' ', '2017-05-23 16:10:21', '29h23.gtvV0rw', 109, 0),
('det är', '2017-05-22 06:29:31', '29LM0pwscaaME', 108, 0),
('a', '2017-05-10 11:38:07', '29Iqg34CL2vqs', 100, 0),
('tetigen', '2017-05-21 20:14:20', '29KJ7SJOmsuok', 107, 0),
('lol', '2017-05-08 20:39:46', '291xSphWm0Bss', 98, 0),
('testtt', '2017-05-21 20:10:14', '291rySmWTuKhg', 106, 0),
('kraft', '2017-05-21 19:35:14', '29Iqg34CL2vqs', 105, 0),
('aa', '2017-05-18 07:07:06', '29ufFn3TQRe3k', 103, 0),
('abc', '2017-05-21 19:29:50', '29ay9WyRkIbqA', 104, 0),
('b', '2017-05-17 06:55:56', '29Cs9fAhYPTAk', 102, 0),
('q', '2017-05-11 17:20:58', '29e1CEREYIuJw', 101, 0);

-- --------------------------------------------------------

--
-- Table structure for table `accountrelation`
--

CREATE TABLE `accountrelation` (
  `quizid` int(11) NOT NULL,
  `accountid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `accountrelation`
--

INSERT INTO `accountrelation` (`quizid`, `accountid`) VALUES
(15, 105),
(18, 105),
(24, 98),
(26, 102),
(28, 98);

-- --------------------------------------------------------

--
-- Table structure for table `friendrelation`
--

CREATE TABLE `friendrelation` (
  `accountid` int(11) NOT NULL,
  `friendid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `friendrelation`
--

INSERT INTO `friendrelation` (`accountid`, `friendid`) VALUES
(98, 1),
(98, 100),
(98, 101),
(100, 1),
(100, 101),
(103, 100),
(102, 100),
(104, 100),
(100, 105),
(102, 105),
(98, 102),
(98, 105),
(109, 105),
(109, 100),
(109, 102),
(105, 100);

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `questionid` int(11) NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `option1` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option2` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option3` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `option4` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `correctanswer` int(11) NOT NULL,
  `questiontype` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`questionid`, `description`, `option1`, `option2`, `option3`, `option4`, `longitude`, `latitude`, `correctanswer`, `questiontype`) VALUES
(5, 'vad heter jag?', 'jonathan?', 'katten?', '', '', 11.979287, 57.6882866, 1, 0),
(19, 'hejhej', 'a', 'b', 'c', '', 11.9981576, 57.6839534, 1, 0),
(20, 'två', 'a', 'b', '', '', 11.9982718, 57.6839661, 0, 0),
(21, 'Vilken låt i Skam delar artistnamn med de som gjorde Rosa Helikopter?', 'ha det!', 'cool girls in street', 'marcus o martinus', 'Dick in the air', 11.979108564555643, 57.68862830520989, 3, 0),
(22, 'Vilken karaktärs skådespelare går på Hartvig Nissen i verkligheten?', 'Nooras', 'Isaks', 'Jonas', 'Sana', 11.978763565421103, 57.68904156533146, 1, 0),
(23, 'Vilket år kom Skam för första gången?', '2010', '2017', '', '', 11.977473422884941, 57.689700334514185, 2015, 1),
(27, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(28, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(29, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(30, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(36, 'adsadas', 'dadwa', 'sdasd', '', '', -122.084, 40, 1, 0),
(39, 'djur Ektorp luftfuktighet 2', 'västvärldens Lucy', 'Hedin Leo den GT', 'rubin', '', 57.6881151, 11.978511, 1, 0),
(40, 'jag har fick riv Wii ej', '68', '69', '', '', 14.126341938972471, 58.47024393047411, 69, 1),
(42, 'sdfsdf', 'sdfsdfsdf', 'xsdfdsffs', '', '', -122.084, 40, 0, 0),
(46, 'tyck guug har du g', 'tyck', 'du', '', '', 11.9774184, 57.6897327, 1, 0),
(47, 'tyck', '0', '10660', '', '', 11.9774201, 57.6897259, 5820, 1),
(48, 'tyck', 'tyck', 'du', '', '', 11.9774184, 57.6897327, 1, 0),
(49, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(50, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(51, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(52, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(53, 'det är en del', '50', '100', '', '', 11.9774219, 57.689735, 100, 1),
(54, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(55, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(56, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(57, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(58, 'under de första åren är', 'skapad av en', 'skapad av de nio', 'skapad av', '', 11.97941567748785, 57.68827543540015, 1, 0),
(59, 'skapad den fredag på café', 'jag är så', 'jag är', 'editfoajen', '', 11.979432441294193, 57.68768312969643, 2, 0),
(60, 'skapad ett företag grundat kv', 'IF IF ta', 'hej följ så', 'Hyresgästföreningen', '', 11.978410854935646, 57.68739691980996, 2, 0),
(61, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1),
(62, 'det är en del', '50', '100', '', '', 57.68816665259725, 11.978474222123623, 64, 1);

-- --------------------------------------------------------

--
-- Table structure for table `quiz`
--

CREATE TABLE `quiz` (
  `quizid` int(11) NOT NULL,
  `title` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `quiz`
--

INSERT INTO `quiz` (`quizid`, `title`, `description`) VALUES
(15, 'SKAM', 'En qwalk om skam!'),
(18, 'Runt EDIT', 'En kortpromenad!'),
(24, 'aasdasd', 'sadsadasd'),
(26, 'bbbbb', 'redigera mig'),
(28, 'dfgdfg', 'addadaw');

-- --------------------------------------------------------

--
-- Table structure for table `quizrelation`
--

CREATE TABLE `quizrelation` (
  `quizid` int(11) NOT NULL,
  `questionid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `quizrelation`
--

INSERT INTO `quizrelation` (`quizid`, `questionid`) VALUES
(15, 21),
(15, 22),
(15, 23),
(18, 27),
(18, 28),
(18, 29),
(18, 30),
(24, 36),
(26, 39),
(26, 3840),
(28, 42);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`accountid`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`questionid`);

--
-- Indexes for table `quiz`
--
ALTER TABLE `quiz`
  ADD PRIMARY KEY (`quizid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `accountid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;
--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `questionid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;
--
-- AUTO_INCREMENT for table `quiz`
--
ALTER TABLE `quiz`
  MODIFY `quizid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
