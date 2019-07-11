package servlets.user;

import controllers.user.ItemController;
import servlets.RoutingConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {RoutingConstants.USER_SINGLE_ITEM})
public class ItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                                                        throws ServletException, IOException {
        int id = 0; //TODO parse it from real request
        (new ItemController(req, resp, this)).show(id);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                                                        throws ServletException, IOException {
        doGet(req, resp);
    }
}
