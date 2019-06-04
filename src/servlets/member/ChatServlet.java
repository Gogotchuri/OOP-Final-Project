
package servlets.member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/members/member/chats/chat"})
public class ChatServlet extends HttpServlet{

	/**
	 Returns one of the chats of the user member

     returned html main components:
     1) chat view
     2) link to the member.CharServlet (PUT) (send message button)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {


	}


	/**
	 Inserts new message in some chat

	 returned html:
	 dispatch to member.CharServlet (GET) (chat where message was put)
	 */
	@Override
	protected void doPut(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {


	}
}
