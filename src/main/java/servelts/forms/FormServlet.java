package servelts.forms;

import jakarta.servlet.http.HttpServlet;
import user.UserController;

public class FormServlet extends HttpServlet {
    protected UserController controller;

    public FormServlet(UserController controller) {
        this.controller = controller;
    }
}
