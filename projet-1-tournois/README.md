# Projet 1 - Systeme de gestion de tournois sportifs

Application Java EE (Servlet/JSP) avec PostgreSQL.

## Fonctionnalites implementees

- Creation de tournoi (nom, sport, type, lieu, dates, duree, terrains).
- Gestion des equipes (ajout et liste).
- Generation automatique du calendrier:
  - Type `CHAMPIONNAT`: round-robin.
  - Type `KO`: appariement elimination directe (tour initial).
- Saisie des scores.
- Classement automatique (points, victoires, nuls, defaites, BP/BC, difference).
- Departage par points puis difference de buts puis buts marques puis buts encaisses.

## Prerequis

- JDK 11+
- Maven 3.8+
- PostgreSQL 13+
- Serveur compatible Jakarta Servlet 5 (Tomcat 10+ conseille)

## Configuration BDD

1. Creer la base:
   - `CREATE DATABASE tournament_db;`
2. Executer `sql/schema.sql`.
3. Adapter les identifiants dans `src/main/resources/db.properties` si besoin.

## Build

```bash
mvn clean package
```

Le fichier WAR sera genere dans `target/tournament-manager.war`.

## Deploiement

1. Deployer le WAR sur Tomcat 10+.
2. Ouvrir l'URL:
   - `http://localhost:8080/tournament-manager/`

## Parcours de test rapide

1. Creer un tournoi.
2. Ajouter 4 equipes.
3. Ouvrir "Matchs" pour generer automatiquement le calendrier.
4. Saisir les scores.
5. Ouvrir "Classement" pour voir la mise a jour en temps reel.
