package presentation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    // Gestisce la navigazione e l'invio del form
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Se il metodo è GET, l'utente sta cercando di navigare alla pagina di login
        // Quindi mostriamo il form di login
        request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
    }

    // Gestisce la raccolta dei dati dal form e la logica di login
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Otteniamo i dati dal form (username e password)
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Chiamare la logica di business per la validazione
        /*BusinessLogic loginLogic = new BusinessLogic();
        boolean isValid = loginLogic.verifyCredentials(username, password);

        if (isValid) {
            // Credenziali corrette, reindirizziamo alla pagina del profilo
            request.getSession().setAttribute("user", username);
            response.sendRedirect("profile.jsp");
        } else {
            // Credenziali errate, visualizziamo un messaggio di errore
            request.setAttribute("errorMessage", "Username o password non validi");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }*/
    }
}