package controllers.front;

import controllers.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexController extends Controller {


    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     *
     * @param req
     * @param res
     * @param servlet
     */
    public IndexController(HttpServletRequest req, HttpServletResponse res, HttpServlet servlet) {
        super(req, res, servlet);
    }

    public void index() throws IOException, ServletException {
        outputToWriter("Hi this is a index page, message is sent from index controller!");
    }
}
