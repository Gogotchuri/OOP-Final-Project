package controllers;

import com.google.gson.JsonObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Base Controller class to be used as a base of every controller
 * with wrapper method for convenience.
 */
public abstract class Controller {
    protected HttpServletResponse response;
    protected HttpServletRequest request;
    protected HttpServlet servlet;
    protected ServletContext context;
    protected HttpSession session;

    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     * @param req
     * @param res
     * @param servlet
     */
    public Controller(HttpServletRequest req, HttpServletResponse res, HttpServlet servlet){
        this.response = res;
        this.request = req;
        this.servlet = servlet;
        this.context = servlet.getServletContext();
        this.session = req.getSession();
    }

    /**
     * Method Forwards request to different resource
     * @param uri
     * @throws ServletException, IOException
     */
    protected void dispatchTo(String uri) throws ServletException, IOException {
        request.getRequestDispatcher(uri).forward(request, response);
    }

    /**
     * Redirects to given uri, appended to base context path
     * @param uri
     * @throws IOException
     */
    protected void redirectTo(String uri) throws IOException {
        response.sendRedirect(context.getContextPath() + uri);
    }

    /**
     * Method writes ext to output writer
     * @param text
     * @throws IOException
     */
    protected void outputToWriter(String text) throws IOException {
        response.getWriter().print(text);
    }

    protected void sendError(int status_code, String message) throws IOException {
        message = (message == null || message.isEmpty()) ? "Something Went wrong! Error message not formatted." : message;
        response.sendError(status_code, message);
    }

    protected void sendApiError(int status_code, String error_msg) throws IOException
    {
        JsonObject jo = new JsonObject();
        jo.addProperty("error", error_msg);
        sendJson(status_code, jo);
    }

    protected void sendJson(int status_code, JsonObject jo) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setStatus(status_code);
        pw.print(jo.toString());
        pw.flush();
    }
}
