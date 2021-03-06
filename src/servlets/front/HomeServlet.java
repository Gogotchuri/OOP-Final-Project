
package servlets.front;

import controllers.front.HomeController;
import servlets.RoutingConstants;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {RoutingConstants.HOME})
public class HomeServlet extends HttpServlet {

    /**
     * returned html main components:
     * 1) link to the front.LoginServlet (GET) (login form)
     * 2) link to the front.RegistrationServlet (GET) (registration form)
     * 3) link to the front.DealServlet (GET) (all deals)
     * 4) In case of login:
     *    link to the front.UserServlet (GET) (user profile)
     *    link to the user.DealCreatorServlet (GET) (deal creator form)
     *
     * @param request - Request Object for getting user request
     * @param response - Response Object for sending back response
     * @throws ServletException - If some Servlet Exception happens
     * @throws IOException - If Some IOException happens
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        new HomeController(request, response, this).index();
    }

}
