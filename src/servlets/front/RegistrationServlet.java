
package servlets.front;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/registration"})
public class RegistrationServlet extends HttpServlet {

	/**
     returned html main components:
     1) fields for registration
     2) link to the front.LoginServlet (POST) (submit new member information)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {


	}


	/**
	 Checks whenever entered data satisfies registration rules.
	 If satisfies, registers user.

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


	}
}
