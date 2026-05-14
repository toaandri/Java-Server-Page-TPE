<%@ page import="java.util.List" %>
<%@ page import="com.projetsimple.model.Tournament" %>
<%
    List<Tournament> tournaments = (List<Tournament>) request.getAttribute("tournaments");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion de tournois</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
</head>
<body class="app-body">
<div class="app-shell stack">
    <div class="app-header">
        <div>
            <h1 class="app-title">Tableau de bord des tournois</h1>
            <p class="app-subtitle">Pilotage central des competitions, equipes, matchs et classements.</p>
        </div>
        <a class="btn btn-primary" href="<%= request.getContextPath() %>/tournaments">Nouveau tournoi</a>
    </div>

    <div class="table-wrap">
    <table>
        <thead>
        <tr>
            <th>Nom</th>
            <th>Sport</th>
            <th>Type</th>
            <th>Lieu</th>
            <th>Dates</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% if (tournaments != null) {
               for (Tournament t : tournaments) { %>
        <tr>
            <td><%= t.getName() %></td>
            <td><%= t.getSport() %></td>
            <td><%= t.getType() %></td>
            <td><%= t.getLocation() %></td>
            <td><%= t.getStartDate() %> - <%= t.getEndDate() %></td>
            <td>
                <div class="actions">
                    <a class="btn btn-secondary" href="<%= request.getContextPath() %>/teams?tournamentId=<%= t.getId() %>">Equipes</a>
                    <a class="btn btn-primary" href="<%= request.getContextPath() %>/matches?tournamentId=<%= t.getId() %>">Matchs</a>
                    <a class="btn btn-success" href="<%= request.getContextPath() %>/standings?tournamentId=<%= t.getId() %>">Classement</a>
                </div>
            </td>
        </tr>
        <%     }
           } %>
        </tbody>
    </table>
    </div>
</div>
</body>
</html>
