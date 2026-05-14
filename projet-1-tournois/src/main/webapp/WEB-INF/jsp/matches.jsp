<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.projetsimple.model.Match" %>
<%@ page import="com.projetsimple.model.Tournament" %>
<%
    Tournament tournament = (Tournament) request.getAttribute("tournament");
    List<Match> matches = (List<Match>) request.getAttribute("matches");
    Map<Integer, String> teamNames = (Map<Integer, String>) request.getAttribute("teamNames");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Matchs</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
</head>
<body class="app-body">
<div class="app-shell stack">
    <div>
        <h1 class="app-title">Matchs - <%= tournament != null ? tournament.getName() : "" %></h1>
        <p class="app-subtitle">Le calendrier est genere automatiquement au premier affichage.</p>
    </div>

    <div class="table-wrap">
    <table>
        <thead>
        <tr><th>Phase</th><th>Date</th><th>Terrain</th><th>Affiche</th><th>Score</th><th>Action</th></tr>
        </thead>
        <tbody>
        <% if (matches != null) {
               for (Match match : matches) { %>
        <tr>
            <td><%= match.getStage() %></td>
            <td><%= match.getScheduledAt() %></td>
            <td><%= match.getFieldNumber() %></td>
            <td>
                <%= teamNames.getOrDefault(match.getHomeTeamId(), "TBD") %>
                vs
                <%= teamNames.getOrDefault(match.getAwayTeamId(), "TBD") %>
            </td>
            <td><%= match.getHomeScore() == null ? "-" : match.getHomeScore() %> - <%= match.getAwayScore() == null ? "-" : match.getAwayScore() %></td>
            <td>
                <form method="post" action="<%= request.getContextPath() %>/matches" class="actions">
                    <input type="hidden" name="tournamentId" value="<%= tournament.getId() %>">
                    <input type="hidden" name="matchId" value="<%= match.getId() %>">
                    <input type="number" min="0" name="homeScore" style="max-width:90px" required>
                    <input type="number" min="0" name="awayScore" style="max-width:90px" required>
                    <button class="btn btn-primary" type="submit">Valider</button>
                </form>
            </td>
        </tr>
        <%     }
           } %>
        </tbody>
    </table>
    </div>

    <div class="actions">
        <a class="btn btn-success" href="<%= request.getContextPath() %>/standings?tournamentId=<%= tournament.getId() %>">Voir classement</a>
        <a class="btn btn-secondary" href="<%= request.getContextPath() %>/teams?tournamentId=<%= tournament.getId() %>">Retour equipes</a>
    </div>
</div>
</body>
</html>
