# Projet 5 - Service de livraison

Stack: Java EE (Servlet/JSP) + PostgreSQL.

## Fonctionnalites
- Gestion des livreurs.
- Creation de commandes de livraison.
- Attribution automatique d'un livreur disponible.
- Cycle de statut respecte:
  - `EN_ATTENTE -> ASSIGNEE -> EN_COURS -> LIVREE`
  - `ANNULEE` possible sauf si deja livree.
- Calcul de prix par distance, poids et frais supplementaires.
- Tableau de bord avec statistiques (revenu total, livrees, annulees).

## Setup
1. `CREATE DATABASE delivery_db;`
2. Executer `sql/schema.sql`
3. Adapter `src/main/resources/db.properties`
4. Build: `mvn clean package`
5. Deployer `target/delivery-manager.war` sur Tomcat 10+
