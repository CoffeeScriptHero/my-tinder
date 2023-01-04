package servelts.forms;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.UserController;

import java.io.IOException;

public class MessageFormServlet extends FormServlet {

    public MessageFormServlet(UserController controller) {
        super(controller);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // receiver id string
        String ris = req.getParameter("receiverId");
        // sender id string
        String sis = req.getParameter("senderId");
        String msg = req.getParameter("message");

        try {
            int receiverId = Integer.parseInt(ris);
            int senderId = Integer.parseInt(sis);
            controller.addMessage(senderId, receiverId, msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
