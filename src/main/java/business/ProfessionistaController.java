package business;

import data.DAO.AutenticazioneDAO;
import data.entity.Cliente;
import data.entity.Professionista;
import data.service.ProfessionistaService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/ProfessionistaController")
public class ProfessionistaController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProfessionistaService prof= new ProfessionistaService();
        Professionista pr= (Professionista) request.getSession().getAttribute("utente");
        // Imposto il content type per la risposta come HTML
        response.setContentType("text/html");

        // Iniziamo a costruire la risposta HTML
        PrintWriter out = response.getWriter();

        // Inizio della tabella
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Nome</th>");
        out.println("<th>Cognome</th>");
        out.println("<th>Data di Nascita</th>");
        out.println("<th>Ultima Dieta</th>");
        out.println("<th>Dieta</th>");
        out.println("<th>Pagamenti</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println(prof.getClienti(pr.getId()));
        out.println("</table>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Ottieni i parametri dai form
        String action = req.getParameter("action");  // 'dieta' o 'pagamenti'
        String clienteId = req.getParameter("clienteId"); // ID cliente

        // Controlla quale azione eseguire
        if (action != null) {
            if (action.equals("dieta")) {
                // Memorizzo l'ID nella sessione
                HttpSession session = req.getSession();
                session.setAttribute("clienteId", clienteId);

                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/gestione_dieta.jsp");
                dispatcher.forward(req, resp);
            } else if (action.equals("progressi")) {
                System.out.println("Progressi home prof");
                // Memorizzo l'ID nella sessione
                HttpSession session = req.getSession();
                session.setAttribute("clienteId", clienteId);

                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/progressi_professionista.jsp");
                dispatcher.forward(req, resp);
            }else{
                throw new ServletException("Invalid action");
            }
        }else{
            throw new ServletException("Action non valido");
        }
    }
}
