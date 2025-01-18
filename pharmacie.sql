-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 18 jan. 2025 à 14:50
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `bibliotheque`
--

-- --------------------------------------------------------

--
-- Structure de la table `appareils_medicals`
--

CREATE TABLE `appareils_medicals` (
  `idAppareil` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `marque` varchar(255) DEFAULT NULL,
  `prix` decimal(10,2) DEFAULT NULL,
  `numeroSerie` varchar(255) DEFAULT NULL,
  `dateFabrication` date DEFAULT NULL,
  `garantieEnMois` int(11) DEFAULT NULL,
  `dateAchat` date DEFAULT NULL,
  `disponible` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `appareils_medicals`
--

INSERT INTO `appareils_medicals` (`idAppareil`, `nom`, `marque`, `prix`, `numeroSerie`, `dateFabrication`, `garantieEnMois`, `dateAchat`, `disponible`) VALUES
(6, 'Appareil2', 'Type1', 200.00, '9876544321', '2025-12-25', 24, '2024-12-25', 1),
(7, 'Appareil1', 'Brand1', 500.00, '987654321', '2024-12-25', 24, '2024-12-25', 1),
(10, 'Appareil1', 'Brand1', 500.00, '9876654321', '2024-12-25', 24, '2024-12-25', 1),
(14, 'Appareil1', 'Brand1', 500.00, '98776654321', '2024-12-25', 24, '2024-12-25', 1),
(27, 'Appareil2', 'Brand2', 600.00, '12345678901', '2024-12-26', 12, '2024-12-26', 1);

-- --------------------------------------------------------

--
-- Structure de la table `clients`
--

CREATE TABLE `clients` (
  `idClient` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `dateAdhesion` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `clients`
--

INSERT INTO `clients` (`idClient`, `nom`, `prenom`, `email`, `telephone`, `dateAdhesion`) VALUES
(107, 'ines', 'ines@example.com', 'ines@', '123 Main St', '1990-01-01'),
(108, 'John Doe', 'john.doe@example.com', '1234577890', '123 Main St', '1990-01-01'),
(109, 'Jane', 'Smith', 'jane.smith@example.com', '0987654321', '2021-05-15');

-- --------------------------------------------------------

--
-- Structure de la table `medicaments`
--

CREATE TABLE `medicaments` (
  `code` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `prix` decimal(10,2) DEFAULT NULL,
  `numeroSerie` bigint(20) DEFAULT NULL,
  `dateExpiration` date DEFAULT NULL,
  `type_medicament` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `medicaments`
--

INSERT INTO `medicaments` (`code`, `nom`, `genre`, `prix`, `numeroSerie`, `dateExpiration`, `type_medicament`) VALUES
(142, 'Med1', 'Genre1', 70.00, 123456789, '2025-02-18', NULL),
(143, 'Med1', 'Genre1', 70.00, 123456789, '2025-02-18', NULL),
(144, 'Med1', 'Genre1', 70.00, 123456789, '2025-02-18', NULL),
(145, 'Med1', 'Genre1', 70.00, 123456789, '2025-02-18', NULL),
(146, 'ExpiringMedicament', 'Category1', 70.00, 20000, '2025-02-18', NULL),
(147, 'Medicament1', 'Category1', 50.00, 12345678902, '2025-07-18', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `paiements_echelonnes`
--

CREATE TABLE `paiements_echelonnes` (
  `idPaiement` bigint(20) NOT NULL,
  `idClient` bigint(20) NOT NULL,
  `montant` double NOT NULL,
  `dateEcheance` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `paiements_echelonnes`
--

INSERT INTO `paiements_echelonnes` (`idPaiement`, `idClient`, `montant`, `dateEcheance`) VALUES
(39, 109, 200, '2025-02-18'),
(40, 109, 200, '2025-03-18'),
(41, 109, 200, '2025-04-18');

-- --------------------------------------------------------

--
-- Structure de la table `ventes_appareils`
--

CREATE TABLE `ventes_appareils` (
  `idVenteA` bigint(20) NOT NULL,
  `idClient` bigint(20) NOT NULL,
  `idAppareil` bigint(20) NOT NULL,
  `quantite` int(11) NOT NULL,
  `prixTotal` double NOT NULL,
  `dateVente` date NOT NULL,
  `modePaiement` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `ventes_appareils`
--

INSERT INTO `ventes_appareils` (`idVenteA`, `idClient`, `idAppareil`, `quantite`, `prixTotal`, `dateVente`, `modePaiement`) VALUES
(27, 109, 27, 1, 600, '2025-01-18', 'Échelonné');

-- --------------------------------------------------------

--
-- Structure de la table `ventes_medicaments`
--

CREATE TABLE `ventes_medicaments` (
  `idVenteM` bigint(20) NOT NULL,
  `idClient` bigint(20) NOT NULL,
  `idMedicament` bigint(20) NOT NULL,
  `quantite` int(11) NOT NULL,
  `prixTotal` double NOT NULL,
  `dateVente` date NOT NULL,
  `modePaiement` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `ventes_medicaments`
--

INSERT INTO `ventes_medicaments` (`idVenteM`, `idClient`, `idMedicament`, `quantite`, `prixTotal`, `dateVente`, `modePaiement`) VALUES
(15, 109, 147, 2, 100, '2025-01-18', 'Comptant');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `appareils_medicals`
--
ALTER TABLE `appareils_medicals`
  ADD PRIMARY KEY (`idAppareil`),
  ADD UNIQUE KEY `numeroSerie` (`numeroSerie`);

--
-- Index pour la table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`idClient`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Index pour la table `medicaments`
--
ALTER TABLE `medicaments`
  ADD PRIMARY KEY (`code`);

--
-- Index pour la table `paiements_echelonnes`
--
ALTER TABLE `paiements_echelonnes`
  ADD PRIMARY KEY (`idPaiement`),
  ADD KEY `idClient` (`idClient`);

--
-- Index pour la table `ventes_appareils`
--
ALTER TABLE `ventes_appareils`
  ADD PRIMARY KEY (`idVenteA`),
  ADD KEY `idClient` (`idClient`),
  ADD KEY `idAppareil` (`idAppareil`);

--
-- Index pour la table `ventes_medicaments`
--
ALTER TABLE `ventes_medicaments`
  ADD PRIMARY KEY (`idVenteM`),
  ADD KEY `idClient` (`idClient`),
  ADD KEY `idMedicament` (`idMedicament`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `appareils_medicals`
--
ALTER TABLE `appareils_medicals`
  MODIFY `idAppareil` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT pour la table `clients`
--
ALTER TABLE `clients`
  MODIFY `idClient` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;

--
-- AUTO_INCREMENT pour la table `medicaments`
--
ALTER TABLE `medicaments`
  MODIFY `code` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=148;

--
-- AUTO_INCREMENT pour la table `paiements_echelonnes`
--
ALTER TABLE `paiements_echelonnes`
  MODIFY `idPaiement` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT pour la table `ventes_appareils`
--
ALTER TABLE `ventes_appareils`
  MODIFY `idVenteA` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT pour la table `ventes_medicaments`
--
ALTER TABLE `ventes_medicaments`
  MODIFY `idVenteM` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `paiements_echelonnes`
--
ALTER TABLE `paiements_echelonnes`
  ADD CONSTRAINT `paiements_echelonnes_ibfk_1` FOREIGN KEY (`idClient`) REFERENCES `clients` (`idClient`);

--
-- Contraintes pour la table `ventes_appareils`
--
ALTER TABLE `ventes_appareils`
  ADD CONSTRAINT `ventes_appareils_ibfk_1` FOREIGN KEY (`idClient`) REFERENCES `clients` (`idClient`),
  ADD CONSTRAINT `ventes_appareils_ibfk_2` FOREIGN KEY (`idAppareil`) REFERENCES `appareils_medicals` (`idAppareil`);

--
-- Contraintes pour la table `ventes_medicaments`
--
ALTER TABLE `ventes_medicaments`
  ADD CONSTRAINT `ventes_medicaments_ibfk_1` FOREIGN KEY (`idClient`) REFERENCES `clients` (`idClient`),
  ADD CONSTRAINT `ventes_medicaments_ibfk_2` FOREIGN KEY (`idMedicament`) REFERENCES `medicaments` (`code`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
