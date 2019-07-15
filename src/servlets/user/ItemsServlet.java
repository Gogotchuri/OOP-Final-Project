
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

    /**
     * Checking if user is authenticated before entering any method.
     *
     * @param request - Request Object for getting user request
     * @param response - Response Object for sending back response
     * @throws ServletException - If some Servlet Exception happens
     * @throws IOException - If Some IOException happens
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ((new AuthenticatedUser(request,response)).unauthenticated())
            return;
        super.service(request, response);
    }


    /**
     * @param request - Request Object for getting user request
     * @param response - Response Object for sending back response
     * @throws ServletException - If some Servlet Exception happens
     * @throws IOException - If Some IOException happens
     */
    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
        throws ServletException, IOException {
        (new ItemController(request, response, this)).index();
    }


    /**
     * @param request - Request Object for getting user request
     * @param response - Response Object for sending back response
     * @throws ServletException - If some Servlet Exception happens
     * @throws IOException - If Some IOException happens
     */
    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
        throws ServletException, IOException {
        (new ItemController(request, response, this)).store();
    }


    /**
     * @param request - Request Object for getting user request
     * @param response - Response Object for sending back response
     * @throws ServletException - If some Servlet Exception happens
     * @throws IOException - If Some IOException happens
     */
    @Override
    protected void doDelete(HttpServletRequest request,
                             HttpServletResponse response)
        throws ServletException, IOException {
        int id = checkIDPresent(request, response);
        if (id == -1) return;
        (new ItemController(request, response,this)).destroy(id);
    }


    /**
     * @param request - Request Object for getting user request
     * @param response - Response Object for sending back response
     * @throws ServletException - If some Servlet Exception happens
     * @throws IOException - If Some IOException happens
     */
    @Override
    protected void doPut(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        int id = checkIDPresent(request, response);
        if (id == -1) return;
        (new ItemController(request, response, this)).update(id);
    }


    /**
     * Checks if request has an id parameter, or returns -1 and sends api errors.
     *
     * @param request - Request Object for getting user request
     * @param response - Response Object for sending back response
     * @throws IOException - If Some IOException happens
     * @return id if present otherwise -1
     */
    private int checkIDPresent(HttpServletRequest request,
                                HttpServletResponse response)
        throws IOException {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
            if(id < 1) throw new NumberFormatException();
        }catch (NumberFormatException e){
            PrintWriter pw = response.getWriter();
            response.setStatus(404);
            // Send error to api
            pw.print("{\"error\":\"This path should be called with parameter (Positive Integer) 'id'!\"}");
            pw.flush();
            return -1;
        }
        return id;
    }

}
