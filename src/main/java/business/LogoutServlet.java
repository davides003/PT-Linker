package business;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/Logout") // URL a cui sarà accessibile la servlet
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ottieni la sessione corrente
        HttpSession session = request.getSession(false); // false evita di creare una nuova sessione se non esiste

        if (session != null) {
            // Invalida la sessione per fare il logout
            session.invalidate();
            System.out.println("Sessione invalidata: logout effettuato con successo.");
        }

        // Reindirizza l'utente alla pagina di login o homepage
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Per supportare sia GET che POST, chiama semplicemente il metodo doGet
        doGet(request, response);
    }
}
