<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.projetsimple.livraison.model.DeliveryPerson" %>
<%@ page import="com.projetsimple.livraison.model.DeliveryOrder" %>
<%
List<DeliveryPerson> people = (List<DeliveryPerson>) request.getAttribute("people");
List<DeliveryOrder> orders = (List<DeliveryOrder>) request.getAttribute("orders");
Map<String, Object> stats = (Map<String, Object>) request.getAttribute("stats");
String flash = (String) session.getAttribute("flash");
if (flash != null) session.removeAttribute("flash");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Service de livraison</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
</head>
<body class="app-body">
<div class="app-shell stack">
    <div>
        <h1 class="app-title">Service de livraison</h1>
        <p class="app-subtitle">Suivi operationnel des livreurs et des commandes.</p>
    </div>
    <% if (flash != null) { %><div class="card"><%= flash %></div><% } %>

    <div class="grid-3">
        <div class="kpi"><div class="kpi-label">Revenu total</div><div class="kpi-value"><%= stats.get("totalRevenue") %> Ar</div></div>
        <div class="kpi"><div class="kpi-label">Livrees</div><div class="kpi-value"><%= stats.get("delivered") %></div></div>
        <div class="kpi"><div class="kpi-label">Annulees</div><div class="kpi-value"><%= stats.get("cancelled") %></div></div>
    </div>

    <div class="grid-3">
        <div class="card">
            <form class="stack" method="post" action="<%= request.getContextPath() %>/delivery">
                <h2 class="app-subtitle">Ajouter livreur</h2>
                <input type="hidden" name="action" value="ADD_PERSON">
                <input name="fullName" placeholder="Nom" required>
                <input name="phone" placeholder="Telephone" required>
                <button class="btn btn-primary" type="submit">Ajouter</button>
            </form>
        </div>
        <div class="card" style="grid-column: span 2;">
            <form class="stack" method="post" action="<%= request.getContextPath() %>/delivery">
                <h2 class="app-subtitle">Nouvelle commande</h2>
                <input type="hidden" name="action" value="CREATE_ORDER">
                <div class="grid-4">
                    <input name="clientName" placeholder="Client" required>
                    <input name="pickupAddress" placeholder="Pickup" required>
                    <input name="deliveryAddress" placeholder="Destination" required>
                    <input type="number" step="0.1" min="0" name="packageWeight" placeholder="Poids" required>
                    <input name="packageSize" placeholder="Taille" required>
                    <input type="number" step="0.1" min="0" name="distanceKm" placeholder="Distance km" required>
                    <input type="number" step="0.1" min="0" name="extraFees" placeholder="Frais supp" required>
                </div>
                <div class="actions"><button class="btn btn-success" type="submit">Creer commande</button></div>
            </form>
        </div>
    </div>

    <div class="grid-3">
        <div>
            <h2 class="app-subtitle">Livreurs</h2>
            <div class="table-wrap"><table>
                <thead><tr><th>Nom</th><th>Tel</th><th>Statut</th></tr></thead>
                <tbody><% for (DeliveryPerson p : people) { %><tr><td><%= p.getFullName() %></td><td><%= p.getPhone() %></td><td><%= p.getStatus() %></td></tr><% } %></tbody>
            </table></div>
        </div>
        <div style="grid-column: span 2;">
            <h2 class="app-subtitle">Commandes</h2>
            <div class="table-wrap"><table>
                <thead><tr><th>ID</th><th>Client</th><th>Trajet</th><th>Statut</th><th>Prix</th><th>Livreur</th><th>Action</th></tr></thead>
                <tbody>
                <% for (DeliveryOrder o : orders) { %>
                <tr>
                    <td><%= o.getId() %></td>
                    <td><%= o.getClientName() %></td>
                    <td><%= o.getPickupAddress() %> -> <%= o.getDeliveryAddress() %></td>
                    <td><%= o.getStatus() %></td>
                    <td><%= o.getPrice() %> Ar</td>
                    <td><%= o.getDeliveryPersonId() == null ? "-" : o.getDeliveryPersonId() %></td>
                    <td>
                        <% if ("ASSIGNEE".equals(o.getStatus())) { %>
                        <form method="post" action="<%= request.getContextPath() %>/delivery" class="actions">
                            <input type="hidden" name="action" value="START"><input type="hidden" name="orderId" value="<%= o.getId() %>">
                            <button class="btn btn-primary" type="submit">Demarrer</button>
                        </form>
                        <% } else if ("EN_COURS".equals(o.getStatus())) { %>
                        <form method="post" action="<%= request.getContextPath() %>/delivery" class="actions">
                            <input type="hidden" name="action" value="DELIVER"><input type="hidden" name="orderId" value="<%= o.getId() %>">
                            <button class="btn btn-success" type="submit">Livrer</button>
                        </form>
                        <% } %>
                        <% if (!"LIVREE".equals(o.getStatus())) { %>
                        <form method="post" action="<%= request.getContextPath() %>/delivery" class="actions">
                            <input type="hidden" name="action" value="CANCEL"><input type="hidden" name="orderId" value="<%= o.getId() %>">
                            <button class="btn btn-secondary" type="submit">Annuler</button>
                        </form>
                        <% } %>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table></div>
        </div>
    </div>
</div>
</body>
</html>
