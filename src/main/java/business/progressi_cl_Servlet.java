package business;
import data.entity.Progressi;
import data.service.ProgressiService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/progressi_cl_servlet")
public class progressi_cl_Servlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("WEB-INF/progressi_cliente.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            int idProgresso = Integer.parseInt(request.getParameter("idProgresso"));
            float peso = Float.parseFloat(request.getParameter("peso"));
            float larghezzaGirovita = Float.parseFloat(request.getParameter("girovita"));
            float circonferenzaBracciaDx = Float.parseFloat(request.getParameter("braccioDx"));
            float circonferenzaBracciaSx = Float.parseFloat(request.getParameter("braccioSx"));
            float circonferenzaTorace = Float.parseFloat(request.getParameter("torace"));
            float circonferenzaGambaDx = Float.parseFloat(request.getParameter("gambaDx"));
            float circonferenzaGambaSx = Float.parseFloat(request.getParameter("gambaSx"));
            String descrizione = request.getParameter("descrizione");

            Part filePart = request.getPart("foto");
            String foto = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            //Progressi progressi = new Progressi(idCliente, foto, descrizione, idProgresso, peso, larghezzaGirovita, circonferenzaBracciaDx, circonferenzaBracciaSx, circonferenzaTorace, circonferenzaGambaDx, circonferenzaGambaSx);

            ProgressiService progressiService = new ProgressiService();
            //progressiService.registraProgressi(progressi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
