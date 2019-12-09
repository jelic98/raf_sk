SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS `raf_sk_fetcher` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `raf_sk_fetcher`;

DROP TABLE IF EXISTS `weather`;
CREATE TABLE `weather` (
  `id` int(11) NOT NULL,
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `temp` double NOT NULL,
  `pressure` double NOT NULL,
  `humidity` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `weather` (`id`, `city`, `temp`, `pressure`, `humidity`) VALUES
(1, 'London', 0, 0, 0),

ALTER TABLE `weather`
  ADD PRIMARY KEY (`id`);
COMMIT;