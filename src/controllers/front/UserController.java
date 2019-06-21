
package controllers.front;

import controllers.Controller;
import managers.UserManager;
import models.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController extends Controller {

    /**
     Creates a controller, usually called from servlet, which is also
     passed by parameter. Servlet method passes taken request and response.
     */
    public UserController(HttpServletRequest request,
                           HttpServletResponse response,
                            HttpServlet servlet) {
        super(request, response, servlet);
    }

    /**
     Sets attributes for displaying some user details and
     dispatches to /pages/public/user-details.jsp
     */
    public void show(int userID) throws ServletException, IOException {

    	User user = UserManager.getUserById(userID);

    	if (user == null) {
			/*
			 User with passed id does not exists.
			 Return error page.
			 */
			 return;
		}

		request.setAttribute("user", user);
        dispatchTo("/pages/public/user-details.jsp");
    }
}
