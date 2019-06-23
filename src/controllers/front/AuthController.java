
package controllers.front;

import controllers.Controller;
import managers.UserManager;
import models.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthController extends Controller {

    /**
     Creates a controller, usually called from servlet, which is also
     passed by parameter. Servlet method passes taken request and response.
     */
    public AuthController(HttpServletRequest request,
                           HttpServletResponse response,
                            HttpServlet servlet) {
        super(request, response, servlet);
    }

    /**
     Returns login form page.
     */
    public void loginForm() throws ServletException, IOException {
        dispatchTo("/pages/public/login.jsp");
    }

    /**
     Returns register form page.
     */
    public void registerForm() throws ServletException, IOException {
        dispatchTo("/pages/public/register.jsp");
    }

    /**
     If authorization was a success writes user model in a session and
     passes home page to authorized user.
     Otherwise returns login page with errors.
     */
    public void login(String username, String password)
        throws ServletException, IOException {

        User user = UserManager.getUserByUsername(username);
        List<String> errors = new ArrayList<>();

        if (user == null)
            errors.add("User with given username doesn't exist!");
        else if (!user.checkPassword(password))
            errors.add("Password doesn't match!");

        if (errors.isEmpty()) {
            session.setAttribute("user", user);
            redirectTo("/home");
            return;
        }

        request.setAttribute("errors", errors);
        loginForm();
    }

    /**
     If registration was a success passes login page to registered user,
     with success error message.
     Otherwise returns login page with errors.
     */
    public void register(String username, String password, String email,
                          String firstName, String lastName, String phoneNumber)
        throws ServletException, IOException {

        ArrayList<String> errors = new ArrayList<>();
        String encryptedPassword = User.encryptPassword(password);

        User user = new User (
            username, encryptedPassword,
             firstName, lastName, email, phoneNumber
        );

        if (UserManager.getUserByUsername(username) != null)
            errors.add("Username isn't unique!");
        else if(!UserManager.storeUser(user)){
            sendError(500, "Something went wrong during storing the user in database. (AuthController:87)");
            return;
        }
        //TODO add email uniqueness logic. Requires: getUserByEmail in UserManager

        if (errors.isEmpty()) {
            request.setAttribute("success", "You have registered successfully!");
            redirectTo("/login");
            return;
        }

        request.setAttribute("errors", errors);
        registerForm();
    }

    /**
     Removes user from session, which is equal of logging out and
     redirects to the home page.
     */
    public void logout() throws IOException {
        session.removeAttribute("user");
        redirectTo("/home");
    }
}
