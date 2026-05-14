<%@ page import="java.util.List" %>
<%@ page import="com.projetsimple.model.StandingRow" %>
<%@ page import="com.projetsimple.model.Tournament" %>
<%
    Tournament tournament = (Tournament) request.getAttribute("tournament");
    List<StandingRow> standings = (List<StandingRow>) request.getAttribute("standings");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Classement</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
</head>
<body class="app-body">
<div class="app-shell stack">
    <div>
        <h1 class="app-title">Classement - <%= tournament != null ? tournament.getName() : "" %></h1>
        <p class="app-subtitle">Vue de performance des equipes.</p>
    </div>

    <div class="table-wrap">
    <table>
        <thead>
        <tr>
            <th>#</th><th>Equipe</th><th>J</th><th>G</th><th>N</th><th>P</th><th>BP</th><th>BC</th><th>Diff</th><th>Pts</th>
        </tr>
        </thead>
        <tbody>
        <% if (standings != null) {
               int index = 1;
               for (StandingRow row : standings) { %>
        <tr>
            <td><%= index++ %></td>
            <td><%= row.getTeamName() %></td>
            <td><%= row.getPlayed() %></td>
            <td><%= row.getWins() %></td>
            <td><%= row.getDraws() %></td>
            <td><%= row.getLosses() %></td>
            <td><%= row.getGoalsFor() %></td>
            <td><%= row.getGoalsAgainst() %></td>
            <td><%= row.getGoalDifference() %></td>
            <td><strong><%= row.getPoints() %></strong></td>
        </tr>
        <%     }
           } %>
        </tbody>
    </table>
    </div>
    <a class="btn btn-secondary" href="<%= request.getContextPath() %>/matches?tournamentId=<%= tournament.getId() %>">Retour matchs</a>
</div>
</body>
</html>
