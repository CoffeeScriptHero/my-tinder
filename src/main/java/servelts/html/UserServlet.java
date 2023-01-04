package servelts.html;

import freemarker.template.Configuration;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import user.User;
import user.UserController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class UserServlet extends HttpServlet {
    private final UserController controller;
    private final Configuration conf;

    public UserServlet(UserController controller, Configuration conf) {
        this.controller = controller;
        this.conf = conf;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        Optional<User> ou = controller.getById(id);

        if (ou.isEmpty()) {
            resp.sendRedirect("/not-found");
        }

        User user = ou.get();
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        try (PrintWriter w = resp.getWriter()) {
            conf.getTemplate("like-page.ftl").process(data, w);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
