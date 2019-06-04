
package servlets.member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/members/member/configuration"})
public class MemberConfigServlet extends HttpServlet {

	/**
     returned html main components:
     1) filled fields with information of member, for updating.
     2) link to the member.MemberConfigServlet (PUT) (submit changes button)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {


	}


	/**
	 Checks whenever entered data satisfies member editing rules.
	 If satisfies, edits member information.

     returned html:
     if satisfies:
	    dispatch to front.MemberServlet (GET) (user member profile)
	 else:
	    dispatch to member.MemberConfigServlet (GET) (configuration form)
	 */
	@Override
	protected void doPut(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {


	}
}
