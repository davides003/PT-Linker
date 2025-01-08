package presentation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
    // Gestisce la navigazione e l'invio del form
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Se il metodo è GET, l'utente sta cercando di navigare alla pagina di login
        // Quindi mostriamo il form di login
        request.getRequestDispatcher("WEB-INF/registrazione.jsp").forward(request, response);
    }

    // Gestisce la raccolta dei dati dal form e la logica di login
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read main form fields
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String birthdate = request.getParameter("birthdate");
        String role = request.getParameter("role");

        // Initialize additional fields
        String height = null;
        String weight = null;
        String waistCircumference = null;
        String armCircumferenceRight = null;
        String armCircumferenceLeft = null;
        String legCircumferenceRight = null;
        String legCircumferenceLeft = null;
        String chestCircumference = null;
        String certificates = null; // This would typically be handled differently

        // Check the role and retrieve additional data accordingly
        if ("cliente".equals(role)) {
            height = request.getParameter("altezza");
            weight = request.getParameter("peso");
            waistCircumference = request.getParameter("girovita");
            armCircumferenceRight = request.getParameter("circonferenza-braccio-destro");
            armCircumferenceLeft = request.getParameter("circonferenza-braccio-sinistro");
            legCircumferenceRight = request.getParameter("circonferenza-gamba-destra");
            legCircumferenceLeft = request.getParameter("circonferenza-gamba-sinistra");
            chestCircumference = request.getParameter("circonferenza-torace");
        } else if ("personal_trainer".equals(role) || "nutrizionista".equals(role)) {
            // Handle file upload for certificates (this requires additional handling)
            // For simplicity, we will just retrieve the parameter name
            certificates = request.getParameter("certificati");
        }

        // Process the data (e.g., save to database, validate, etc.)
        // For demonstration, we will just print the data to the console
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Birthdate: " + birthdate);
        System.out.println("Role: " + role);

        // Print additional fields based on role
        if ("cliente".equals(role)) {
            System.out.println("Height: " + height);
            System.out.println("Weight: " + weight);
            System.out.println("Waist Circumference: " + waistCircumference);
            System.out.println("Arm Circumference Right: " + armCircumferenceRight);
            System.out.println("Arm Circumference Left: " + armCircumferenceLeft);
            System.out.println("Leg Circumference Right: " + legCircumferenceRight);
            System.out.println("Leg Circumference Left: " + legCircumferenceLeft);
            System.out.println("Chest Circumference: " + chestCircumference);
        } else if ("personal_trainer".equals(role) || "nutrizionista".equals(role)) {
            System.out.println("Certificates: " + certificates);
        }

        // Set response type
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        // Build HTML response
        String htmlResponse = "<html><body>";
        htmlResponse += "<h2>Registrazione avvenuta con successo!</h2>";
        htmlResponse += "<p>Grazie per esserti registrato, " + name + "!</p>";
        htmlResponse += "</body></html>";

        // Return response
        writer.println(htmlResponse);
    }
}
