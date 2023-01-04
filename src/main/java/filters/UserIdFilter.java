package filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.UserController;

import java.io.IOException;

public class UserIdFilter extends HttpFilter{
    public UserIdFilter(UserController controller) {
        super(controller);
    }

    @Override
    public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path == null) {
            chain.doFilter(req, resp);
            return;
        } else if (path.startsWith("/")) {
            path = path.substring(1);
        }

        try {
            int id = Integer.parseInt(path);
            int mainUserId = controller.getMainUser().getId();

            if (id == 1) {
                controller.clearLikedProfiles();
            }

            if (id == mainUserId) {
                resp.sendRedirect("/users/" + (id + 1));
            } else {
                boolean userExists = controller.checkUserExists(id);
                if (userExists) {
                    chain.doFilter(req, resp);
                } else {
                    resp.sendRedirect("/liked");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.sendRedirect("/not-found");
        }
    }
}
