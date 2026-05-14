# Projet 4 - Gestion parking

Stack: Java EE (Servlet/JSP) + PostgreSQL.

## Fonctionnalites
- Gestion des places (standard/VIP).
- Reservation de place avec priorite VIP.
- Entree/sortie vehicule.
- Calcul tarifaire horaire (abonne gratuit).
- Disponibilite en temps reel.
- Historique entrees/sorties et reservations.

## Setup
1. `CREATE DATABASE parking_db;`
2. Executer `sql/schema.sql`
3. Adapter `src/main/resources/db.properties`
4. Build: `mvn clean package`
5. Deployer `target/parking-manager.war` sur Tomcat 10+
