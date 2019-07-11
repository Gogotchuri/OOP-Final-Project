
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
     returned html main components:
     1) fields for creating deal
     2) link to the user.DealCreatorServlet (POST) (create new deal button)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {
		//Checking if user is authorized
		if((new AuthenticatedUser(request, response)).unauthenticated()) return;

		(new DealsController(request, response, this)).create();

	}


	/**
	 Checks whenever entered data satisfies deal creating rules:

	 Parameters should be type of:
	 item_id = 1 & item_id = 2 & wanted_id = 3 & wanted_id = 4
	 that means that
	 with items with id = 1 and 2 user wants to create Deal
	 and exchange that items into items with category 3 and 4

	 If satisfies, creates new deal.

     returned html:
     if satisfies:
	    dispatch to front.Deal (GET) (deal view)
	 else:
	 	dispatch to user.DealCreatorServlet (GET) (deal creator form)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
						   HttpServletResponse response)
		throws ServletException, IOException {
		//Checking if user is authorized
		if((new AuthenticatedUser(request, response)).unauthenticated()) return;

		(new DealsController(request, response, this)).store();
	}
}
