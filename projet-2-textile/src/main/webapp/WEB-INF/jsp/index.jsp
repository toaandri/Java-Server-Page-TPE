<%@ page import="java.util.List" %>
<%@ page import="com.projetsimple.textile.model.TextileOrder" %>
<%
    List<TextileOrder> orders = (List<TextileOrder>) request.getAttribute("orders");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion commandes textile</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
</head>
<body class="app-body">
<div class="app-shell stack">
    <div class="app-header">
        <div>
            <h1 class="app-title">Commandes textile</h1>
            <p class="app-subtitle">Suivi des commandes clients et progression de production.</p>
        </div>
        <a class="btn btn-primary" href="<%= request.getContextPath() %>/orders">Nouvelle commande</a>
    </div>

    <div class="table-wrap">
    <table>
        <thead>
        <tr><th>ID</th><th>Client</th><th>Article</th><th>Quantite</th><th>Livraison prevue</th><th>Statut</th><th>Action</th></tr>
        </thead>
        <tbody>
        <% if (orders != null) {
            for (TextileOrder order : orders) { %>
        <tr>
            <td><%= order.getId() %></td>
            <td><%= order.getClientName() %></td>
            <td><%= order.getArticleType() %></td>
            <td><%= order.getQuantity() %></td>
            <td><%= order.getExpectedDeliveryDate() %></td>
            <td><%= order.getStatus() %></td>
            <td><a class="btn btn-secondary" href="<%= request.getContextPath() %>/workflow?orderId=<%= order.getId() %>">Workflow</a></td>
        </tr>
        <% }} %>
        </tbody>
    </table>
    </div>
</div>
</body>
</html>
