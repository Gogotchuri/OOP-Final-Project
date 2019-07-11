package servlets.user;

import controllers.user.ItemController;
import servlets.RoutingConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {RoutingConstants.USER_ITEMS})
public class ItemsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                                                throws ServletException, IOException {
        (new ItemController(req, resp, this)).index();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                                                throws ServletException, IOException {
        (new ItemController(req, resp, this)).store();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
                                                throws ServletException, IOException {
        int id = 0; //TODO implement me
        (new ItemController(req,resp,this)).destroy(id);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
                                                throws ServletException, IOException {
        int id = 0;//TODO implement me
        (new ItemController(req, resp, this)).update(id);
    }
}
