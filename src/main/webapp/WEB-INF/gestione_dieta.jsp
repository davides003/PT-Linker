<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestione Tabella Excel</title>
</head>
<body>
<script>
    // Funzione per fare una richiesta alla servlet per ottenere il contenuto del file Excel
    function caricaContenutoTabella() {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "dieta", true); // La servlet che controlla se il file esiste
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                document.body.innerHTML = xhr.responseText; // Inserisce il contenuto HTML nella pagina
            }
        };
        xhr.send();
    }

    // Funzione per salvare il contenuto della tabella modificata
    function salvaContenutoTabella() {
        var form = document.getElementById("formTabella"); // Ottiene il form
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "dieta", true); // Chiama la stessa servlet con il metodo POST
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        // Serializza i dati del form per inviarli come parametri POST
        var formData = new FormData(form);
        var params = new URLSearchParams(formData).toString();

        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                alert("File salvato con successo!"); // Mostra un messaggio di conferma
                caricaContenutoTabella(); // Ricarica la tabella aggiornata
            }
        };
        xhr.send(params); // Invia i dati alla servlet
    }

    // Funzioni per aggiungere righe e colonne
    function aggiungiRiga() {
        const tabella = document.getElementById("tabella"); // Ottiene la tabella
        const riga1 = tabella.rows[0]; // Ottiene la prima riga per calcolare il numero di colonne
        const numColonne = riga1.cells.length; // Numero di colonne
        const indRiga = tabella.rows.length;

        const nuovaRiga = tabella.insertRow(); // Aggiunge una nuova riga alla fine della tabella
        for (let i = 0; i < numColonne; i++) {
            const nuovaCella = nuovaRiga.insertCell(); // Crea una nuova cella
            const input = document.createElement("input");
            input.type = "text";
            input.name = "cella_" + indRiga + "_" + i;
            input.id = "cella_" + indRiga + "_" + i;
            input.value = "cella_" + indRiga + "_" + i;
            nuovaCella.appendChild(input);
        }
    }

    function aggiungiColonna() {
        const tabella = document.getElementById("tabella"); // Ottiene la tabella
        for (let i = 0; i < tabella.rows.length; i++) {
            const nuovaCella = tabella.rows[i]; // Aggiunge una cella alla riga corrente
            const numColonna = nuovaCella.cells.length;
            const nCella = nuovaCella.insertCell();
            const input = document.createElement("input");
            input.type = "text";
            input.name = "cella_" + i + "_" + numColonna;
            input.id = "cella_" + i + "_" + numColonna;
            input.value = "cella_" + i + "_" + numColonna;
            nCella.appendChild(input);
        }
    }

    // Quando la pagina è pronta, carica il contenuto della tabella
    window.onload = caricaContenutoTabella;
</script>
</body>
</html>