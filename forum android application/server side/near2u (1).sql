-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Φιλοξενητής: 127.0.0.1
-- Χρόνος δημιουργίας: 19 Φεβ 2016 στις 16:19:01
-- Έκδοση διακομιστή: 10.1.9-MariaDB
-- Έκδοση PHP: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Βάση δεδομένων: `near2u`
--

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `n2u_forum_category`
--

CREATE TABLE `n2u_forum_category` (
  `id` int(11) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `n2u_forum_category`
--

INSERT INTO `n2u_forum_category` (`id`, `description`) VALUES
(1, 'Android'),
(2, 'Java'),
(3, 'Databases'),
(4, 'Users'),
(5, 'Miscellaneous');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `n2u_forum_post`
--

CREATE TABLE `n2u_forum_post` (
  `id` int(11) NOT NULL,
  `text` text NOT NULL,
  `date_created` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `n2u_forum_post`
--

INSERT INTO `n2u_forum_post` (`id`, `text`, `date_created`, `user_id`, `topic_id`) VALUES
(1, 'kanto copy kai meta paste panw sto fakelo drawable...', '2016-02-17 01:14:05', 1, 5),
(2, 'afou mpeis sto localhost/phpmyadmin epilegeis ti vasi sou kai vriskeis tn epilogi import me tin opoia mporeis na eisageis to sql arxeio sou', '2016-02-17 01:19:38', 1, 2),
(3, 'nai...kai 8elw na to eisagw sto project mou', '2016-02-17 01:23:03', 1, 5),
(4, 'sas euxaristw apla 8elw na dw kai enan enallaktiko tropo', '2016-02-17 01:26:53', 1, 6),
(5, 'k egw to idio 8a sou protinw...polu kalo', '2016-02-17 01:38:06', 1, 7),
(6, 'to pro java programming', '2016-02-17 01:40:57', 1, 7),
(7, 'to arxeio pou 8a xrisimopoiiseis ws drawable einai se kapoio fakelo tou pc sou?', '2016-02-17 01:42:41', 1, 5),
(8, 'uparxoun idi pinakes me to idio onoma...prepei na tous diagrapsw?', '2016-02-17 01:45:49', 1, 1),
(9, 'oxi apla kaneis overwrite', '2016-02-17 01:45:53', 1, 1),
(10, 'euxaristw', '2016-02-17 01:58:57', 1, 8),
(11, 'npliktrologise ston browser localhost/phpmyadmin/(to onoma pou edwses st vasi sou)', '2016-02-17 02:02:05', 1, 8),
(12, 'swsta... dn to eixa skeftei', '2016-02-17 02:27:43', 1, 9),
(13, 'tote mporeis na sumperaneis apo ta alla stoixeia pou exeis dwsei poio einai to id pou psaxneis', '2016-02-17 02:29:33', 1, 9),
(14, 'nai omws uparxoun kai alla stoixeia...', '2016-02-17 02:32:08', 1, 9),
(15, 'exeis kapoio pinaka pou na krataei tis times tou user id?', '2016-02-17 02:33:08', 1, 9),
(16, 'kane ena check sto prwto gramma tou onomatos an einai kefalaio', '2016-02-17 20:45:13', 1, 10),
(17, 'giati den ta ftiaxneis me xml?', '2016-02-17 21:44:04', 1, 6),
(18, 'kai vevaia mporeis...einai pio duskolo omws', '2016-02-17 23:05:01', 1, 6),
(19, 'me tin epilogi import pou exei to phpmyadmin mporeis na vreis kai na eisageis tous pinakes pou exeis ftiaxei', '2016-02-18 19:45:24', 1, 1),
(20, 'to Head First Android Development einai mia kali epilogi', '2016-02-19 01:14:36', 1, 14),
(21, 'Don''t worry...', '2016-02-19 11:46:42', 1, 15);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `n2u_forum_topic`
--

CREATE TABLE `n2u_forum_topic` (
  `id` int(11) NOT NULL,
  `text` text NOT NULL,
  `date_created` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `forum_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `n2u_forum_topic`
--

INSERT INTO `n2u_forum_topic` (`id`, `text`, `date_created`, `user_id`, `forum_id`, `title`) VALUES
(1, 'pws 8a kanw Import pinakes sti database mou?', '2016-02-16 23:43:20', 1, 3, 'pws 8a kanw Import pinakes sti database mou?'),
(2, 'pws 8a kanw Import pinakes sti database mou?', '2016-02-16 23:45:32', 1, 1, 'pws 8a kanw Import pinakes sti database mou?'),
(3, 'pws mporw na emfanisw ari8misi stis seires tou kwdika sto android studio?', '2016-02-16 23:48:44', 1, 1, 'pws mporw na emfanisw ari8misi stis seires tou kwdika sto android studio?'),
(4, 'den mporw na kalesw mia function.xreiazomai voi8eia!!!', '2016-02-17 00:39:52', 1, 2, 'function'),
(5, 'pws mporw na pros8esw drawable se xml layout? ', '2016-02-17 01:05:19', 1, 1, 'pws mporw na pros8esw drawable se xml layout?'),
(6, 'mporw na ftiaxw ta layout tis efarmogis mou xrisimopoiwntas java', '2016-02-17 01:26:29', 1, 2, 'mporw na ftiaxw ta layout tis efarmogis mou xrisimopoiwntas java'),
(7, 'poio sugramma mou protinete gia na ma8w java?', '2016-02-17 01:32:25', 1, 2, 'poio sugramma mou protinete gia na ma8w java?'),
(8, 'pws mporw na vrw ti database pou dimiourgisa?', '2016-02-17 01:48:27', 1, 3, 'pws mporw na vrw ti database pou dimiourgisa?'),
(9, 'pws 8a vrw to user Id pou apo8ikeutike stin SQL datbase mou? ', '2016-02-17 02:26:41', 1, 4, 'pws 8a vrw to user Id pou apo8ikeutike stin SQL datbase mou?'),
(10, 'giati den dexetai to project name pou orizw otan dimiourgw neo project?', '2016-02-17 20:44:50', 1, 2, 'giati den dexetai to project name pou orizw otan dimiourgw neo project?'),
(11, 'gnwrizete an uparxei vivlio java sti vivlio8iki tou panepistimiou?', '2016-02-17 21:24:40', 1, 2, 'gnwrizete an uparxei vivlio java sti vivlio8iki tou panepistimiou?'),
(12, 'pws 8a dimiourgisw mia nea class sto project mou?', '2016-02-17 23:04:38', 1, 2, 'pws 8a dimiourgisw mia nea class sto project mou?'),
(13, 'pws mporw na ftiaxw mia database xrisimopoiwntas php?', '2016-02-18 19:45:12', 1, 3, 'pws mporw na ftiaxw mia database xrisimopoiwntas php?'),
(14, 'exete na mou proteinete kapoio biblio sxetika me to android development?', '2016-02-19 01:14:24', 1, 1, 'exete na mou proteinete kapoio biblio sxetika me to android development?'),
(15, 'exete na mou proteinete kapoio biblio sxetika me to android development?', '2016-02-19 11:45:46', 1, 4, 'exete na mou proteinete kapoio biblio sxetika me to android development?');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `n2u_message`
--

CREATE TABLE `n2u_message` (
  `id` int(11) NOT NULL,
  `MESSAGE` text NOT NULL,
  `DATE_SENT` datetime NOT NULL,
  `FROM_USER_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `n2u_message`
--

INSERT INTO `n2u_message` (`id`, `MESSAGE`, `DATE_SENT`, `FROM_USER_ID`) VALUES
(1, 'asasa', '2016-02-14 00:00:00', 1),
(2, 'asasa', '2016-02-14 00:00:00', 1),
(3, 'eeeeee', '2016-02-14 00:00:00', 1),
(4, 'wow', '2016-02-14 00:00:00', 2),
(5, 'eeeeeeerrrrrrrr', '2016-02-14 00:00:00', 1),
(6, 'lolo', '2016-02-15 00:00:00', 1),
(7, '', '2016-02-19 03:55:22', 1),
(8, 'lololo', '2016-02-19 04:05:55', 1),
(9, 'lalala', '2016-02-19 04:06:09', 1),
(10, 'lolp', '2016-02-19 04:11:15', 1),
(11, 'lklk', '2016-02-19 04:12:04', 1),
(12, 'sss', '2016-02-19 04:14:22', 1),
(13, 'sss', '2016-02-19 04:15:47', 1),
(14, 'qqqq', '2016-02-19 04:16:23', 1),
(15, 'qqqq', '2016-02-19 04:17:32', 1),
(16, 'qqqq', '2016-02-19 04:17:43', 1),
(17, 'qqqq', '2016-02-19 04:18:22', 1),
(18, 'qqqq', '2016-02-19 04:18:35', 1),
(19, 'krkrkr', '2016-02-19 04:19:18', 1),
(20, 'krkrkr', '2016-02-19 04:20:32', 1),
(21, 'lol', '2016-02-19 04:23:14', 1),
(22, 'lol', '2016-02-19 04:26:11', 1),
(23, 'lol', '2016-02-19 04:26:36', 1),
(24, 'lol', '2016-02-19 04:26:52', 1),
(25, 'lol', '2016-02-19 04:27:03', 1),
(26, 'trtrtr', '2016-02-19 04:27:41', 1),
(27, 'single', '2016-02-19 04:30:25', 1),
(28, 'message', '2016-02-19 04:34:17', 1),
(29, 'message', '2016-02-19 04:35:06', 1),
(30, 'message', '2016-02-19 04:35:44', 1),
(31, 'message123', '2016-02-19 04:36:30', 1),
(32, 'kikipo', '2016-02-19 04:37:50', 1),
(33, 'rÅ™tyuhgffy', '2016-02-19 04:39:41', 1),
(34, 'hghhgggyhfg', '2016-02-19 04:40:49', 1),
(35, 'new message', '2016-02-19 09:27:11', 8),
(36, 'new broadcast', '2016-02-19 09:27:35', 8),
(37, 'hi lina', '2016-02-19 11:42:21', 14),
(38, 'Hi Panos! How are you?', '2016-02-19 13:00:30', 15),
(39, 'hello!!!', '2016-02-19 13:01:23', 15);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `n2u_message_to_receivers`
--

CREATE TABLE `n2u_message_to_receivers` (
  `id` int(11) NOT NULL,
  `message_id` int(11) NOT NULL,
  `receiver_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `n2u_message_to_receivers`
--

INSERT INTO `n2u_message_to_receivers` (`id`, `message_id`, `receiver_id`) VALUES
(1, 1, 2),
(2, 1, 3),
(3, 4, 3),
(6, 1, 2),
(13, 1, 2),
(14, 1, 2),
(15, 1, 1),
(16, 1, 1),
(17, 1, 1),
(18, 1, 1),
(19, 1, 1),
(20, 1, 1),
(21, 1, 1),
(22, 1, 1),
(23, 1, 1),
(24, 1, 1),
(25, 1, 1),
(26, 1, 1),
(27, 1, 1),
(28, 1, 1),
(29, 1, 1),
(30, 1, 1),
(31, 19, 1),
(32, 19, 1),
(33, 19, 1),
(34, 19, 1),
(35, 21, 1),
(36, 21, 1),
(37, 21, 1),
(38, 21, 1),
(39, 26, 2),
(40, 26, 3),
(41, 26, 4),
(42, 26, 5),
(44, 28, 2),
(45, 31, 2),
(48, 34, 2),
(49, 35, 2),
(50, 36, 1),
(51, 36, 2),
(52, 36, 3),
(53, 36, 4),
(54, 36, 5),
(55, 36, 6),
(56, 36, 7),
(57, 37, 3),
(58, 38, 2),
(59, 39, 1),
(60, 39, 2),
(61, 39, 3),
(62, 39, 4),
(63, 39, 5),
(64, 39, 6),
(65, 39, 7),
(66, 39, 8),
(67, 39, 9),
(68, 39, 10),
(69, 39, 11),
(70, 39, 12),
(71, 39, 13),
(72, 39, 14);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `n2u_user`
--

CREATE TABLE `n2u_user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `occupation` varchar(255) NOT NULL,
  `birth_date` date NOT NULL,
  `hobbies` varchar(255) NOT NULL,
  `interests` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `n2u_user`
--

INSERT INTO `n2u_user` (`id`, `username`, `password`, `occupation`, `birth_date`, `hobbies`, `interests`) VALUES
(1, 'ioanna', 'ioanna', '', '0000-00-00', '', ''),
(2, 'panos', 'panos', 'student', '1989-04-09', 'studying', 'android forum development'),
(3, 'lina', 'lina', '', '0000-00-00', '', ''),
(4, 'george', 'george', '', '0000-00-00', '', ''),
(5, 'nick', 'nick', '', '0000-00-00', '', ''),
(6, 'Mixalis', 'q', 'q', '1988-06-09', '', ''),
(7, 'Maria', 'lo', 'lo', '1991-02-04', 'lo', 'lo'),
(8, 'Katerina', '13345', 'fhvg', '0000-00-00', 'cgyhvd', 'dhhvv'),
(9, 'Bob', '12345', 'gggg', '0000-00-00', 'fffgh', 'ggg5'),
(10, 'Christos', '11111', 'hrjtjt', '0000-00-00', 'hdhdj', 'cjfjfr'),
(11, 'Alexis', 'lllll', 'fhgty', '0000-00-00', 'ghgv', 'fghgt'),
(12, 'Anastasia', '1111', 'Ï†Î¹Ï†Î¹Ï†', '0000-00-00', 'Ï‡Î¾Î´', 'Î¾Ï†Î¾Ï†Î¹4Î¾Ï‡Î¾Ï†Î¾'),
(13, 'kostas', '34456', 'Î·Î´Î·Î´Î·', '0000-00-00', '', ''),
(14, 'panos', 'lazaris', 'developer', '0000-00-00', 'football', 'java developing'),
(15, 'dimitris', 'dimitris', 'developer', '0000-00-00', 'football', 'java');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `n2u_user_history`
--

CREATE TABLE `n2u_user_history` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `longitude` double DEFAULT NULL,
  `date_visited` datetime NOT NULL,
  `latitude` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `n2u_user_history`
--

INSERT INTO `n2u_user_history` (`id`, `user_id`, `longitude`, `date_visited`, `latitude`) VALUES
(1, 1, 23.66037087, '2016-02-18 22:11:16', 37.94473526),
(2, 1, 23.66037087, '2016-02-18 22:12:41', 37.94473526),
(3, 1, 23.64236, '2016-02-18 22:32:24', 37.97011),
(4, 2, 23.64698, '2016-02-18 22:32:24', 37.94299),
(5, 3, 23.70385, '2016-02-18 22:32:24', 38.03078),
(6, 1, 23.66037087, '2016-02-18 22:43:46', 37.94473526),
(7, 1, 23.66037087, '2016-02-18 22:43:54', 37.94473526),
(8, 1, 23.66037087, '2016-02-18 23:17:43', 37.94473526),
(9, 1, 23.66037087, '2016-02-18 23:19:59', 37.94473526),
(10, 1, 23.66037087, '2016-02-18 23:21:10', 37.94473526),
(11, 1, 23.66037087, '2016-02-18 23:23:18', 37.94473526),
(12, 1, 23.66037087, '2016-02-18 23:27:18', 37.94473526),
(13, 1, 23.66037087, '2016-02-18 23:29:21', 37.94473526),
(14, 1, 23.71566702, '2016-02-18 23:34:39', 38.0130445),
(15, 1, 23.71566702, '2016-02-18 23:37:15', 38.0130445),
(16, 1, 23.71566702, '2016-02-18 23:41:38', 38.0130445),
(17, 1, 23.71566702, '2016-02-18 23:43:08', 38.0130445),
(18, 1, 23.71566702, '2016-02-18 23:52:18', 38.0130445),
(19, 1, 23.71566702, '2016-02-18 23:52:59', 38.0130445),
(20, 1, 23.71566702, '2016-02-19 00:10:32', 38.0130445),
(21, 1, 23.71566702, '2016-02-19 00:11:23', 38.0130445),
(22, 1, 23.71566702, '2016-02-19 00:15:52', 38.0130445),
(23, 1, 23.71566702, '2016-02-19 00:18:33', 38.0130445),
(24, 1, 23.71566702, '2016-02-19 00:19:14', 38.0130445),
(25, 1, 23.71566702, '2016-02-19 00:19:35', 38.0130445),
(26, 1, 23.71566702, '2016-02-19 00:21:21', 38.0130445),
(27, 1, 23.71566702, '2016-02-19 00:24:25', 38.0130445),
(28, 1, 23.71566702, '2016-02-19 00:25:31', 38.0130445),
(29, 1, 23.71566702, '2016-02-19 00:25:53', 38.0130445),
(30, 1, 23.71566702, '2016-02-19 00:27:18', 38.0130445),
(31, 1, 23.71566702, '2016-02-19 00:41:53', 38.0130445),
(32, 1, 23.71566702, '2016-02-19 01:14:57', 38.0130445),
(33, 1, 23.71566702, '2016-02-19 01:15:04', 38.0130445),
(34, 1, 23.71566702, '2016-02-19 01:15:09', 38.0130445),
(35, 1, 23.71566702, '2016-02-19 02:16:58', 38.0130445),
(36, 1, 23.71566702, '2016-02-19 02:17:25', 38.0130445),
(37, 1, 23.71566702, '2016-02-19 02:26:17', 38.0130445),
(38, 1, 23.71566702, '2016-02-19 02:28:21', 38.0130445),
(39, 1, 23.71566702, '2016-02-19 02:28:38', 38.0130445),
(40, 1, 23.71566702, '2016-02-19 02:29:42', 38.0130445),
(41, 1, 23.71566702, '2016-02-19 02:38:02', 38.0130445),
(42, 1, 23.71566702, '2016-02-19 02:45:29', 38.0130445),
(43, 1, 23.71566702, '2016-02-19 03:04:44', 38.0130445),
(44, 1, 23.71566702, '2016-02-19 03:30:28', 38.0130445),
(45, 1, 23.71566702, '2016-02-19 03:55:18', 38.0130445),
(46, 1, 23.71566702, '2016-02-19 04:05:42', 38.0130445),
(47, 1, 23.71541164, '2016-02-19 04:40:39', 38.01322429),
(48, 1, 23.71541164, '2016-02-19 05:15:07', 38.01322429),
(49, 1, 23.71541164, '2016-02-19 05:18:25', 38.01322429),
(50, 1, 23.71541164, '2016-02-19 05:19:13', 38.01322429),
(51, 1, 23.71541164, '2016-02-19 05:19:55', 38.01322429),
(52, 1, 23.71541164, '2016-02-19 05:21:06', 38.01322429),
(53, 1, 23.71541164, '2016-02-19 05:24:05', 38.01322429),
(54, 1, 23.71541164, '2016-02-19 05:25:27', 38.01322429),
(55, 1, 23.71541164, '2016-02-19 05:27:37', 38.01322429),
(56, 1, 23.71541164, '2016-02-19 05:30:08', 38.01322429),
(57, 1, 23.71541164, '2016-02-19 05:30:31', 38.01322429),
(58, 1, 23.71541164, '2016-02-19 05:30:39', 38.01322429),
(59, 1, 23.71541164, '2016-02-19 05:31:48', 38.01322429),
(60, 1, 23.71541164, '2016-02-19 05:39:28', 38.01322429),
(61, 1, 23.71541164, '2016-02-19 05:41:18', 38.01322429),
(62, 1, 23.71541164, '2016-02-19 05:42:44', 38.01322429),
(63, 1, 23.71541164, '2016-02-19 05:45:02', 38.01322429),
(64, 8, 23.71541164, '2016-02-19 09:26:43', 38.01322429),
(65, 9, 23.71541164, '2016-02-19 09:34:10', 38.01322429),
(66, 1, 23.71541164, '2016-02-19 09:40:00', 38.01322429),
(67, 1, 23.71541164, '2016-02-19 09:45:45', 38.01322429),
(68, 1, 23.71541164, '2016-02-19 09:47:20', 38.01322429),
(69, 1, 23.71541164, '2016-02-19 10:49:25', 38.01322429),
(70, 1, 23.71541164, '2016-02-19 10:52:06', 38.01322429),
(71, 1, 23.71541164, '2016-02-19 11:15:05', 38.01322429),
(72, 1, 23.71541164, '2016-02-19 11:18:46', 38.01322429),
(73, 1, 23.71541164, '2016-02-19 11:30:14', 38.01322429),
(74, 1, 23.71541164, '2016-02-19 11:32:25', 38.01322429),
(75, 14, 23.71541164, '2016-02-19 11:38:43', 38.01322429),
(76, 14, 23.71541164, '2016-02-19 11:40:30', 38.01322429),
(77, 15, 23.71541164, '2016-02-19 12:16:47', 38.01322429),
(78, 15, 23.71541164, '2016-02-19 12:47:53', 38.01322429),
(79, 15, 23.71541164, '2016-02-19 12:59:31', 38.01322429),
(80, 15, 23.71541164, '2016-02-19 14:26:28', 38.01322429);

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `n2u_forum_category`
--
ALTER TABLE `n2u_forum_category`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ID` (`id`);

--
-- Ευρετήρια για πίνακα `n2u_forum_post`
--
ALTER TABLE `n2u_forum_post`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ID` (`id`),
  ADD KEY `fk_post_user_id` (`user_id`),
  ADD KEY `fk_post_topic_id` (`topic_id`);

--
-- Ευρετήρια για πίνακα `n2u_forum_topic`
--
ALTER TABLE `n2u_forum_topic`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ID` (`id`),
  ADD KEY `fk_topic_user_id` (`user_id`),
  ADD KEY `fk_topic_category_id` (`forum_id`);

--
-- Ευρετήρια για πίνακα `n2u_message`
--
ALTER TABLE `n2u_message`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ID` (`id`),
  ADD KEY `fk_message_user_id` (`FROM_USER_ID`);

--
-- Ευρετήρια για πίνακα `n2u_message_to_receivers`
--
ALTER TABLE `n2u_message_to_receivers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `fk_message_id` (`message_id`),
  ADD KEY `fk_receiver_id` (`receiver_id`);

--
-- Ευρετήρια για πίνακα `n2u_user`
--
ALTER TABLE `n2u_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Ευρετήρια για πίνακα `n2u_user_history`
--
ALTER TABLE `n2u_user_history`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ID` (`id`),
  ADD KEY `fk_history_user_id` (`user_id`);

--
-- AUTO_INCREMENT για άχρηστους πίνακες
--

--
-- AUTO_INCREMENT για πίνακα `n2u_forum_category`
--
ALTER TABLE `n2u_forum_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT για πίνακα `n2u_forum_post`
--
ALTER TABLE `n2u_forum_post`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT για πίνακα `n2u_forum_topic`
--
ALTER TABLE `n2u_forum_topic`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT για πίνακα `n2u_message`
--
ALTER TABLE `n2u_message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;
--
-- AUTO_INCREMENT για πίνακα `n2u_message_to_receivers`
--
ALTER TABLE `n2u_message_to_receivers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=73;
--
-- AUTO_INCREMENT για πίνακα `n2u_user`
--
ALTER TABLE `n2u_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT για πίνακα `n2u_user_history`
--
ALTER TABLE `n2u_user_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;
--
-- Περιορισμοί για άχρηστους πίνακες
--

--
-- Περιορισμοί για πίνακα `n2u_forum_post`
--
ALTER TABLE `n2u_forum_post`
  ADD CONSTRAINT `fk_post_topic_id` FOREIGN KEY (`topic_id`) REFERENCES `n2u_forum_topic` (`id`),
  ADD CONSTRAINT `fk_post_user_id` FOREIGN KEY (`user_id`) REFERENCES `n2u_user` (`id`);

--
-- Περιορισμοί για πίνακα `n2u_forum_topic`
--
ALTER TABLE `n2u_forum_topic`
  ADD CONSTRAINT `fk_topic_category_id` FOREIGN KEY (`forum_id`) REFERENCES `n2u_forum_category` (`id`),
  ADD CONSTRAINT `fk_topic_user_id` FOREIGN KEY (`user_id`) REFERENCES `n2u_user` (`id`);

--
-- Περιορισμοί για πίνακα `n2u_message`
--
ALTER TABLE `n2u_message`
  ADD CONSTRAINT `fk_message_user_id` FOREIGN KEY (`FROM_USER_ID`) REFERENCES `n2u_user` (`id`);

--
-- Περιορισμοί για πίνακα `n2u_message_to_receivers`
--
ALTER TABLE `n2u_message_to_receivers`
  ADD CONSTRAINT `fk_message_id` FOREIGN KEY (`message_id`) REFERENCES `n2u_message` (`id`),
  ADD CONSTRAINT `fk_receiver_id` FOREIGN KEY (`receiver_id`) REFERENCES `n2u_user` (`id`);

--
-- Περιορισμοί για πίνακα `n2u_user_history`
--
ALTER TABLE `n2u_user_history`
  ADD CONSTRAINT `fk_history_user_id` FOREIGN KEY (`user_id`) REFERENCES `n2u_user` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
