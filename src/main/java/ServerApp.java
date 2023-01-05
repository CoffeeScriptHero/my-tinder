import filters.CookieFilter;
import filters.LoginFilter;
import filters.UserIdFilter;
import freemarker.template.Configuration;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servelts.forms.LoginFormServlet;
import servelts.forms.MessageFormServlet;
import servelts.redirects.UsersRedirectServlet;
import servelts.html.*;
import user.UserController;
import servelts.forms.LikeFormServlet;
import servelts.html.StaticContentServlet;

import java.io.File;
import java.sql.*;
import java.util.EnumSet;

// http://localhost:8050/users
public class ServerApp {
    private static final EnumSet<DispatcherType> ft = EnumSet.of(DispatcherType.REQUEST);
    private final static String URL = "jdbc:postgresql://tai.db.elephantsql.com/kmsmihnj";
    private final static String USER = "kmsmihnj";
    private final static String PASSWORD = "AgJkW5xtrr3l4ZOWG9qrkV8MPDhb2Bss";

    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        Configuration conf = new Configuration(Configuration.VERSION_2_3_31);
        conf.setDirectoryForTemplateLoading(new File("dynamic"));
        conf.setDefaultEncoding("UTF-8");
        Server server = new Server(8050);

        UserController userController = new UserController(conn);
        UserServlet usersPage = new UserServlet(userController, conf);
        LikedServlet likedUsers = new LikedServlet(conf, userController);
        MessagesServlet messagesServlet = new MessagesServlet(conf, userController);
        LikeFormServlet likeForm = new LikeFormServlet(userController);
        LoginFormServlet loginForm = new LoginFormServlet(userController);
        MessageFormServlet messageForm = new MessageFormServlet(userController);

        CookieFilter cookieFilter = new CookieFilter(userController);
        UserIdFilter userIdFilter = new UserIdFilter(userController);
        LoginFilter loginFilter = new LoginFilter(userController);

        ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(LoginServlet.class, "/login");
        handler.addServlet(new ServletHolder(loginForm), "/login-form");
        handler.addServlet(UsersRedirectServlet.class, "/");
        handler.addServlet(UsersRedirectServlet.class, "/users");
        handler.addServlet(UsersRedirectServlet.class, "/users/");
        handler.addServlet(new ServletHolder(usersPage), "/users/*");
        handler.addServlet(new ServletHolder(likedUsers), "/liked");
        handler.addServlet(new ServletHolder(likeForm), "/like-form");
        handler.addServlet(new ServletHolder(messagesServlet), "/messages/*");
        handler.addServlet(new ServletHolder(messageForm), "/message-form");
        handler.addServlet(NotFoundServlet.class, "/not-found");
        handler.addServlet(new ServletHolder(new StaticContentServlet("static")), "/static/*");

        handler.addFilter(new FilterHolder(loginFilter), "/login", ft);
        handler.addFilter(new FilterHolder(cookieFilter), "/users/*", ft);
        handler.addFilter(new FilterHolder(cookieFilter), "/liked", ft);
        handler.addFilter(new FilterHolder(cookieFilter), "/messages/*", ft);
        handler.addFilter(new FilterHolder(userIdFilter), "/users/*", ft);

        server.setHandler(handler);
        server.start();
        server.join();
    }
}
