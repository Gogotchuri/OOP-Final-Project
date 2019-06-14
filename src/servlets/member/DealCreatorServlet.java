
package servlets.member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/user/deals/create"})
public class DealCreatorServlet extends HttpServlet {

	/**
     returned html main components:
     1) fields for creating deal
     2) link to the member.DealCreatorServlet (POST) (create new deal button)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {


	}


	/**
	 Checks whenever entered data satisfies deal creating rules.
	 If satisfies, creates new deal.

     returned html:
     if satisfies:
	    dispatch to front.Deal (GET) (deal view)
	 else:
	 	dispatch to member.DealCreatorServlet (GET) (deal creator form)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
						   HttpServletResponse response)
		throws ServletException, IOException {


	}
}
