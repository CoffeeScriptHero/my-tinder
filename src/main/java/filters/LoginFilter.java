package filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.UserController;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class LoginFilter extends HttpFilter{
    public LoginFilter(UserController controller) {
        this.controller = controller;
    }

    @Override
    void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Optional<Cookie> cookieId = Optional.ofNullable(cookies)
                .flatMap(cc -> Arrays.stream(cc).filter(c -> c.getName().equals("id"))
                        .findFirst());

        if (cookieId.isPresent()) {
             resp.sendRedirect("/users/1");
        } else {
            chain.doFilter(req, resp);
        }
    }
}
