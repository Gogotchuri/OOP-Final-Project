
package servlets.user;

import controllers.user.DealsController;
import servlets.RoutingConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {RoutingConstants.USER_SINGLE_DEAL})
public class DealServlet extends HttpServlet {

    /**
     returned html main components:
     Rep
     */
    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
        throws ServletException, IOException {

        int id = 0; //TODO check auth and correct parsing
        (new DealsController(request, response, this)).show(id);
    }
}
