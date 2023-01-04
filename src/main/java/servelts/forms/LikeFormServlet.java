package servelts.forms;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.User;
import user.UserController;

import java.io.IOException;

public class LikeFormServlet extends FormServlet {
    public LikeFormServlet(UserController controller) {
        super(controller);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String param = req.getParameter("submit");
        boolean isLiked = Boolean.parseBoolean(param);
        String path = req.getHeader("referer"); // last path before /like-form
        String id = path.substring(path.lastIndexOf('/') + 1);

        if (isLiked) {
            String name = req.getParameter("name");
            String img = req.getParameter("img");
            controller.saveLikedProfile(new User(Integer.parseInt(id), name, img));
        }

        resp.sendRedirect("/users/" + (Integer.parseInt(id) + 1));
    }
}
