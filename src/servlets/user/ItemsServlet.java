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

@WebServlet(urlPatterns = {RoutingConstants.USER_ITEMS})
public class ItemsServlet extends HttpServlet {

    //Checking if user is authenticated before entering any method
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if((new AuthenticatedUser(req,resp)).unauthenticated()) return;
        super.service(req, resp);
    }

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
        int id = checkIDPresent(req,resp);
        if(id == -1) return;

        (new ItemController(req,resp,this)).destroy(id);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
                                                throws ServletException, IOException {
        int id = checkIDPresent(req,resp);
        if(id == -1) return;

        (new ItemController(req, resp, this)).update(id);
    }

    /**
     * Checks if request has an id parameter, or returns -1 and sends api errors
     *
     * @return id if present otherwise -1
     */
    private int checkIDPresent(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
            if(id < 1) throw new NumberFormatException();
        }catch (NumberFormatException e){
            PrintWriter pw = resp.getWriter();
            resp.setStatus(404);
            //Send error to api
            pw.print("{\"error\":\"This path should be called with parameter (Positive Integer) 'id'!\"}");
            pw.flush();
            return -1;
        }
        return id;
    }
}
