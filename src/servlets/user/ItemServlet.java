package servlets.user;

import controllers.user.ItemController;
import middlewares.AuthenticatedUser;
import servlets.RoutingConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {RoutingConstants.USER_SINGLE_ITEM})
public class ItemServlet extends HttpServlet {
    //Checking if user is authenticated before entering any method
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if((new AuthenticatedUser(req,resp)).unauthenticated()) return;
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                                                        throws ServletException, IOException {
        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException nfe){
            nfe.printStackTrace();
            PrintWriter pw = resp.getWriter();
            resp.setStatus(404);
            //Send error to api
            pw.print("{\"error\":\"This path should be called with parameter 'id'!\"}");
            pw.flush();
            return;
        }

        (new ItemController(req, resp, this)).show(id);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                                                        throws ServletException, IOException {
        doGet(req, resp);
    }
}
