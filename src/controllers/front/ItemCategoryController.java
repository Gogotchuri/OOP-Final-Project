package controllers.front;

import controllers.Controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ItemCategoryController extends Controller {
    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     *
     * @param req
     * @param res
     * @param servlet
     */
    public ItemCategoryController(HttpServletRequest req, HttpServletResponse res, HttpServlet servlet) {
        super(req, res, servlet);
    }

    public void index() {
        //TODO send types and brands as json array
    }
}
