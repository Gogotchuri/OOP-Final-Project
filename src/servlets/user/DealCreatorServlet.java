
package servlets.user;

import controllers.user.DealsController;
import middlewares.AuthenticatedUser;
import servlets.RoutingConstants;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {RoutingConstants.USER_DEAL_CREATE})
public class DealCreatorServlet extends HttpServlet {

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
     * returned html main components:
     * 1) fields for creating deal
     * 2) link to the user.DealCreatorServlet (POST) (create new deal button)
	 *
	 * @param request - Request Object for getting user request
	 * @param response - Response Object for sending back response
	 * @throws ServletException - If some Servlet Exception happens
	 * @throws IOException - If Some IOException happens
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {
		(new DealsController(request, response, this)).create();
	}


	/**
	 * Checks whenever entered data satisfies deal creating rules:
	 *
	 * Parameters should be type of:
	 * item_id = 1 & item_id = 2 & wanted_id = 3 & wanted_id = 4
	 * that means that
	 * with items with id = 1 and 2 user wants to create Deal
	 * and exchange that items into items with category 3 and 4
	 *
	 * If satisfies, creates new deal.
	 *
     * returned html:
     * if satisfies:
	 *    dispatch to front.Deal (GET) (deal view)
	 * else:
	 * 	  dispatch to user.DealCreatorServlet (GET) (deal creator form)
	 *
	 * @param request - Request Object for getting user request
	 * @param response - Response Object for sending back response
	 * @throws ServletException - If some Servlet Exception happens
	 * @throws IOException - If Some IOException happens
	 */
	@Override
	protected void doPost(HttpServletRequest request,
						   HttpServletResponse response)
		throws ServletException, IOException {
		(new DealsController(request, response, this)).store();
	}

}
