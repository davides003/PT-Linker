<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrazione - PTLinker</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #ffffff;
        }

        .header {
            background-color: #ff6600;
            color: white;
            padding: 10px;
            text-align: left;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .header img {
            width: 30px;
            height: 30px;
            margin-right: 10px;
        }

        .header h1 {
            font-size: 20px;
        }

        .container {
            text-align: center;
            padding: 20px;
            width: 90%;
            margin: 0 auto;
        }

        .container h2 {
            font-family: 'Courier New', Courier, monospace;
            font-size: 36px;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 20px;
            text-align: left;
            max-width: 400px;
            margin: 10px auto;
        }

        .form-group label {
            display: block;
            font-size: 18px;
            margin-bottom: 5px;
        }

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #000;
            border-radius: 5px;
        }

        .btn {
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            color: white;
            background-color: #000;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            margin: 10px 5px;
        }

        .btn:hover {
            opacity: 0.9;
        }

        /* Media Queries for responsiveness */
        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                align-items: center;
            }

            .header h1 {
                font-size: 18px;
                text-align: center;
            }

            .container h2 {
                font-size: 28px;
            }

            .form-group input,
            .form-group select {
                font-size: 14px;
                padding: 8px;
            }

            .btn {
                font-size: 14px;
                padding: 8px 15px;
            }
        }

        @media (max-width: 480px) {
            .header h1 {
                font-size: 16px;
            }

            .container h2 {
                font-size: 24px;
            }

            .form-group label {
                font-size: 16px;
            }

            .form-group input,
            .form-group select {
                font-size: 14px;
                padding: 6px;
            }

            .btn {
                font-size: 14px;
                padding: 8px 12px;
            }
        }
    </style>
</head>
<body>
<div class="header">
    <h1>PT-Linker</h1>
</div>

<div class="container">
    <h2>REGISTRAZIONE</h2>
    <form action="RegistrazioneServlet" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="email">E-Mail:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required minlength="6">
        </div>
        <div class="form-group">
            <label for="confirm-password">Conferma Password:</label>
            <input type="password" id="confirm-password" name="confirm-password" required minlength="6">
        </div>
        <div class="form-group">
            <label for="name">Nome:</label>
            <input type="text" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="surname">Cognome:</label>
            <input type="text" id="surname" name="surname" required>
        </div>
        <div class="form-group">
            <label for="birthdate">Data di nascita:</label>
            <input type="date" id="birthdate" name="birthdate" required>
        </div>
        <div class="form-group">
            <label for="role">Sono un:</label>
            <select id="role" name="role" required>
                <option value="" disabled selected>Scegli</option>
                <option value="cliente">Cliente</option>
                <option value="personal_trainer">Personal Trainer</option>
                <option value="nutrizionista">Nutrizionista</option>
            </select>
        </div>
        <div id="additionalFields"></div>
        <button type="submit" class="btn">Registrati</button>
    </form>
</div>

<script>
    const roleSelect = document.getElementById("role");
    const additionalFieldsContainer = document.getElementById("additionalFields");

    roleSelect.addEventListener("change", function () {
        additionalFieldsContainer.innerHTML = ""; // Clear previous fields

        if (this.value === "cliente") {
            additionalFieldsContainer.innerHTML = `
                <div class="form-group">
                    <label for="altezza">Altezza:</label>
                    <input type="text" id="altezza" name="altezza" required>
                </div>
                <div class="form-group">
                    <label for="peso">Peso:</label>
                    <input type="text" id="peso" name="peso" required>
                </div>
                <div class="form-group">
                    <label for="girovita">Girovita:</label>
                    <input type="text" id="girovita" name="girovita" required>
                </div>
                <div class="form-group">
                    <label for="circonferenza-braccio-destro">Circonferenza Braccio Destro:</label>
                    <input type="text" id="circonferenza-braccio-destro" name="circonferenza-braccio-destro" required>
                </div>
                <div class="form-group">
                    <label for="circonferenza-braccio-sinistro">Circonferenza Braccio Sinistro:</label>
                    <input type="text" id="circonferenza-braccio-sinistro" name="circonferenza-braccio-sinistro" required>
                </div>
                <div class="form-group">
                    <label for="circonferenza-gamba-destra">Circonferenza Gamba Destra:</label>
                    <input type="text" id="circonferenza-gamba-destra" name="circonferenza-gamba-destra" required>
                </div>
                <div class="form-group">
                    <label for="circonferenza-gamba-sinistra">Circonferenza Gamba Sinistra:</label>
                    <input type="text" id="circonferenza-gamba-sinistra" name="circonferenza-gamba-sinistra" required>
                </div>
                <div class="form-group">
                    <label for="circonferenza-torace">Circonferenza Torace:</label>
                    <input type="text" id="circonferenza-torace" name="circonferenza-torace" required>
                </div>
            `;
        } else if (this.value === "personal_trainer" || this.value === "nutrizionista") {
            additionalFieldsContainer.innerHTML = `
                <div class="form-group">
                    <label for="certificati">Certificati (carica PDF):</label>
                    <input type="file" id="certificati" name="certificati" accept=".pdf" multiple required>
                </div>
            `;
        }
    });
</script>
</body>
</html>