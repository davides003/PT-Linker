<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PT-Linker Nutritionist</title>
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

        .header h1 {
            font-size: 1.5rem;
        }

        .logout-container form {
            margin: 0;
        }

        .logout-container .logout-button {
            background-color: #ff3333;
            color: white;
            border: none;
            border-radius: 20px;
            padding: 10px 20px;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
        }

        .logout-container .logout-button:hover {
            background-color: #e62e2e;
            transform: scale(1.05);
        }

        .logout-container .logout-button:active {
            background-color: #cc2929;
            transform: scale(0.98);
        }

        .search-bar {
            margin: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .search-bar input[type="text"] {
            width: 50%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .search-bar button {
            margin-left: 10px;
            padding: 10px 20px;
            font-size: 16px;
            background-color: black;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .content {
            display: flex;
            flex-wrap: wrap;
            padding: 20px;
            gap: 20px;
        }

        .left-panel, .right-panel {
            flex: 1 1 150px;
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 20px;
        }

        .left-panel img, .right-panel img {
            width: 100%;
            max-width: 150px;
            height: auto;
            border: 1px solid #ccc;
        }

        .left-panel .notification {
            position: relative;
            display: inline-block;
        }

        .left-panel .notification .badge {
            position: absolute;
            top: -5px;
            right: -10px;
            background-color: red;
            color: white;
            border-radius: 50%;
            padding: 5px 10px;
            font-size: 14px;
        }

        .table-container {
            flex-grow: 1;
            overflow-x: auto;
            min-width: 300px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            text-align: left;
            margin-top: 20px;
        }

        table th, table td {
            border: 1px solid #ccc;
            padding: 10px;
        }

        table th {
            background-color: #f2f2f2;
        }

        .filter {
            margin-left: 20px;
            display: flex;
            align-items: center;
        }

        @media (max-width: 1024px) {
            .search-bar input[type="text"] {
                width: 70%;
            }
        }

        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                text-align: center;
            }

            .header img {
                margin-bottom: 10px;
            }

            .content {
                flex-direction: column;
                align-items: center;
            }

            .left-panel, .right-panel {
                flex: 1 1 100%;
                max-width: 300px;
            }
        }

        @media (max-width: 480px) {
            .search-bar input[type="text"] {
                width: 90%;
            }

            .left-panel img, .right-panel img {
                max-width: 100px;
            }

            table th, table td {
                font-size: 14px;
                padding: 8px;
            }
        }
    </style>
</head>
<body>
<div class="header">
    <h1>PT-Linker</h1>
    <div class="logout-container">
        <form action="Logout" method="post">
            <button type="submit" class="logout-button">Logout</button>
        </form>
    </div>
</div>

<div class="search-bar">
    <input type="text" placeholder="Search...">
    <button>Search</button>
</div>

<div class="content">
    <div class="left-panel">
        <div class="notification">
            <img src="chat-placeholder.png" alt="Chat">
        </div>
        <img src="diet-set-placeholder.png" alt="progress management">
    </div>

    <div class="table-container">
        <div class="filter">
            <input type="checkbox" id="filter">
            <label for="filter">Filter</label>
        </div>

        <table id="user-table">
            <tbody>
            <!-- Il contenuto sarà aggiornato dinamicamente -->
            </tbody>
        </table>
    </div>

    <div class="right-panel">
        <img src="balance-placeholder.png" alt="Balance">
        <img src="certifications-placeholder.png" alt="Certifications">
        <a href="ProfiloSettingServlet"><img src="settings-placeholder.png" alt="Settings"></a>
    </div>
</div>

<!-- JavaScript -->
<script>
    async function fetchTableData() {
        try {
            // Invia una richiesta GET alla servlet
            const response = await fetch('ProfessionistaController');
            if (!response.ok) {
                throw new Error('Errore durante la richiesta alla servlet');
            }

            // Recupera il contenuto della tabella dalla servlet
            const data = await response.text();

            // Aggiorna il contenuto del tbody
            document.querySelector('#user-table tbody').innerHTML = data;
        } catch (error) {
            console.error('Errore:', error);
        }
    }

    // Chiama la funzione per aggiornare la tabella quando la pagina è caricata
    window.onload = fetchTableData;
</script>
</body>
</html>