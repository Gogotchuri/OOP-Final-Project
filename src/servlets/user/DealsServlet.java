package servlets.user;

import controllers.user.DealsController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * /TODO check auth middleware in every user servlet (Whether user is logged in or not)
 * This servlet is for returning all deals of the given user
 * */
@WebServlet(urlPatterns = {"/user/deals"})
public class DealsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        (new DealsController(req, resp, this)).index();
    }
}
