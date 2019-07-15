package middlewares;

import models.User;
import servlets.RoutingConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticatedUser{
    protected HttpServletResponse response;
    protected HttpServletRequest request;

    public AuthenticatedUser(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    public boolean check() throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            response.sendRedirect(request.getContextPath()+ RoutingConstants.LOGIN);
            return false;
        }
        return true;
    }

    public boolean authenticated() throws IOException {
        return check();
    }


    public boolean unauthenticated() throws IOException {
        return !check();
    }
}
