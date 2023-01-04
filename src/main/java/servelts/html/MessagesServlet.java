package servelts.html;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.Message;
import user.User;
import user.UserController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MessagesServlet extends HtmlServlet {
    public MessagesServlet(Configuration conf, UserController controller) {
        super(conf, controller);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = 0;
        String path = req.getPathInfo();

        if (path.startsWith("/")) {
             id = Integer.parseInt(path.substring(1));
        }

        Optional<User> ou = controller.getById(id);

        if (ou.isEmpty()) {
            resp.sendRedirect("/not-found");
            return;
        }

        User user = ou.get();
        User mainUser = controller.getMainUser();

        List<Message> messages = controller.getDialogue(mainUser.getId(), user.getId());

        Map<String, Object> data = new HashMap<>();

        data.put("user", user);
        data.put("mainUser", mainUser);
        data.put("messages", messages);

        try (PrintWriter w = resp.getWriter()) {
            conf.getTemplate("chat.ftl").process(data, w);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
