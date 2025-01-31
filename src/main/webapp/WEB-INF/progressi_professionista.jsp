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
        <span id="peso">--kg</span>
    </div>
    <div class="data-box">
        <label>Larghezza girovita:</label>
        <span id="girovita">--cm</span>
    </div>
    <div class="data-box">
        <label>Circonferenza braccio dx:</label>
        <span id="braccioDx">--cm</span>
    </div>
    <div class="data-box">
        <label>Circonferenza braccio sx:</label>
        <span id="braccioSx">--cm</span>
    </div>
    <div class="data-box">
        <label>Circonferenza torace:</label>
        <span id="torace">--cm</span>
    </div>
    <div class="data-box">
        <label>Circonferenza gamba dx:</label>
        <span id="gambaDx">--cm</span>
    </div>
    <div class="data-box">
        <label>Circonferenza gamba sx:</label>
        <span id="gambaSx">--cm</span>
    </div>
    <div class="data-box">
        <label>Descrizione delle problematiche:</label>
        <p id="descrizione">
        </p>
    </div>
    <div class="photo" id="foto">
        <label>Foto:</label>

    </div>
</div>
<script>
    window.onload = function() {
        fetch('progressiController')
            .then(response => response.json()) // Parsea la risposta JSON
            .then(data => {
                // Ora "data" è l'oggetto Progresso deserializzato
                // Puoi accedere ai campi dell'oggetto come "data.peso", "data.descrizione", ecc.

                // Esempio di come visualizzare alcuni campi nell'interfaccia
                document.getElementById("peso").innerText = data.peso + " kg";
                document.getElementById("girovita").innerText = data.larghezzaGirovita + " cm";
                document.getElementById("braccioDx").innerText = data.circonferenzaBracciaDx + " cm";
                document.getElementById("braccioSx").innerText = data.circonferenzaBracciaSx + " cm";
                document.getElementById("torace").innerText = data.circonferenzaTorace + " cm";
                document.getElementById("gambaDx").innerText = data.circonferenzaGambaDx + " cm";
                document.getElementById("gambaSx").innerText = data.circonferenzaGambaSx + " cm";
                document.getElementById("descrizione").innerText = data.descrizione;

                // Se ci sono immagini, ad esempio
                if (data.percorsiFoto && data.percorsiFoto.length > 0) {
                    let percorsoImmagine = '${pageContext.request.contextPath}/' + data.percorsiFoto[0];

                    // Rimuove eventuali doppie barre (//) nel percorso generato
                    percorsoImmagine = percorsoImmagine.replace(/\/+/g, '/');

                    console.log("Percorso immagine:", percorsoImmagine); // Debug

                    // Creazione dell'elemento <img>
                    let img = document.createElement("img");
                    img.src = percorsoImmagine;
                    img.alt = "Foto progresso";
                    img.style.width = "700px"; // Opzionale: imposta dimensioni


                    // Trova il div con id="fotoContainer" e aggiunge l'immagine
                    let divFoto = document.getElementById("foto");
                    if (divFoto) {
                        divFoto.appendChild(img);
                    } else {
                        console.error("Div con id='foto' non trovato");
                    }
                }
            })
            .catch(error => {
                console.error("Errore durante la chiamata alla servlet:", error);
            });
    };
</script>
</body>
</html>