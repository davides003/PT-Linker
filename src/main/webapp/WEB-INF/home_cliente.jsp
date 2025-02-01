<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PT-Linker</title>
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

        .logout-container form {
            margin: 0;
        }

        .logout-container .logout-button {
            background-color: #ff3333; /* Colore del bottone */
            color: white;
            border: none;
            border-radius: 20px; /* Bordi arrotondati */
            padding: 10px 20px; /* Spaziatura interna */
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer; /* Cambia il cursore */
            transition: background-color 0.3s ease, transform 0.2s ease; /* Animazione su hover */
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2); /* Ombra */
        }

        .logout-container .logout-button:hover {
            background-color: #e62e2e; /* Colore al passaggio del mouse */
            transform: scale(1.05); /* Leggero ingrandimento */
        }

        .logout-container .logout-button:active {
            background-color: #cc2929; /* Colore quando cliccato */
            transform: scale(0.98); /* Effetto pressione */
        }

        .container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            padding: 20px;
            justify-content: center;
        }

        .card {
            flex: 1 1 calc(33.33% - 40px); /* Dimensione responsiva */
            max-width: 400px;
            height: 400px;
            border: 2px dashed #ccc;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            background-color: white;
            text-align: center;
            font-weight: bold;
            color: #333;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .card img {
            max-width: 100%;
            max-height: 80%;
        }

        @media (max-width: 1024px) {
            .card {
                flex: 1 1 calc(50% - 40px); /* 2 card per riga */
            }
        }

        @media (max-width: 768px) {
            .card {
                flex: 1 1 100%; /* 1 card per riga */
            }
        }

        @media (max-width: 480px) {
            .header {
                flex-direction: column;
                text-align: center;
            }

            .header img {
                margin-bottom: 10px;
            }

            .header h1 {
                font-size: 1.2rem;
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


<div class="container">
    <div class="card">
        <div>400 x 400</div>
        <div>MY Personal Trainer</div>
    </div>
    <div class="card">
        <div>400 x 400</div>
        <div>MY Nutritionist</div>
    </div>
    <a href="settings.jsp">
        <div class="card" >
            <div>400 x 400</div>
            <div>Settings</div>
        </div>
    </a>
    <div class="card">
        <div>400 x 400</div>
        <div>Workout</div>
    </div>
    <a href="dieta">
        <%
            session.setAttribute("pagina","home_cliente");
        %>
        <div class="card">
            <div>400 x 400</div>
            <div>Diet</div>
        </div>
    </a>
    <a href="progressiController">
    <div class="card">
        <div>400 x 400</div>
        <div>My Results</div>
    </div>
    </a>
</div>
</body>
</html>
