<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"> <!-- Per icone-->
    <title>Gestione Dieta</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }

        .header {
            position: relative;
            background-color: #ff6600;
            color: white;
            padding: 10px;
            text-align: center;
        }

        /* Bottoni generali */
        .header button {
            position: absolute;
            background-color: #ff6600;
            color: white;
            border: none;
            padding: 10px;
            font-size: 16px;
            top:25%;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .header button:hover {
            background-color: #e65c00;
            transform: scale(1.05);
        }

        /* Bottone a sinistra */
        .header .left-btn {
            left: 10px;  /* Posiziona il bottone a sinistra */
        }

        /* Bottone a destra */
        .header .right-btn {
            right: 10px; /* Posiziona il bottone a destra */
        }

        .table-container {
            margin: 20px auto;
            text-align: center;
            overflow-x: auto;
        }

        table {
            width: 90%;
            margin: 0 auto;
            border-collapse: collapse;
            text-align: center;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            background-color: white;
        }

        table th, table td {
            border: 1px solid #ccc;
            padding: 5px; /* Puoi regolare il padding per migliorare l'estetica */
            box-sizing: border-box; /* Assicura che il padding non influenzi la larghezza e altezza */
        }

        table th {
            background-color: #f2f2f2;
        }

        table td:not(:last-child) { /* Seleziona tutte le celle tranne l'ultima colonna */
            width: 100px; /* Imposta la larghezza della cella */
            height: 150px; /* Imposta l'altezza della cella */
        }

        table td input[type="text"] {
            width: 100%;
            height: 100%;
            border: none;
            padding: 5px;
            box-sizing: border-box;
        }

        table td input[type="text"]:focus {
            outline: none;
            border: 1px solid #ff6600;
            background-color: #f1f1f1;
        }

        /* La regola sottostante applica uno stile solo alle celle nell'ultima colonna. */
        table td:last-child {
            width: 50px; /* La larghezza si adatta al contenuto (i bottoni) */
        }

        .buttons-container {
            margin: 20px;
            text-align: center;
        }

        .buttons-container button {
            background-color: #ff6600;
            color: white;
            border: none;
            border-radius: 20px;
            padding: 10px 20px;
            font-size: 16px;
            margin: 5px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
        }

        .buttons-container button:hover {
            background-color: #e65c00;
            transform: scale(1.05);
        }

        .buttons-container button:active {
            background-color: #cc5200;
            transform: scale(0.98);
        }

        .remove-row-btn {
            background-color: #ff3333;
            color: white;
            border: none;
            border-radius: 10px;
            padding: 5px 10px;
            cursor: pointer;
            font-weight: bold;
        }

        .remove-row-btn:hover {
            background-color: #e62e2e;
        }

        @media (max-width: 768px) {
            .buttons-container button {
                padding: 8px 15px;
                font-size: 14px;
            }
        }

        @media (max-width: 480px) {
            table th, table td {
                font-size: 14px;
                padding: 8px;
            }

            .buttons-container button {
                font-size: 12px;
                padding: 6px 10px;
            }
        }
    </style>
</head>
<body>
<div class="header">
    <button class="left-btn" onclick="window.history.back();">Indietro</button>
    <h1>Gestione Dieta</h1>
    <button class="right-btn" onclick="window.location.href='profilo.html';">
        <i class="fas fa-user"></i> Profilo
    </button>
</div>

<!-- Form che invia i dati -->
<form id="formTabella" method="post" action="dieta">
    <div class="table-container" id="table-container">
        <!-- La tabella verrà caricata dinamicamente dalla servlet o creata inizialmente -->
    </div>

    <div class="buttons-container">
        <button type="submit">Salva Modifiche</button>
    </div>
</form>

<script>
    let tabella;

    // Funzione per svuotare una riga (anziché eliminarla) usando l'indice della riga
    function svuotaRiga(rowIndex) {
        const table = document.getElementById("tabella");  // Ottieni la tabella tramite il suo ID
        const row = table.rows[(rowIndex+1)]; // Ottieni la riga in base all'indice passato

        if (row) {
            // Itera su tutte le celle della riga (escludendo l'ultima cella che è il bottone)
            for (let i = 0; i < row.cells.length - 1; i++) {
                const input = row.cells[i].querySelector("input[type='text']");  // Trova l'input all'interno della cella
                if (input) {
                    input.value = "";  // Svuota il contenuto dell'input
                }
            }
        } else {
            console.error("Riga non trovata per indice:", rowIndex);
        }
    }

    // Funzione per fare una richiesta alla servlet per ottenere il contenuto del file Excel
    function caricaContenutoTabella() {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "dieta", true); // La servlet che controlla se il file esiste
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                document.getElementById("table-container").innerHTML = xhr.responseText; // Inserisce il contenuto HTML nella pagina
            }
        };
        xhr.send();
    }

    // Quando la pagina è pronta, crea la tabella di default
    window.onload = caricaContenutoTabella();
</script>
</body>
</html>