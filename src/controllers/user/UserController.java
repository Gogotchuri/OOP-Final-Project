package controllers.user;

import controllers.Controller;
import managers.UserManager;
import models.User;
import services.RequestValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class UserController extends Controller {
    private User user;
    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     *
     * @param req
     * @param res
     * @param servlet
     */
    public UserController(HttpServletRequest req, HttpServletResponse res, HttpServlet servlet) throws ServletException {
        super(req, res, servlet);
        user = (User) session.getAttribute("user");
        if(user == null) throw new ServletException("Unauthorized user!");
    }

    public void editForm() throws ServletException, IOException {
        request.setAttribute("user", user);
        dispatchTo("/pages/user/edit-profile.jsp");
    }

    public void update() throws ServletException, IOException {
        //Validate and update
        Map<String, List<String>> rules = new HashMap<>();
        //Validation rules
        rules.put("email", Arrays.asList("required", "type:email"));
        rules.put("password", Arrays.asList("required", "min_len:8", "max_len:128"));
        rules.put("phone_number", Arrays.asList("type:number", "min_len:9", "max_len:32"));
        RequestValidator validator = new RequestValidator(request, rules);
        if(validator.failed()){
            request.setAttribute("errors", validator.getErrors());
            editForm();
            return;
        }

        List<String> errors = new ArrayList<>();

        user.setRawPassword(request.getParameter("password"));
        String newEmail = request.getParameter("email");
        if(UserManager.getUserByEmail(newEmail) != null){
            errors.add("Email is already taken");
            request.setAttribute("errors", errors);
            editForm();
            return;
        }

        user.setEmail(newEmail);
        if(!UserManager.updateExistingUser(user)){
            sendError(500, "Something went wrong during user update!");
            return;
        }

        editForm();

    }


}
