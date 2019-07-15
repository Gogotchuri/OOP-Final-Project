
package servlets.front;

import controllers.front.AuthController;
import servlets.RoutingConstants;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {RoutingConstants.LOGIN})
public class LoginServlet extends HttpServlet {

	/**
     * returned html main components:
     * 1) fields for logging in
     * 2) link to the front.LoginServlet (POST) (submit login and password)
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
		new AuthController(request, response, this).loginForm();
	}


	/**
	 * Checks whenever entered data matches with some registered account.
	 *
     * returned html:
     * if matches:
	 *   dispatch to front.HomeServlet (GET) (home page)
	 * else:
	 *   dispatch to front.LoginServlet (GET) (login form)
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
		String username = request.getParameter("username"),
				password = request.getParameter("password");
		new AuthController(request, response, this).login(username, password);
	}

}
