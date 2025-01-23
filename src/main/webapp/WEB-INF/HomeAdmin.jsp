<%@ page import="data.entity.Amministratore" %>
<%@ page import="data.entity.Cliente" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="data.entity.Professionista" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9; /* Sfondo chiaro */
        }

        h1, h2, h3 {
            color: #333;
            text-align: center;
        }

        h1 {
            background-color: #FF6600; /* Colore arancione */
            color: white;
            padding: 15px 0;
            margin: 0;
            box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
        }

        h2 {
            margin-top: 20px;
            font-size: 24px;
        }

        h3 {
            font-size: 20px;
            margin: 10px 0;
        }

        form {
            max-width: 600px;
            margin: 20px auto;
            background: white;
            padding: 20px;
            box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        form label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }

        form input[type="text"],
        form input[type="email"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
        }

        form button {
            background-color: #FF6600;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px; /* Spazio sopra il bottone */
            display: block; /* Trasforma il bottone in un elemento a blocco */
            margin-left: auto; /* Centra il bottone orizzontalmente */
            margin-right: auto; /* Centra il bottone orizzontalmente */
        }

        form button:hover {
            background-color: #e05500;
        }

        table {
            border-collapse: collapse;
            width: 90%;
            margin: 20px auto;
            background: white;
            box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            overflow: hidden;
        }

        th, td {
            text-align: left;
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #FF6600;
            color: white;
            text-align: center;
        }

        td {
            text-align: center;
            color: #555;
        }

        table tr:hover {
            background-color: #f1f1f1;
        }

        button {
            background-color: #FF6600;
            color: white;
            padding: 8px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }

        button:hover {
            background-color: #e05500;
        }

        ul {
            list-style-type: none;
            padding: 0;
        }

        li {
            color: #555;
            margin: 5px 0;
        }

        hr {
            margin: 30px auto;
            width: 90%;
            border: 1px solid #ddd;
        }

        .logout-container {
            text-align: center;
            margin-top: 20px;
        }

        .logout-container button {
            background-color: #FF6600;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        .logout-container button:hover {
            background-color: #e05500;
        }
        .logout-container {
            text-align: center;
            margin-top: 20px;
        }

        .logout-container button {
            background-color: #FF6600;
            color: white;
            padding: 12px 25px; /* Dimensioni aumentate */
            border: none;
            border-radius: 8px; /* Angoli arrotondati più evidenti */
            cursor: pointer;
            font-size: 18px; /* Testo più grande */
            font-weight: bold; /* Testo in grassetto */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Ombra per maggiore profondità */
            transition: all 0.3s ease; /* Transizione fluida per gli hover */
        }

        .logout-container button:hover {
            background-color: #e05500; /* Colore hover */
            transform: scale(1.05); /* Leggero zoom */
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* Ombra più marcata */
        }
    </style>
</head>
<body>
<h1>Admin Page</h1>

<!-- Visualizzazione e modifica dati admin -->
<h2>I tuoi dati</h2>
<form action="AdminController" method="post">
    <input type="hidden" name="action" value="updateAdmin">
    <label for="name">Nome:</label>
    <input type="text" id="name" name="name" value="Nome" required><br>
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" value="Email" required><br>
    <button type="submit">Salva</button>
</form>

<hr>

<!-- Visualizzazione utenti -->
<h2>Lista utenti</h2>
<%
    ArrayList<Cliente> clients = null;//(ArrayList<Cliente>) request.getAttribute("clients");
    ArrayList<Professionista> professionista = null;//(ArrayList<PersonalTrainer>) request.getAttribute("professionistas");
%>
<h2>Professionisti</h2>
    <table>
        <%if (professionista != null && !professionista.isEmpty()) { %>
            <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Ruolo</th>
                <th>Certificazioni</th>
                <th>Abilita/Disabilita</th>
            </tr>
            </thead>
        <% } %>
        <hr>
        <!-- Visualizzazione Personal Trainers -->
        <% if (professionista == null || professionista.isEmpty()) { %>
            <h3>Non ci sono Professionisti registrati</h3>
        <% }else{ %>
        <h3>Personal Trainer</h3>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Certificazioni</th>
                <th>Abilita/Disabilita</th>
            </tr>
            </thead>
            <tbody>
            <%
                for (Professionista pr : professionista) {
                    if(pr.getTipo().equals("personal_trainer")){
            %>
            <tr>
                <td><%= pr.getId() %></td>
                <td><%= pr.getNome() %></td>
                <td>
                    <ul>
                        <% for (String cert : pr.getAttestati()) { %>
                        <li><%= cert %></li>
                        <% } %>
                    </ul>
                </td>
                <td>
                    <form action="AdminController" method="post">
                        <input type="hidden" name="action" value="togglePersonalTrainer">
                        <input type="hidden" name="id" value="<%= pr.getId() %>">
                        <button type="submit">
                            <%= pr.isAbilitato() ? "Disabilita" : "Abilita" %>
                        </button>
                    </form>
                </td>
            </tr>
            <% }else{%>
            </tbody>
        </table>
        <hr>
    <!-- Visualizzazione Nutrizionisti -->
    <h3>Nutrizionisti</h3>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Certificazioni</th>
                <th>Abilita/Disabilita</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><%= pr.getId() %></td>
                <td><%= pr.getNome() %></td>
                <td>
                    <ul>
                        <% for (String cert : pr.getAttestati()) { %>
                        <li><%= cert %></li>
                        <% } %>
                    </ul>
                </td>
                <td>
                    <form action="AdminController" method="post">
                        <input type="hidden" name="action" value="toggleNutrizionista">
                        <input type="hidden" name="id" value="<%= pr.getId() %>">
                        <button type="submit">
                            <%= pr.isAbilitato() ? "Disabilita" : "Abilita" %>
                        </button>
                    </form>
                </td>
            </tr>
            <% }
            }%>
            </tbody>
        </table>
    <%
        }
    %>
        <hr>
    <h2>Clienti</h2>
    <% if (clients == null || clients.isEmpty()) { %>
        <h3>Non ci sono Clienti registrati</h3>
    <% }else{ %>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Email</th>
            </tr>
            </thead>
            <tbody>
            <%
                for (Cliente client : clients) {
            %>
            <tr>
                <td><%= client.getId() %></td>
                <td><%= client.getNome() %></td>
                <td><%= client.getEmail() %></td>
            </tr>
            <% } %>
        </tbody>
        </table>
    <% } %>
    </table>
    <!-- Bottone Logout -->
<div class="logout-container">
    <form action="Logout" method="post">
        <button type="submit">Logout</button>
    </form>
</div>
</body>
</html>