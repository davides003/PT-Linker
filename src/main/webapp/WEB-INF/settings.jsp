<%@ page import="data.entity.Utente" %>
<%@ page import="data.entity.Cliente" %>
<%@ page import="data.entity.Professionista" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: redav
  Date: 20/01/2025
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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

        .container {
            padding-top: 20px;
            display: flex;
            flex-wrap: wrap;
            gap: 25px;
            padding-left: 10%;
            justify-content: space-between;
            padding-right: 10%;
        }

        .card {
            flex: 1 1 calc(33.33% - 40px); /* Responsive size */
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

        .socet{
            display: grid;

        }

        .socet label{
            text-align: center;
            font-size: 24px; /* Imposta la dimensione del testo */
            font-weight: bold; /* Rende il testo in grassetto (opzionale) */
        }
        .socet input{
            text-align: center;
            border: none;
            padding: 10px;
            font-size: 15px;
            color: #333; /* Imposta il colore del testo (opzionale) */
        }

        .card img {
            max-width: 100%;
            max-height: 80%;
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

        @media (max-width: 1024px) {
            .card {
                flex: 1 1 calc(50% - 40px); /* 2 cards per row */
            }
        }

        @media (max-width: 768px) {
            .card {
                flex: 1 1 100%; /* 1 card per row */
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
    <script>
        function abilitaModifica() {
            // Seleziona tutti gli input nella pagina
            const inputs = document.querySelectorAll('#Altezza, #Peso, #larghezzaGirovita, #circonferenzaBracciaDx, #circonferenzaBracciaSx, #circonferenzaTorace, #circonferenzaGambaDx, #circonferenzaGambaSx');
            // Abilita tutti gli input rendendoli modificabili
            inputs.forEach(input => {
                input.readOnly = false; // Rimuove il readonly
                input.style.border = "1px solid #ff6600"; // Cambia lo stile per indicare la modifica
            });

            // Rende invisibile il pulsante Modifica
            document.getElementById("btnMod").style.display = "none";

            // Rende visibile il pulsante Disabilita Modifica
            document.getElementById("btnDisMod").style.display = "inline-block";

            // Rende visibile il pulsante Salva
            document.getElementById("btnSalva").style.display = "inline-block";

            // Rende visibile l'inserimento dei file
            document.getElementById("insert-file").style.display = "inline-block";
        }

        function disabilitaModifica() {
            // Seleziona tutti gli input nella pagina
            const inputs = document.querySelectorAll("input");

            // Disabilita la modifica e ripristina lo stile
            inputs.forEach(input => {
                input.readOnly = true; // Aggiunge il readonly
                input.style.border = "0px solid #ccc"; // Ripristina lo stile
            });

            // Rende invisibile il pulsante Modifica
            document.getElementById("btnMod").style.display = "inline-block";

            // Rende visibile il pulsante Disabilita Modifica
            document.getElementById("btnDisMod").style.display = "none";

            // Nasconde il pulsante Salva
            document.getElementById("btnSalva").style.display = "none";

            // Rende invisibile l'inserimento dei file
            document.getElementById("insert-file").style.display = "none";
        }
    </script>

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

<form action="ProfiloSettingServlet" method="post">
<div class="container">
    <%
        Utente ut = (Utente) request.getSession().getAttribute("utente");
    %>
    <div class="socet">
        <label typeof="Nome">Nome</label>
        <input type="text" name="Nome" value="<%= ut.getNome() %>" readonly>
    </div>
    <div class="socet">
        <label typeof="Cognome">Cognome</label>
        <input type="text" name="Cognome" value="<%= ut.getCognome() %>" readonly>
    </div>
    <div class="socet">
        <label typeof="Username">Username</label>
        <input type="text" name="Username" value="<%= ut.getUsername() %>" readonly>
    </div>
    <div class="socet">
        <label typeof="E-Mail">E-Mail</label>
        <input type="text" name="E-Mail" value="<%= ut.getEmail() %>" readonly>
    </div>
    <div class="socet">
        <label typeof="nascita">Data di Nascita</label>
        <input type="text" name="nascita" value="<%= ut.getDataDiNascita() %>" readonly>
    </div>
<%
    String className = ut.getClass().getSimpleName();
    if(className.equals("Cliente")){
        Cliente cliente = (Cliente) ut;
    %>
    <div class="socet">
        <label for="Altezza" class="socet">Altezza</label>
        <input type="text" id="Altezza" name="Altezza" value="<%= cliente.getAltezza() %>" readonly>
    </div>
    <div class="socet">
        <label for="Peso">Peso</label>
        <input type="text" id="Peso" name="Peso" value="<%= cliente.getPeso() %>" readonly>
    </div>
    <div class="socet">
        <label for="larghezzaGirovita">Larghezza Girovita</label>
        <input type="text" id="larghezzaGirovita" name="larghezzaGirovita" value="<%= cliente.getLarghezzaGirovita() %>" readonly>
    </div>
    <div class="socet">
        <label for="circonferenzaBracciaDx">Circonferenza Braccia Destro</label>
        <input type="text" id="circonferenzaBracciaDx" name="circonferenzaBracciaDx" value="<%= cliente.getCirconferenzaBracciaDx() %>" readonly>
    </div>
    <div class="socet">
        <label for="circonferenzaBracciaSx">Circonferenza Braccia Sinistro</label>
        <input type="text" id="circonferenzaBracciaSx" name="circonferenzaBracciaSx" value="<%= cliente.getCirconferenzaBracciaSx() %>" readonly>
    </div>
    <div class="socet">
        <label for="circonferenzaTorace">Circonferenza Torace</label>
        <input type="text" id="circonferenzaTorace" name="circonferenzaTorace" value="<%= cliente.getCirconferenzaTorace() %>" readonly>
    </div>
    <div class="socet">
        <label for="circonferenzaGambaDx">Circonferenza Gamba Destra</label>
        <input type="text" id="circonferenzaGambaDx" name="circonferenzaGambaDx" value="<%= cliente.getCirconferenzaGambaDx() %>" readonly>
    </div>
    <div class="socet">
        <label for="circonferenzaGambaSx">Circonferenza Gamba Sinistra</label>
        <input type="text" id="circonferenzaGambaSx" name="circonferenzaGambaSx" value="<%= cliente.getCirconferenzaGambaSx() %>" readonly>
    </div>

<%
    }else if(className.equals("Professionista")){
        Professionista professionista = (Professionista) ut;
%>
    <div class="socet">
        <label typeof="Tipo">Tipo</label>
        <input type="text" id="Tipo" name="Tipo" value="<%= professionista.getTipo() %>" readonly>
    </div>
        <%
            ArrayList<String> list = professionista.getAttestati();
            for(String percorso : list){
        %>
        <div class="socet">
            <!--<label><%=percorso%></label> -->
            <%
                percorso = percorso.substring(percorso.indexOf("documenti"));
            %>
            <!--<a href="window.open('<%=percorso%>')" target="_blank" download>Visualizza PDF</a>-->
            <!--<a href="#" onclick="window.open('<%=percorso%>', '_blank'); return false;">Visualizza PDF</a>-->
            <iframe src="https://docs.google.com/viewer?embedded=true&url=http://localhost:8080/PT_LINKER_war/documenti/certificati/certificato_3_1.pdf" width="600" height="400">
                Il tuo browser non supporta gli iframe.
            </iframe>
        </div>
        <%
            }
        %>
    <div class="form-group" id="insert-file" style="display: none;">
        <label for="certificati">Certificati (carica PDF):</label>
        <input type="file" id="certificati" name="certificati" accept=".pdf" multiple required>
    </div>
    <%
        }
    %>
</div>
<div style="text-align: center">
    <!-- Bottone per abilitare e salvare -->
    <div>
        <button type="button" id="btnDisMod" onclick="disabilitaModifica()" class="btn" style="display: none;">Disabilita Modifica</button>
        <button type="button" id="btnMod" onclick="abilitaModifica()" class="btn">Modifica</button>
        <button type="submit" id="btnSalva" class="btn" style="display: none;">Salva</button>
    </div>
</div>

</form>

</body>
</html>
