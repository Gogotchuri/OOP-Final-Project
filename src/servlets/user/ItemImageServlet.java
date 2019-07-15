package servlets.user;

import controllers.user.ImageController;
import middlewares.AuthenticatedUser;
import servlets.RoutingConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {RoutingConstants.USER_ITEM_IMAGES})
public class ItemImageServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if((new AuthenticatedUser(req, resp)).unauthenticated()) return;
        super.service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int item_id;
        try {
            item_id = Integer.parseInt(req.getParameter("item_id"));
        }catch (NumberFormatException nfe){
            nfe.printStackTrace();
            PrintWriter pw = resp.getWriter();
            resp.setStatus(404);
            pw.print("{\"error\":\"This path should be called with parameter 'item_id'!\"}");
            pw.flush();
            return;
        }
        (new ImageController(req, resp, this)).storeForItem(item_id);
    }
}
