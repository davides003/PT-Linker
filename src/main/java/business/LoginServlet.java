package business;
import  jakarta.servlet.RequestDispatcher;
import data.entity.Cliente;
import data.entity.Professionista;
import data.service.LoginService;
import data.entity.Utente;
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
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Chiamare la logica di business per la validazione
        LoginService loginLogic = new LoginService();
        Utente isValid = loginLogic.verificaUtente(email, password);

        if (isValid instanceof Cliente) {

            System.out.println("Cliente");
            request.getSession().setAttribute("utente", isValid);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/home_cliente.jsp");
            dispatcher.forward(request, response);  // Inoltra la richiesta al JSP


        }// Verifica se l'utente è un professionista
        else if (isValid instanceof Professionista) {
            System.out.println("prof");
            request.getSession().setAttribute("utente", isValid);
            System.out.println("prof");

            // Usa RequestDispatcher per inoltrare la richiesta alla pagina JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/home_professionista.jsp");
            dispatcher.forward(request, response);  // Inoltra la richiesta al JSP
        }


        else {
            // Credenziali errate, visualizziamo un messaggio di errore
            request.setAttribute("errorMessage", "Username o password non validi");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
}