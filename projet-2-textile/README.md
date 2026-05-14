# Projet 2 - Gestion de commandes textile

Application Java EE (Servlet/JSP) avec PostgreSQL.

## Couvre le CDC
- Creation de commande client.
- Workflow impose: `COUPE -> COUTURE -> FINITION -> LIVRAISON`.
- Blocage si etape precedente non terminee.
- Statut commande (`EN_ATTENTE`, `EN_PRODUCTION`, `LIVRE`).
- Estimation simple de retard/planning.

## Prerequis
- JDK 11+
- Maven 3.8+
- PostgreSQL
- Tomcat 10+

## Base de donnees
1. `CREATE DATABASE textile_db;`
2. Executer `sql/schema.sql`.
3. Ajuster `src/main/resources/db.properties` si besoin.

## Build
```bash
mvn clean package
```

## Execution
- Deployer `target/textile-orders-manager.war`
- Ouvrir `http://localhost:8080/textile-orders-manager/`
