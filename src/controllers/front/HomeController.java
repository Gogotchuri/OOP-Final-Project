
package controllers.front;

import controllers.Controller;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeController extends Controller {

    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     */
    public HomeController(HttpServletRequest request,
    					   HttpServletResponse response,
    					    HttpServlet servlet) {
        super(request, response, servlet);
    }


    public void index() throws IOException, ServletException {
        outputToWriter("Hello from controller!");
    }
}
