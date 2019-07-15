
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
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            PrintWriter pw = response.getWriter();
            response.setStatus(404);
            // Send error to api
            pw.print("{\"error\":\"This path should be called with parameter 'id'!\"}");
            pw.flush();
            return;
        }
        (new ItemController(request, response, this)).show(id);
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
        doGet(request, response);
    }

}
