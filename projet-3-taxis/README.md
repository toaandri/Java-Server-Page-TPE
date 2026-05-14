# Projet 3 - Gestion flotte de taxis

Stack: Java EE (Servlet/JSP) + PostgreSQL.

## Fonctionnalites
- Gestion chauffeurs et vehicules.
- Creation de courses.
- Attribution automatique au premier chauffeur/vehicule disponibles.
- Cycle de course: `ASSIGNEE -> EN_COURS -> TERMINEE`.
- Calcul tarifaire de base + commission entreprise.
- Tableau de bord avec statistiques (CA, commission, courses terminees/annulees).

## Setup
1. Creer la base: `CREATE DATABASE taxis_db;`
2. Executer `sql/schema.sql`
3. Ajuster `src/main/resources/db.properties`
4. Build: `mvn clean package`
5. Deployer `target/taxis-fleet-manager.war` sur Tomcat 10+
