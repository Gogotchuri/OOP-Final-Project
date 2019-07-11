package controllers.user;

import controllers.Controller;
import controllers.ResourceController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Mainly used as an api crud class
public class ItemController extends Controller implements ResourceController {
    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     *
     * @param req
     * @param res
     * @param servlet
     */
    public ItemController(HttpServletRequest req, HttpServletResponse res, HttpServlet servlet) {
        super(req, res, servlet);
    }

    /**
     * Return all items for user, json formatted
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void index() throws IOException, ServletException {

    }

    /**
     * Return single item with given id, json formatted
     * @param id item id
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void show(int id) throws IOException, ServletException {

    }

    /**
     * This method usually returns create form for the resource
     * No need to return create form while using through api
     * @throws IOException
     * @throws ServletException
     */
    @Override
    @Deprecated
    public void create() throws IOException, ServletException {
    }

    /**
     * Parses request parameters
     * Validates request parameters
     * And stores resource in db,
     * if stored with success return json representation of the resource
     * otherwise returns error
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void store() throws IOException, ServletException {

    }

    @Override
    public void update(int id) throws IOException, ServletException {

    }

    /**
     * This method usually returns edit form
     * No need for that when using api
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    @Override
    @Deprecated
    public void edit(int id) throws IOException, ServletException {

    }

    /**
     * Destroys item with given id
     * Return errors/response with json
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void destroy(int id) throws IOException, ServletException {

    }
}
