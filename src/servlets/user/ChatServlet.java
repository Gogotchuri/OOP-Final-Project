
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

		int cycle_id;
		try { cycle_id = Integer.parseInt(request.getParameter("cycle_id")); }
		catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"This address should be called, with numeric parameter \"cycle_id\"!");
			return;
		}

		(new ChatsController(request, response, this)).show(cycle_id);
	}


	/**
	 Sends new message in some chat

	 returned html:
	 dispatch to user.ChatServlet (GET) (chat where message was sent)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
						   HttpServletResponse response)
		throws ServletException, IOException {
		//Checking if user is authorized
		if((new AuthenticatedUser(request, response)).unauthenticated()) return;

		int cycle_id;
		try { cycle_id = Integer.parseInt(request.getParameter("cycle_id")); }
		catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"This address should be called, with numeric parameter \"cycle_id\"!");
			return;
		}

		(new ChatsController(request, response, this)).sendMessage(cycle_id);

	}
}
