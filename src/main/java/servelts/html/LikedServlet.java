package servelts.html;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.User;
import user.UserController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikedServlet extends HtmlServlet {
    public LikedServlet(Configuration conf, UserController controller) {
        super(conf, controller);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<User> users = controller.getLikedProfiles();

        Map<String, Object> data = new HashMap<>();
        data.put("users", users);

        try (PrintWriter pw = resp.getWriter()) {
            conf.getTemplate("people-list.ftl").process(data, pw);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
