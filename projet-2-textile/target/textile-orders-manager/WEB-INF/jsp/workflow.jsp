<%@ page import="java.util.List" %>
<%@ page import="com.projetsimple.textile.model.TextileOrder" %>
<%@ page import="com.projetsimple.textile.model.ProductionStep" %>
<%
    TextileOrder order = (TextileOrder) request.getAttribute("order");
    List<ProductionStep> steps = (List<ProductionStep>) request.getAttribute("steps");
    String delayInfo = (String) request.getAttribute("delayInfo");
    String errorMessage = (String) session.getAttribute("errorMessage");
    if (errorMessage != null) {
        session.removeAttribute("errorMessage");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Workflow commande</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
</head>
<body class="app-body">
<div class="app-shell stack">
    <div>
        <h1 class="app-title">Workflow commande #<%= order.getId() %> - <%= order.getClientName() %></h1>
        <p class="app-subtitle">Statut global: <strong><%= order.getStatus() %></strong></p>
        <p class="app-subtitle"><%= delayInfo %></p>
    </div>
    <% if (errorMessage != null) { %>
    <div class="alert alert-danger"><%= errorMessage %></div>
    <% } %>

    <div class="table-wrap">
    <table>
        <thead>
        <tr><th>Etape</th><th>Responsable</th><th>Statut</th><th>Debut</th><th>Fin</th><th>Action</th></tr>
        </thead>
        <tbody>
        <% for (ProductionStep step : steps) { %>
        <tr>
            <td><%= step.getStepName() %></td>
            <td><%= step.getResponsible() == null ? "-" : step.getResponsible() %></td>
            <td><%= step.getStatus() %></td>
            <td><%= step.getStartedAt() == null ? "-" : step.getStartedAt() %></td>
            <td><%= step.getEndedAt() == null ? "-" : step.getEndedAt() %></td>
            <td>
                <% if ("PENDING".equals(step.getStatus())) { %>
                <form method="post" action="<%= request.getContextPath() %>/workflow" class="actions">
                    <input type="hidden" name="orderId" value="<%= order.getId() %>">
                    <input type="hidden" name="stepId" value="<%= step.getId() %>">
                    <input type="hidden" name="action" value="START">
                    <input name="responsible" placeholder="Responsable" required>
                    <button class="btn btn-primary" type="submit">Demarrer</button>
                </form>
                <% } else if ("IN_PROGRESS".equals(step.getStatus())) { %>
                <form method="post" action="<%= request.getContextPath() %>/workflow" class="actions">
                    <input type="hidden" name="orderId" value="<%= order.getId() %>">
                    <input type="hidden" name="stepId" value="<%= step.getId() %>">
                    <input type="hidden" name="action" value="COMPLETE">
                    <input name="notes" placeholder="Remarques">
                    <button class="btn btn-success" type="submit">Valider</button>
                </form>
                <% } else { %>
                <span class="badge">Terminee</span>
                <% } %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    </div>

    <a class="btn btn-secondary" href="<%= request.getContextPath() %>/">Retour</a>
</div>
</body>
</html>
