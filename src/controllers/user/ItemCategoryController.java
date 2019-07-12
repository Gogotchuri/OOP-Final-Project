package controllers.user;

import controllers.Controller;
import models.User;

public class ItemCategoryController extends Controller {

    private User user;
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
        user = (User) session.getAttribute("user");
        if (user == null)
            throw new ServletException("Unauthorized user!");
    }
}
