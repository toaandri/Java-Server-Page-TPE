<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nouvelle commande</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
</head>
<body class="app-body">
<div class="app-shell stack">
    <div>
        <h1 class="app-title">Nouvelle commande client</h1>
        <p class="app-subtitle">Enregistrement d'une nouvelle demande de production textile.</p>
    </div>
    <form class="card stack" method="post" action="<%= request.getContextPath() %>/orders">
        <div class="grid-3">
            <div class="field">
                <label>Nom client</label>
                <input name="clientName" required>
            </div>
            <div class="field">
                <label>Type article/modele</label>
                <input name="articleType" required>
            </div>
            <div class="field">
                <label>Quantite</label>
                <input type="number" min="1" name="quantity" required>
            </div>
            <div class="field">
                <label>Taille/Couleur</label>
                <input name="sizeColor">
            </div>
            <div class="field">
                <label>Date livraison prevue</label>
                <input type="date" name="expectedDeliveryDate" required>
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
