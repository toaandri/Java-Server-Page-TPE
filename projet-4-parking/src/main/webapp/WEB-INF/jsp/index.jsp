<%@ page import="java.util.List" %>
<%@ page import="com.projetsimple.parking.model.ParkingSpot" %>
<%@ page import="com.projetsimple.parking.model.Reservation" %>
<%@ page import="com.projetsimple.parking.model.ParkingEntry" %>
<%
List<ParkingSpot> spots = (List<ParkingSpot>) request.getAttribute("spots");
List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
List<ParkingEntry> entries = (List<ParkingEntry>) request.getAttribute("entries");
Integer available = (Integer) request.getAttribute("available");
String flash = (String) session.getAttribute("flash");
if (flash != null) session.removeAttribute("flash");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion parking</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
</head>
<body class="app-body">
<div class="app-shell stack">
    <div>
        <h1 class="app-title">Gestion parking</h1>
        <p class="app-subtitle">Places disponibles: <strong><%= available %></strong></p>
    </div>
    <% if (flash != null) { %><div class="card"><%= flash %></div><% } %>

    <div class="grid-4">
        <div class="card">
            <form class="stack" method="post" action="<%= request.getContextPath() %>/parking">
                <h2 class="app-subtitle">Ajouter place</h2>
                <input type="hidden" name="action" value="ADD_SPOT">
                <input name="code" placeholder="Code place" required>
                <label><input type="checkbox" name="vipReserved"> Reserve VIP</label>
                <button class="btn btn-primary" type="submit">Ajouter</button>
            </form>
        </div>
        <div class="card">
            <form class="stack" method="post" action="<%= request.getContextPath() %>/parking">
                <h2 class="app-subtitle">Reservation</h2>
                <input type="hidden" name="action" value="RESERVE">
                <input name="customerName" placeholder="Client" required>
                <select name="userType"><option>STANDARD</option><option>ABONNE</option><option>VIP</option></select>
                <input name="plateNumber" placeholder="Plaque" required>
                <input type="number" min="1" name="durationHours" placeholder="Duree h" required>
                <button class="btn btn-success" type="submit">Reserver</button>
            </form>
        </div>
        <div class="card">
            <form class="stack" method="post" action="<%= request.getContextPath() %>/parking">
                <h2 class="app-subtitle">Entree vehicule</h2>
                <input type="hidden" name="action" value="ENTRY">
                <input name="plateNumber" placeholder="Plaque" required>
                <select name="userType"><option>STANDARD</option><option>ABONNE</option><option>VIP</option></select>
                <button class="btn btn-primary" type="submit">Enregistrer entree</button>
            </form>
        </div>
        <div class="card">
            <form class="stack" method="post" action="<%= request.getContextPath() %>/parking">
                <h2 class="app-subtitle">Sortie vehicule</h2>
                <input type="hidden" name="action" value="EXIT">
                <input name="plateNumber" placeholder="Plaque" required>
                <label><input type="checkbox" name="subscribed"> Abonne</label>
                <button class="btn btn-secondary" type="submit">Enregistrer sortie</button>
            </form>
        </div>
    </div>

    <div class="grid-3">
        <div>
            <h2 class="app-subtitle">Places</h2>
            <div class="table-wrap"><table><thead><tr><th>Code</th><th>VIP</th><th>Occupee</th></tr></thead><tbody>
            <% for (ParkingSpot s : spots) { %><tr><td><%= s.getCode() %></td><td><%= s.isVipReserved() %></td><td><%= s.isOccupied() %></td></tr><% } %>
            </tbody></table></div>
        </div>
        <div>
            <h2 class="app-subtitle">Reservations</h2>
            <div class="table-wrap"><table><thead><tr><th>Client</th><th>Type</th><th>Plaque</th><th>Place</th><th>Statut</th><th>Action</th></tr></thead><tbody>
            <% for (Reservation r : reservations) { %><tr>
                <td><%= r.getCustomerName() %></td>
                <td><%= r.getUserType() %></td>
                <td><%= r.getPlateNumber() %></td>
                <td><%= r.getSpotId() %></td>
                <td><%= r.getStatus() %></td>
                <td>
                    <% if ("CONFIRMEE".equals(r.getStatus())) { %>
                    <div class="actions">
                        <form method="post" action="<%= request.getContextPath() %>/parking">
                            <input type="hidden" name="action" value="RESERVATION_ARRIVAL">
                            <input type="hidden" name="reservationId" value="<%= r.getId() %>">
                            <button class="btn btn-success" type="submit">Voiture arrivee</button>
                        </form>
                        <form method="post" action="<%= request.getContextPath() %>/parking">
                            <input type="hidden" name="action" value="CANCEL_RESERVATION">
                            <input type="hidden" name="reservationId" value="<%= r.getId() %>">
                            <button class="btn btn-secondary" type="submit">Annuler</button>
                        </form>
                    </div>
                    <% } else { %>
                    -
                    <% } %>
                </td>
            </tr><% } %>
            </tbody></table></div>
        </div>
        <div>
            <h2 class="app-subtitle">Entrees/Sorties</h2>
            <div class="table-wrap"><table><thead><tr><th>Plaque</th><th>Place</th><th>Entree</th><th>Sortie</th><th>Montant</th></tr></thead><tbody>
            <% for (ParkingEntry e : entries) { %><tr><td><%= e.getPlateNumber() %></td><td><%= e.getSpotId() %></td><td><%= e.getEntryAt() %></td><td><%= e.getExitAt() %></td><td><%= e.getAmount() %></td></tr><% } %>
            </tbody></table></div>
        </div>
    </div>
</div>
</body>
</html>
