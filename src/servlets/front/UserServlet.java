
package servlets.front;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/users/"})
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


	}
}
