package controllers.user;

import controllers.Controller;
import controllers.ResourceController;
import managers.DealsManager;
import models.Deal;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DealsController extends Controller implements ResourceController {
    private User user;
    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     *
     * @param req
     * @param res
     * @param servlet
     */
    public DealsController(HttpServletRequest req, HttpServletResponse res, HttpServlet servlet) throws ServletException, IOException {
        super(req, res, servlet);
        user = (User) session.getAttribute("user");
        if(user == null) throw new ServletException("Unauthorized user!");
    }

    /**
     * Displays Collection view of the resource
     * i.e. Deals page.
     * Should be called after request to resource base path
     *
     * @throws IOException
     * @throws ServletException
     */
    public void index() throws IOException, ServletException {
        List<Deal> deals = DealsManager.getUserDeals(user.getId());
    }

    /**
     * Displays view of the Entity for the given resource
     * i.e. single deal page with a given id.
     *
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    public void show(int id) throws IOException, ServletException {

    }

    /**
     * return Creation form page
     *
     * @throws IOException
     * @throws ServletException
     */
    public void create() throws IOException, ServletException {

    }

    /**
     * Stores entry in database
     */
    public void store() throws IOException, ServletException {

    }


    /**
     * Makes changes to the resource with given id
     *
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    public void update(int id) throws IOException, ServletException {

    }

    /**
     * Displays a form for changing resource with given id
     *
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    public void edit(int id) throws IOException, ServletException {

    }

    /**
     * Destroys resource with given id
     *
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    public void destroy(int id) throws IOException, ServletException {

    }
}
