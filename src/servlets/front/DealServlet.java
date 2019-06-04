
package servlets.front;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/deals/deal"})
public class DealServlet extends HttpServlet {

    /**
     returned html main components:
     1) Information about some deal

     If deal of this user member:
     2) link to the member.DealConfigServlet (GET) (configuration form)
     3) link to the member.DealConfigServlet (DELETE) (delete button)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {


    }
}
