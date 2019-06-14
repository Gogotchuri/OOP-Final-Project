
package servlets.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/users/chats/"})
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


	}
}
