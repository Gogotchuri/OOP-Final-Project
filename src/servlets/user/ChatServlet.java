
package servlets.user;

import controllers.user.ChatsController;
import middlewares.AuthenticatedUser;
import servlets.RoutingConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {RoutingConstants.USER_SINGLE_CHAT})
public class ChatServlet extends HttpServlet{

	//Checking if user is authenticated before entering any method
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if((new AuthenticatedUser(req,resp)).unauthenticated()) return;
		super.service(req, resp);
	}

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
