package servelts.html;

import freemarker.template.Configuration;
import jakarta.servlet.http.HttpServlet;
import user.UserController;

public class HtmlServlet extends HttpServlet {
    protected final Configuration conf;
    protected UserController controller;

    public HtmlServlet(Configuration conf) {
        this.conf = conf;
    }

    public HtmlServlet(Configuration conf, UserController controller) {
        this.conf = conf;
        this.controller = controller;
    }
}
