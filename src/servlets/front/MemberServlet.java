
package servlets.front;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/members/member"})
public class MemberServlet extends HttpServlet {

	/**
     returned html main components:
     1) Some information about member
     2) link to the front.DealsServlet (GET) (only this member deals)

     If profile of this user:
     3) link to the member.ChatsServlet (GET) (user member chats)
     4) link to the member.MemberConfigServlet (GET) (configuration form)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {


	}
}
