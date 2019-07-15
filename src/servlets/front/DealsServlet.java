
package servlets.front;

import controllers.front.DealsController;
import controllers.front.DealsController.SearchCriteria;
import controllers.front.DealsController.SearchCriteria.Criteria;
import servlets.RoutingConstants;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(urlPatterns = {RoutingConstants.DEALS})
public class DealsServlet extends HttpServlet {

    /**
     * Finds collection of the deals with some criteria.
     *
     * returned html main components:
     * 1) list of the links to the front.DealServlet (GET) (view of deal),
     *    found by some criteria.
     *
     * @param request - Request Object for getting user request
     * @param response - Response Object for sending back response
     * @throws ServletException - If some Servlet Exception happens
     * @throws IOException - If Some IOException happens
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        /* Initialize criteria parameters passed
         * by 'request' object for indexing deals */

        SearchCriteria sc = new SearchCriteria();
        Enumeration<String> criteria = request.getParameterNames();

        while (criteria.hasMoreElements()) {
            String criteriaName = criteria.nextElement();
            Criteria aCriteria = Criteria.getCriteria(criteriaName);

            if (aCriteria == null) continue;

            sc.addCriteria(aCriteria, request.getParameter(criteriaName));
        }

        new DealsController(request, response, this).index(sc);
    }

}
