
package servlets.member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/deals/deal/cycles"})
public class DealCyclesServlet extends HttpServlet {

	/**
	 returned html main components:
     1) list of the links to the member.DealCycleServlet (GET) (some cycle)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {


	}
}
