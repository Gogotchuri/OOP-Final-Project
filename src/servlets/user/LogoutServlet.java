
package servlets.user;

import controllers.front.AuthController;
import servlets.RoutingConstants;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {RoutingConstants.LOGOUT})
public class LogoutServlet extends HttpServlet {

    /**
     * Calls doPost().
     *
     * @param request - Request Object for getting user request
     * @param response - Response Object for sending back response
     * @throws IOException - If Some IOException happens
     */
    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
        throws IOException {
        doPost(request, response);
    }


    /**
     * Logs out user.
     *
     * @param request - Request Object for getting user request
     * @param response - Response Object for sending back response
     * @throws IOException - If Some IOException happens
     */
    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
        throws IOException {
        (new AuthController(request, response, this)).logout();
    }

}
