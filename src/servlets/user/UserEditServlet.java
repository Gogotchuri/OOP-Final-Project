
package servlets.user;

import controllers.user.UserController;
import middlewares.AuthenticatedUser;
import servlets.RoutingConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {RoutingConstants.USER_EDIT})
public class UserEditServlet extends HttpServlet {

	/**
     returned html main components:
     1) filled fields with information of user, for updating.
     2) link to the user.UserEditServlet (PUT) (submit user changes button)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {

		//Checking if user is authorized
		if((new AuthenticatedUser(request, response)).unauthenticated()) return;

		(new UserController(request, response, this)).editForm();

	}


	/**
	 Checks whenever entered data satisfies user editing rules.
	 If satisfies, edits user information.

     returned html:
     if satisfies:
	    dispatch to front.UserServlet (GET) (user profile)
	 else:
	    dispatch to user.UserEditServlet (GET) (user configuration form)
	 */
	@Override
	protected void doPut(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {

		//Checking if user is authorized
		if((new AuthenticatedUser(request, response)).unauthenticated()) return;

		(new UserController(request, response, this)).update();


	}
}
