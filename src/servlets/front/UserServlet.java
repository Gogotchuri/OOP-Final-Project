
package servlets.front;

import controllers.front.UserController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/users/show"})
public class UserServlet extends HttpServlet {

	/**
     returned html main components:
     1) Some information about user
     2) link to the front.DealsServlet (GET) (only this user deals)

     If profile of this user:
     3) link to the user.ChatsServlet (GET) (user's chats)
     4) link to the user.UserConfigServlet (GET) (user configuration form)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {

        int userID;
        try { userID = Integer.parseInt(request.getParameter("id")); }
        catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"This address should be called with parameter \"id\"!");
             return;
        }

		new UserController(request, response, this).show(userID);
	}
}
