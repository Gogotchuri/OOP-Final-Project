package controllers;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Should be implemented by controller
 * which control specific resource. i.e. Deals
 * Base path convention: /resource -> /resource/{id}
 */
public interface ResourceController {
    /**
     * Displays Collection view of the resource
     * i.e. Deals page.
     * Should be called after request to resource base path
     * @throws IOException
     * @throws ServletException
     */
    void index() throws IOException, ServletException;

    /**
     * Displays view of the Entity for the given resource
     * i.e. single deal page with a given id.
     * @throws IOException
     * @throws ServletException
     */
    void show(int id) throws IOException, ServletException;

    /**
     * return Creation form page
     * @throws IOException
     * @throws ServletException
     */
    void create() throws IOException, ServletException;

    /**
     * Stores entry in database
     * */
    void store() throws IOException, ServletException;

    /**
     * Makes changes to the resource with given id
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    void update(int id) throws IOException, ServletException;


    /**
     * Displays a form for changing resource with given id
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    void edit(int id) throws IOException, ServletException;

    /**
     * Destroys resource with given id
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    void destroy(int id) throws IOException, ServletException;
}
