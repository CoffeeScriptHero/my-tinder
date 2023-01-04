package servelts.forms;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.User;
import user.UserController;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class LoginFormServlet extends FormServlet {
    public LoginFormServlet(UserController controller) {
        super(controller);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Optional<User> ou = controller.getByEmail(email);

        try {
            if (ou.isEmpty()) {
                Cookie cookie = new Cookie("id", UUID.randomUUID().toString());
                controller.add(email, name, password, cookie.getValue());
                resp.addCookie(cookie);
            } else {
                User user = ou.get();
                if (user.getName().equals(name) && user.getPassword().equals(password)) {
                    resp.addCookie(new Cookie("id", user.getCookieId()));
                } else {
                    resp.setHeader("message", "User already exists!");
                }
            }
        } catch (Exception ex) {
            resp.setHeader("message", "Unexpected error");
            ex.printStackTrace();
        }
    }
}
