<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nouveau tournoi</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
</head>
<body class="app-body">
<div class="app-shell stack">
    <div class="app-header">
        <div>
            <h1 class="app-title">Creation d'un tournoi</h1>
            <p class="app-subtitle">Saisir les informations de reference du tournoi.</p>
        </div>
    </div>
    <form method="post" action="<%= request.getContextPath() %>/tournaments" class="card stack">
        <div class="grid-2">
            <div class="field">
                <label>Nom</label>
                <input name="name" required>
            </div>
            <div class="field">
                <label>Sport</label>
                <input name="sport" required>
            </div>
            <div class="field">
                <label>Type</label>
                <select name="type" required>
                    <option value="CHAMPIONNAT">Championnat</option>
                    <option value="KO">Elimination directe</option>
                </select>
            </div>
            <div class="field">
                <label>Lieu</label>
                <input name="location" required>
            </div>
            <div class="field">
                <label>Date debut</label>
                <input type="date" name="startDate" required>
            </div>
            <div class="field">
                <label>Date fin</label>
                <input type="date" name="endDate" required>
            </div>
            <div class="field">
                <label>Duree match (minutes)</label>
                <input type="number" min="10" name="matchDurationMinutes" required>
            </div>
            <div class="field">
                <label>Nombre de terrains</label>
                <input type="number" min="1" name="availableFields" required>
            </div>
        </div>
        <div class="actions">
            <button class="btn btn-primary" type="submit">Enregistrer</button>
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/">Retour</a>
        </div>
    </form>
</div>
</body>
</html>
