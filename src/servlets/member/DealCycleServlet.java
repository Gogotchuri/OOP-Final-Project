
package servlets.member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/deals/deal/cycles/cycle"})
public class DealCycleServlet extends HttpServlet{

	/**
	 Returns some possible cycle of some deal

     returned html main components:
     1) cycle information
     2) link to the member.DealCycleServlet (PUT) (accept/undo/reject button)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {


	}


	/**
	 Accepts/Undo/Remove offered cycle

	 returned html:
	 if was accepted or undo:
	 	dispatch to member.DealCycleServlet (GET) (cycle where action performed)
	 else if removed:
	 	dispatch to member.DealCyclesServlet (GET) (all cycles of deal, there cycle was deleted)
	 */
	@Override
	protected void doPut(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {


	}
}
