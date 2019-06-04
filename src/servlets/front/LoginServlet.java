
package servlets.front;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

	/**
     returned html main components:
     1) fields for logging in
     2) link to the front.LoginServlet (POST) (submit login and password)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {


	}


	/**
	 Checks whenever entered data matches with some registered account.

     returned html:
     if matches:
	    dispatch to front.HomeServlet (GET) (home page)
	 else:
	    dispatch to front.LoginServlet (GET) (login form)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
						   HttpServletResponse response)
		throws ServletException, IOException {


	}
}
