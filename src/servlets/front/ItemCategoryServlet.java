
package servlets.front;

import controllers.front.ItemCategoryController;
import servlets.RoutingConstants;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {RoutingConstants.ITEM_CATEGORIES})
public class ItemCategoryServlet extends HttpServlet {

    /**
     * If type_name and manufacturer_name parameters isn't specified
     * Returns json object with arrays of types and
     * manufacturers representing all currently known entries in db
     * Otherwise returns models/series json object which has those references
     *
     * @param request - Request Object for getting user request
     * @param response - Response Object for sending back response
     * @throws IOException - If Some IOException happens
     * */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        (new ItemCategoryController(request, response, this)).index();
    }

}
