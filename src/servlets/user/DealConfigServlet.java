
package servlets.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/deals/configuration"})
public class DealConfigServlet extends HttpServlet {

	/**
     returned html main components:
     1) filled fields with information of deal, for updating.
     2) link to the user.DealConfigServlet (PUT) (submit deal changes button)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {


	}


	/**
	 Checks whenever entered data satisfies deal editing rules.
	 If satisfies, edits deal information.

     returned html:
     if satisfies:
	    dispatch to front.DealServlet (GET) (deal view)
	 else:
	    dispatch to user.DealConfigServlet (GET) (deal configuration form)
	 */
	@Override
	protected void doPut(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {


	}


	/**
	 Deletes deal.

	 returned html:
	 if deleted successfully:
	 	dispatch to front.Deals (GET) (only user deals)
	 else:
	 	dispatch to front.Deal (GET) (stays on deal statement)
	 */
	@Override
	protected void doDelete(HttpServletRequest request,
						  	 HttpServletResponse response)
		throws ServletException, IOException {


	}
}
