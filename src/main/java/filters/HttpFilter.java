package filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.UserController;

import java.io.IOException;

public abstract class HttpFilter implements Filter {
    protected UserController controller;

    public HttpFilter() {}

    public HttpFilter(UserController controller) {
        this.controller = controller;
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    public boolean isHttp(ServletRequest req, ServletResponse resp) {
        return req instanceof HttpServletRequest && resp instanceof HttpServletResponse;
    }

    abstract void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        if (isHttp(req, resp)) {
            HttpServletRequest httpReq = (HttpServletRequest) req;
            HttpServletResponse httpResp = (HttpServletResponse) resp;

            doHttpFilter(httpReq, httpResp, filterChain);
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {}
}
