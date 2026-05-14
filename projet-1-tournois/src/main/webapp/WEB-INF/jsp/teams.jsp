<%@ page import="java.util.List" %>
<%@ page import="com.projetsimple.model.Team" %>
<%@ page import="com.projetsimple.model.Tournament" %>
<%
    Tournament tournament = (Tournament) request.getAttribute("tournament");
    List<Team> teams = (List<Team>) request.getAttribute("teams");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Equipes</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
</head>
<body class="app-body">
<div class="app-shell stack">
    <div>
        <h1 class="app-title">Equipes - <%= tournament != null ? tournament.getName() : "" %></h1>
        <p class="app-subtitle">Gestion des equipes participantes au tournoi.</p>
    </div>

    <form method="post" action="<%= request.getContextPath() %>/teams" class="card stack">
        <input type="hidden" name="tournamentId" value="<%= tournament.getId() %>">
        <div class="grid-3">
            <div class="field"><label>Nom equipe</label><input name="name" placeholder="Nom equipe" required></div>
            <div class="field"><label>URL logo</label><input name="logoUrl" placeholder="URL logo"></div>
            <div class="field"><label>Contact</label><input name="contact" placeholder="Contact"></div>
        </div>
        <div class="actions"><button class="btn btn-primary" type="submit">Ajouter equipe</button></div>
    </form>

    <div class="table-wrap">
    <table>
        <thead><tr><th>#</th><th>Nom</th><th>Logo</th><th>Contact</th></tr></thead>
        <tbody>
        <% if (teams != null) {
               for (Team team : teams) { %>
        <tr>
            <td><%= team.getId() %></td>
            <td><%= team.getName() %></td>
            <td><%= team.getLogoUrl() == null ? "" : team.getLogoUrl() %></td>
            <td><%= team.getContact() == null ? "" : team.getContact() %></td>
        </tr>
        <%     }
           } %>
        </tbody>
    </table>
    </div>

    <div class="actions">
        <a class="btn btn-success" href="<%= request.getContextPath() %>/matches?tournamentId=<%= tournament.getId() %>">Voir / generer matchs</a>
        <a class="btn btn-secondary" href="<%= request.getContextPath() %>/">Retour</a>
    </div>
</div>
</body>
</html>
