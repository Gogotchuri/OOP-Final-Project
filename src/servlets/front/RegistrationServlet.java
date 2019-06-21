
package servlets.front;

import controllers.front.AuthController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/register"})
public class RegistrationServlet extends HttpServlet {

	/**
     returned html main components:
     1) fields for registration
     2) link to the front.LoginServlet (POST) (submit new user information)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {
		new AuthController(request, response, this).registerForm();
	}


	/**
	 Checks whenever entered data satisfies registration rules.
	 If satisfies, registers guest.

     returned html:
     if satisfies:
	    dispatch to front.HomeServlet (GET) (home page)
	 else:
	    dispatch to front.RegistrationServlet (GET) (registration form)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
						   HttpServletResponse response)
		throws ServletException, IOException {

		String
			username = request.getParameter("username"),
			password = request.getParameter("password"),
			email = request.getParameter("email"),
			firstName = request.getParameter("first-name"),
			lastName = request.getParameter("last-name"),
			phoneNumber = request.getParameter("phone-number");

		new AuthController(request, response, this).register (
			username, password, email, firstName, lastName, phoneNumber
		);
	}
}