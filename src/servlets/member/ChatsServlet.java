
package servlets.member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/members/member/chats"})
public class ChatsServlet extends HttpServlet {

	/**
	 returned html main components:
     1) list of the links to the member.ChatServlet (GET) (some chat)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {


	}
}
