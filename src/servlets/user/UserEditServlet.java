
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
     * returned html main components:
     * 1) filled fields with information of user, for updating.
     * 2) link to the user.UserEditServlet (POST) (submit user changes button)
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
		(new UserController(request, response, this)).editForm();
	}


	/**
	 * Checks whenever entered data satisfies user editing rules.
	 * If satisfies, edits user information.
	 *
     * returned html:
     * if satisfies:
	 *    dispatch to front.UserServlet (GET) (user profile)
	 * else:
	 *    dispatch to user.UserEditServlet (GET) (user configuration form)
	 *
	 * @param request - Request Object for getting user request
	 * @param response - Response Object for sending back response
	 * @throws ServletException - If some Servlet Exception happens
	 * @throws IOException - If Some IOException happens
	 */
	@Override
	protected void doPut(HttpServletRequest request,
						   HttpServletResponse response)
		throws ServletException, IOException {
		(new UserController(request, response, this)).update();
	}

}
