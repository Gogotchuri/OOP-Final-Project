package controllers;

import java.io.IOException;

public interface ApiResourceController {
    /**
     * Displays Collection view of the resource
     * i.e. all deals
     * Should be called after request to resource base path
     * @throws IOException cause by PrintWriter
     */
    void index() throws IOException;

    /**
     * Displays view of the Entity for the given resource
     * i.e. single deal page with a given id.
     * @throws IOException cause by PrintWriter
     */
    void show(int id) throws IOException;

    /**
     * Stores entry in database
     * @throws IOException cause by PrintWriter
     * */
    void store() throws IOException;

    /**
     * Makes changes to the resource with given id
     * @param id of the resource
     * @throws IOException cause by PrintWriter
     */
    void update(int id) throws IOException;

    /**
     * Destroys resource with given id
     * @param id of the resource
     * @throws IOException cause by PrintWriter
     */
    void destroy(int id) throws IOException;
}
