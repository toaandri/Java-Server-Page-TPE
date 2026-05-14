<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.projetsimple.taxis.model.Driver" %>
<%@ page import="com.projetsimple.taxis.model.Vehicle" %>
<%@ page import="com.projetsimple.taxis.model.Ride" %>
<%
List<Driver> drivers = (List<Driver>) request.getAttribute("drivers");
List<Vehicle> vehicles = (List<Vehicle>) request.getAttribute("vehicles");
List<Ride> rides = (List<Ride>) request.getAttribute("rides");
Map<String, Object> stats = (Map<String, Object>) request.getAttribute("stats");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion flotte taxis</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
</head>
<body class="app-body">
<div class="app-shell stack">
    <div>
        <h1 class="app-title">Gestion flotte taxis</h1>
        <p class="app-subtitle">Operations chauffeurs, vehicules et courses en temps reel.</p>
    </div>

    <div class="grid-4">
        <div class="kpi"><div class="kpi-label">CA total</div><div class="kpi-value"><%= stats.get("revenue") %> Ar</div></div>
        <div class="kpi"><div class="kpi-label">Commission entreprise</div><div class="kpi-value"><%= stats.get("companyRevenue") %> Ar</div></div>
        <div class="kpi"><div class="kpi-label">Courses terminees</div><div class="kpi-value"><%= stats.get("completed") %></div></div>
        <div class="kpi"><div class="kpi-label">Courses annulees</div><div class="kpi-value"><%= stats.get("cancelled") %></div></div>
    </div>

    <div class="grid-3">
        <div class="card">
            <form class="stack" method="post" action="<%= request.getContextPath() %>/drivers">
                <h2 class="app-subtitle">Ajouter chauffeur</h2>
                <input name="fullName" placeholder="Nom complet" required>
                <input name="phone" placeholder="Telephone" required>
                <input name="licenseNumber" placeholder="Permis" required>
                <button class="btn btn-primary" type="submit">Ajouter</button>
            </form>
        </div>
        <div class="card">
            <form class="stack" method="post" action="<%= request.getContextPath() %>/vehicles">
                <h2 class="app-subtitle">Ajouter vehicule</h2>
                <input name="brandModel" placeholder="Marque / modele" required>
                <input name="plateNumber" placeholder="Immatriculation" required>
                <input type="number" min="0" name="mileage" placeholder="Kilometrage" required>
                <button class="btn btn-primary" type="submit">Ajouter</button>
            </form>
        </div>
        <div class="card">
            <form class="stack" method="post" action="<%= request.getContextPath() %>/rides">
                <h2 class="app-subtitle">Nouvelle course</h2>
                <input type="hidden" name="action" value="CREATE">
                <input name="pickup" placeholder="Depart" required>
                <input name="destination" placeholder="Destination" required>
                <input type="number" step="0.1" min="0" name="distanceKm" placeholder="Distance km" required>
                <input type="number" min="0" name="waitMinutes" placeholder="Attente (min)" required>
                <input type="number" step="0.1" min="0" name="extraFees" placeholder="Frais supplementaires" required>
                <button class="btn btn-success" type="submit">Creer</button>
            </form>
        </div>
    </div>

    <div class="grid-2">
        <div>
            <h2 class="app-subtitle">Chauffeurs</h2>
            <div class="table-wrap"><table>
                <thead><tr><th>Nom</th><th>Telephone</th><th>Permis</th><th>Statut</th></tr></thead>
                <tbody><% for (Driver d : drivers) { %><tr><td><%= d.getFullName() %></td><td><%= d.getPhone() %></td><td><%= d.getLicenseNumber() %></td><td><%= d.getStatus() %></td></tr><% } %></tbody>
            </table></div>
        </div>
        <div>
            <h2 class="app-subtitle">Vehicules</h2>
            <div class="table-wrap"><table>
                <thead><tr><th>Modele</th><th>Plaque</th><th>KM</th><th>Statut</th></tr></thead>
                <tbody><% for (Vehicle v : vehicles) { %><tr><td><%= v.getBrandModel() %></td><td><%= v.getPlateNumber() %></td><td><%= v.getMileage() %></td><td><%= v.getStatus() %></td></tr><% } %></tbody>
            </table></div>
        </div>
    </div>

    <h2 class="app-subtitle">Courses</h2>
    <div class="table-wrap"><table>
        <thead><tr><th>ID</th><th>Trajet</th><th>Statut</th><th>Prix</th><th>Chauffeur</th><th>Action</th></tr></thead>
        <tbody>
        <% for (Ride r : rides) { %>
        <tr>
            <td><%= r.getId() %></td>
            <td><%= r.getPickup() %> -> <%= r.getDestination() %></td>
            <td><%= r.getStatus() %></td>
            <td><%= r.getTotalPrice() %> Ar</td>
            <td><%= r.getDriverId() == null ? "Non assigne" : r.getDriverId() %></td>
            <td>
                <% if ("ASSIGNEE".equals(r.getStatus())) { %>
                <form method="post" action="<%= request.getContextPath() %>/rides" class="actions">
                    <input type="hidden" name="action" value="START"><input type="hidden" name="rideId" value="<%= r.getId() %>">
                    <button class="btn btn-primary" type="submit">Demarrer</button>
                </form>
                <% } else if ("EN_COURS".equals(r.getStatus())) { %>
                <form method="post" action="<%= request.getContextPath() %>/rides" class="actions">
                    <input type="hidden" name="action" value="FINISH"><input type="hidden" name="rideId" value="<%= r.getId() %>">
                    <button class="btn btn-success" type="submit">Terminer</button>
                </form>
                <% } else { %>-<% } %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table></div>
</div>
</body>
</html>
