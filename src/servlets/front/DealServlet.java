
package servlets.front;

import controllers.front.DealsController;
import servlets.RoutingConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {RoutingConstants.SINGLE_DEAL})
public class DealServlet extends HttpServlet {

    /**
     returned html main components:
     1) Information about some deal

     If deal of this user:
     2) link to the user.DealConfigServlet (GET) (deal configuration form)
     3) link to the user.DealConfigServlet (DELETE) (deal delete button)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int dealID;
        try { dealID = Integer.parseInt(request.getParameter("id")); }
        catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "This address should be called, with numeric parameter \"id\"!");
            return;
        }

        new DealsController(request, response, this).show(dealID);
    }
}
