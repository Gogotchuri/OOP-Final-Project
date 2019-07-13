package servlets.user;

import controllers.user.ItemCategoryController;
import middlewares.AuthenticatedUser;
import servlets.RoutingConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {RoutingConstants.USER_ITEM_CATEGORIES})
public class ItemCategoriesServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if((new AuthenticatedUser(req, resp)).unauthenticated()) return;
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        (new ItemCategoryController(req, resp, this)).index();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        (new ItemCategoryController(req,resp, this)).store();
    }
}
