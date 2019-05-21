package servlets.front;

import controllers.front.IndexController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // req.getRequestDispatcher("/home.html").forward(req, resp);
        (new IndexController(req, resp, this)).index();
    }

}
