
package servlets.user;

import controllers.user.ChatsController;
import middlewares.AuthenticatedUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/user/chats/show"})
public class ChatServlet extends HttpServlet{

	/**
	 Returns one of chats of the user

     returned html main components:
     1) chat view
     2) link to the user.ChatServlet (POST) (send message button)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {
		//Checking if user is authorized
		if((new AuthenticatedUser(request, response)).unauthenticated()) return;

		int id;
		try { id = Integer.parseInt(request.getParameter("id")); }
		catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"This address should be called, with numeric parameter \"id\"!");
			return;
		}

		(new ChatsController(request, response, this)).show(id);
	}
}
