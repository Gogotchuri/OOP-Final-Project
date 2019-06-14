
package servlets.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/deals/cycles/"})
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


	}


	/**
	 Accepts/Undo offered cycle

	 returned html:
	 dispatch to user.DealCycleServlet (GET) (cycle where action performed)
	 */
	@Override
	protected void doPut(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {


	}


	/**
	 Deletes offered cycle

	 returned html:
	 dispatch to user.DealCyclesServlet (GET) (all cycles of deal, there cycle was deleted)
	 */
	@Override
	protected void doDelete(HttpServletRequest request,
							 HttpServletResponse response)
		throws ServletException, IOException {


	}
}
