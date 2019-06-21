
package servlets.user;

import controllers.user.ChatsController;
import middlewares.AuthenticatedUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/user/chats"})
public class ChatsServlet extends HttpServlet {

	/**
	 returned html main components:
     1) list of the links to the user.ChatServlet (GET) (some chat)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {

		//Checking if user is authorized
		if((new AuthenticatedUser(request, response)).unauthenticated()) return;

		(new ChatsController(request, response, this)).index();
	}
}
