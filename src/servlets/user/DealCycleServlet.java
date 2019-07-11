
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

@WebServlet(urlPatterns = {RoutingConstants.USER_SINGLE_CYCLE})
public class DealCycleServlet extends HttpServlet{

	/**
	 Returns some possible cycle of some deal

     returned html main components:
     1) cycle information
     2) link to the user.DealCycleServlet (PUT) (cycle accept/undo button)
     3) link to the user.DealCycleServlet (DELETE) (cycle reject button)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {
		//Checking if user is authorized
		if((new AuthenticatedUser(request, response)).unauthenticated()) return;

		int cycle_id;
		try { cycle_id = Integer.parseInt(request.getParameter("cycle_id")); }
		catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"This address should be called, with numeric parameter \"cycle_id\"!");
			return;
		}

		(new CyclesController(request, response, this)).show(cycle_id);

	}


	/**
	 Accepts offered cycle, takes cycle_id and deal_id as request parameter

	 returned html:
	 dispatch to user.DealCycleServlet (GET) (cycle where action performed)
	 */
	@Override
	protected void doPut(HttpServletRequest request,
						 HttpServletResponse response)
			throws ServletException, IOException {
		//Checking if user is authorized
		if((new AuthenticatedUser(request, response)).unauthenticated()) return;

		int cycle_id, deal_id;
		try {
			cycle_id = Integer.parseInt(request.getParameter("cycle_id"));
			deal_id = Integer.parseInt(request.getParameter("deal_id"));
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"This address should be called, with numeric parameter \"cycle_id\" and \"deal_id\"!");
			return;
		}

		(new CyclesController(request, response, this)).acceptCycle(cycle_id, deal_id);

	}


	/**
	 Deletes/Rejects offered cycle

	 returned html:
	 dispatch to user.DealCyclesServlet (GET) (all cycles of deal, there cycle was deleted)
	 */
	@Override
	protected void doDelete(HttpServletRequest request,
							HttpServletResponse response)
			throws ServletException, IOException {
		//Checking if user is authorized
		if((new AuthenticatedUser(request, response)).unauthenticated()) return;

		int cycle_id;
		try { cycle_id = Integer.parseInt(request.getParameter("cycle_id")); }
		catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"This address should be called, with numeric parameter \"cycle_id\"!");
			return;
		}

		(new CyclesController(request, response, this)).rejectCycle(cycle_id);

	}
}
