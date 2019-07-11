
package servlets.user;

import controllers.user.CyclesController;
import middlewares.AuthenticatedUser;
import servlets.RoutingConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {RoutingConstants.USER_CYCLES})
public class DealCyclesServlet extends HttpServlet {

	/**
	 returned html main components:
     1) list of the links to the user.DealCycleServlet (GET) (some cycle)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {

		//Checking if user is authorized
		if((new AuthenticatedUser(request, response)).unauthenticated()) return;

		int deal_id;
		try { deal_id = Integer.parseInt(request.getParameter("deal_id")); }
		catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"This address should be called, with numeric parameter \"deal_id\"!");
			return;
		}

		(new CyclesController(request, response, this)).index();

	}
}
