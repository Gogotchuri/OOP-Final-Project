
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
import java.io.PrintWriter;

@WebServlet(urlPatterns = {RoutingConstants.USER_SINGLE_CYCLE})
public class DealCycleServlet extends HttpServlet{

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
	 * Returns some possible cycle of some deal
	 *
     * returned html main components:
     * 1) cycle information
     * 2) link to the user.DealCycleServlet (PUT) (cycle accept/undo button)
     * 3) link to the user.DealCycleServlet (DELETE) (cycle reject button)
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
		int cycle_id;
		try {
			cycle_id = Integer.parseInt(request.getParameter("cycle_id"));
		}
		catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"This address should be called, with numeric parameter \"cycle_id\"!");
			return;
		}
		(new CyclesController(request, response, this)).show(cycle_id);
	}


	/**
	 * Accepts offered cycle, takes cycle_id and deal_id as request parameter
	 *
	 * returned html:
	 * dispatch to user.DealCycleServlet (GET) (cycle where action performed)
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

		int cycle_id, deal_id;
		try {
			cycle_id = Integer.parseInt(request.getParameter("cycle_id"));
			deal_id = Integer.parseInt(request.getParameter("deal_id"));
		} catch (NumberFormatException e) {
			PrintWriter pw = response.getWriter();
			response.setStatus(404);
			pw.print("{\"error\":\"This address should be called, with numeric parameter 'cycle_id' and 'deal_id'!\"}");
			pw.flush();
			return;
		}

		(new CyclesController(request, response, this)).acceptCycle(cycle_id, deal_id);
	}


	/**
	 * Deletes/Rejects offered cycle
	 *
	 * returned html:
	 * dispatch to user.DealCyclesServlet (GET) (all cycles of deal, there cycle was deleted)
	 *
	 * @param request - Request Object for getting user request
	 * @param response - Response Object for sending back response
	 * @throws ServletException - If some Servlet Exception happens
	 * @throws IOException - If Some IOException happens
	 */
	@Override
	protected void doDelete(HttpServletRequest request,
							 HttpServletResponse response)
		throws ServletException, IOException {

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
