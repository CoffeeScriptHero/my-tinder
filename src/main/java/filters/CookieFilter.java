package filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.User;
import user.UserController;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class CookieFilter extends HttpFilter {
    public CookieFilter(UserController controller) {
        super(controller);
    }

    @Override
    public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Optional<Cookie> cookieId = Optional.ofNullable(cookies)
                .flatMap(cc -> Arrays.stream(cc).filter(c -> c.getName().equals("id"))
                        .findFirst());

        if (cookieId.isPresent()) {
            // тобто мейн юзера не встановлено, бо перелік id у бд починається з 1
            User user = controller.getMainUser();
            if (user.getId() == 0 || !Objects.equals(user.getCookieId(), cookieId.get().getValue())) {
                controller.setMainUser(cookieId.get().getValue());
            }
            chain.doFilter(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
    }
}
