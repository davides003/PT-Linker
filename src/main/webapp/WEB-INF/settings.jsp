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
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            padding: 20px;
            justify-content: center;
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

        .card img {
            max-width: 100%;
            max-height: 80%;
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
        }

        function disabilitaModifica() {
            // Seleziona tutti gli input nella pagina
            const inputs = document.querySelectorAll("input");

            // Disabilita la modifica e ripristina lo stile
            inputs.forEach(input => {
                input.readOnly = true; // Aggiunge il readonly
                input.style.border = "1px solid #ccc"; // Ripristina lo stile
            });

            // Rende invisibile il pulsante Modifica
            document.getElementById("btnMod").style.display = "inline-block";

            // Rende visibile il pulsante Disabilita Modifica
            document.getElementById("btnDisMod").style.display = "none";

            // Nasconde il pulsante Salva
            document.getElementById("btnSalva").style.display = "none";
        }
    </script>

</head>
<body>
    <div class="header">
        <img src="logo-placeholder.png" alt="Logo">
        <h1>PT-Linker</h1>
        <img src="profile-placeholder.png" alt="Profile">
    </div>

<form action="ProfiloSettingServlet" method="post">
<div class="container">
    <%
        Utente ut = (Utente) request.getSession().getAttribute("utente");
    %>
    <div>
        <label typeof="Nome">Nome</label>
        <input type="text" name="Nome" value="<%= ut.getNome() %>" readonly>
    </div>
    <div>
        <label typeof="Cognome">Cognome</label>
        <input type="text" name="Cognome" value="<%= ut.getCognome() %>" readonly>
    </div>
    <div>
        <label typeof="Username">Username</label>
        <input type="text" name="Username" value="<%= ut.getUsername() %>" readonly>
    </div>
    <div>
        <label typeof="E-Mail">E-Mail</label>
        <input type="text" name="E-Mail" value="<%= ut.getEmail() %>" readonly>
    </div>
    <div>
        <label typeof="nascita">Data di Nascita</label>
        <input type="text" name="nascita" value="<%= ut.getDataDiNascita() %>" readonly>
    </div>
<%
    String className = ut.getClass().getSimpleName();
    if(className.equals("Cliente")){
        Cliente cliente = (Cliente) ut;
    %>
    <div>
        <label for="Altezza">Altezza</label>
        <input type="text" id="Altezza" name="Altezza" value="<%= cliente.getAltezza() %>" readonly>
    </div>
    <div>
        <label for="Peso">Peso</label>
        <input type="text" id="Peso" name="Peso" value="<%= cliente.getPeso() %>" readonly>
    </div>
    <div>
        <label for="larghezzaGirovita">Larghezza Girovita</label>
        <input type="text" id="larghezzaGirovita" name="larghezzaGirovita" value="<%= cliente.getLarghezzaGirovita() %>" readonly>
    </div>
    <div>
        <label for="circonferenzaBracciaDx">Circonferenza Braccia Destro</label>
        <input type="text" id="circonferenzaBracciaDx" name="circonferenzaBracciaDx" value="<%= cliente.getCirconferenzaBracciaDx() %>" readonly>
    </div>
    <div>
        <label for="circonferenzaBracciaSx">Circonferenza Braccia Sinistro</label>
        <input type="text" id="circonferenzaBracciaSx" name="circonferenzaBracciaSx" value="<%= cliente.getCirconferenzaBracciaSx() %>" readonly>
    </div>
    <div>
        <label for="circonferenzaTorace">Circonferenza Torace</label>
        <input type="text" id="circonferenzaTorace" name="circonferenzaTorace" value="<%= cliente.getCirconferenzaTorace() %>" readonly>
    </div>
    <div>
        <label for="circonferenzaGambaDx">Circonferenza Gamba Destra</label>
        <input type="text" id="circonferenzaGambaDx" name="circonferenzaGambaDx" value="<%= cliente.getCirconferenzaGambaDx() %>" readonly>
    </div>
    <div>
        <label for="circonferenzaGambaSx">Circonferenza Gamba Sinistra</label>
        <input type="text" id="circonferenzaGambaSx" name="circonferenzaGambaSx" value="<%= cliente.getCirconferenzaGambaSx() %>" readonly>
    </div>

<%
    }else if(className.equals("Professionista")){
        Professionista professionista = (Professionista) ut;
%>
    <div><label typeof="Tipo">Tipo</label>
        <input type="text" id="Tipo" name="Tipo" value="<%= professionista.getTipo() %>" readonly>
    </div>
        <%
            ArrayList<String> list = professionista.getAttestati();
            for(String percorso : list ){
        %>
        <div>
            <label><%=percorso%></label>
            <!--<a href="window.open('<%=percorso%>')" target="_blank" download>Visualizza PDF</a>-->
            <a href="#" onclick="window.open('<%=percorso%>', '_blank'); return false;">Visualizza PDF</a>

        </div>
        <%
            }
        %>
    <%
        }
    %>

    <!-- Bottone per abilitare e salvare -->
    <div>
        <button type="button" id="btnDisMod" onclick="disabilitaModifica()" style="display: none;">Disabilita Modifica</button>
        <button type="button" id="btnMod" onclick="abilitaModifica()">Modifica</button>
        <button type="submit" id="btnSalva" style="display: none;">Salva</button>
    </div>

</div>
</form>

</body>
</html>
