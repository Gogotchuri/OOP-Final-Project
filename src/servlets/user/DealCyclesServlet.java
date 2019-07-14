
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

	//Checking if user is authenticated before entering any method
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if((new AuthenticatedUser(req,resp)).unauthenticated()) return;
		super.service(req, resp);
	}
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

		(new CyclesController(request, response, this)).index();

	}
}
