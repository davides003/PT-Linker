<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PT-Linker - Visualizza Progressi</title>
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
        .data-box {
            margin-bottom: 15px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f1f1f1;
        }
        .data-box label {
            font-weight: bold;
            display: block;
        }
        .photo {
            text-align: center;
            margin-top: 15px;
        }
        .photo img {
            max-width: 100%;
            height: auto;
            border-radius: 10px;
        }
    </style>
</head>
<body>
<div class="header">
    <img src="logo-placeholder.png" alt="Logo">
    <h1>PT-Linker - Visualizza Progressi</h1>
</div>

<div class="container">
    <h2>Dettagli Progressi</h2>
    <div class="data-box">
        <label>Peso:</label>
        <span id="peso">-- kg</span>
    </div>
    <div class="data-box">
        <label>Larghezza girovita:</label>
        <span id="girovita">-- cm</span>
    </div>
    <div class="data-box">
        <label>Circonferenza braccio dx:</label>
        <span id="braccioDx">-- cm</span>
    </div>
    <div class="data-box">
        <label>Circonferenza braccio sx:</label>
        <span id="braccioSx">-- cm</span>
    </div>
    <div class="data-box">
        <label>Circonferenza torace:</label>
        <span id="torace">-- cm</span>
    </div>
    <div class="data-box">
        <label>Circonferenza gamba dx:</label>
        <span id="gambaDx">-- cm</span>
    </div>
    <div class="data-box">
        <label>Circonferenza gamba sx:</label>
        <span id="gambaSx">-- cm</span>
    </div>
    <div class="data-box">
        <label>Descrizione delle problematiche:</label>
        <p id="descrizione">--</p>
    </div>
    <div class="photo">
        <label>Foto:</label>
        <img id="foto" src="placeholder-image.png" alt="Foto del progresso">
    </div>
</div>
</body>
</html>