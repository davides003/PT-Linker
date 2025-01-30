<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PT-Linker - Progressi</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }
        .header {
            background-color: #ff6600;
            color: white;
            padding: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .header img {
            width: 50px;
            height: 50px;
        }
        .header h1 {
            font-size: 1.5rem;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }
        label {
            font-weight: bold;
        }
        input, textarea {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            background-color: #ff6600;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
        }
        button:hover {
            background-color: #e65c00;
        }
    </style>
</head>
<body>
<div class="header">
    <img src="logo-placeholder.png" alt="Logo">
    <h1>PT-Linker - Progressi</h1>
</div>

<div class="container">
    <h2>Aggiungi i tuoi progressi</h2>
    <form action="upload_progress.php" method="post" enctype="multipart/form-data">
        <label for="peso">Peso (kg):</label>
        <input type="number" id="peso" name="peso" min="0" step="0.1" required>

        <label for="girovita">Larghezza girovita (cm):</label>
        <input type="number" id="girovita" name="girovita" min="0" step="0.1" required>

        <label for="braccioDx">Circonferenza braccio dx (cm):</label>
        <input type="number" id="braccioDx" name="braccioDx" min="0" step="0.1" required>

        <label for="braccioSx">Circonferenza braccio sx (cm):</label>
        <input type="number" id="braccioSx" name="braccioSx" min="0" step="0.1" required>

        <label for="torace">Circonferenza torace (cm):</label>
        <input type="number" id="torace" name="torace" min="0" step="0.1" required>

        <label for="gambaDx">Circonferenza gamba dx (cm):</label>
        <input type="number" id="gambaDx" name="gambaDx" min="0" step="0.1" required>

        <label for="gambaSx">Circonferenza gamba sx (cm):</label>
        <input type="number" id="gambaSx" name="gambaSx" min="0" step="0.1" required>

        <label for="descrizione">Descrizione delle problematiche:</label>
        <textarea id="descrizione" name="descrizione" rows="4" required></textarea>

        <label for="foto">Carica una foto (JPG, PNG, max 5MB):</label>
        <input type="file" id="foto" name="foto" accept="image/png, image/jpeg" required>

        <button type="submit">Salva Progressi</button>
        <input type="hidden" id="idCliente" name="idCliente">
        <script>
            document.getElementById('idCliente').value = getClienteId(); // Funzione per ottenere l'ID
        </script>

    </form>
</div>
</body>
</html>

