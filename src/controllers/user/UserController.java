package controllers.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

    /**
     * Updates local user information
     * returns response as a json object
     */
    public void update() throws ServletException, IOException {
        Gson gson = new Gson();
        //Validate and update
        Map<String, List<String>> rules = new HashMap<>();
        //Validation rules
        rules.put("email", Arrays.asList("type:email"));
        rules.put("password", Arrays.asList("min_len:8", "max_len:128"));
        rules.put("phone_number", Arrays.asList("type:number", "min_len:9", "max_len:32"));
        RequestValidator validator = new RequestValidator(request, rules);
        if(validator.failed()){
            JsonObject jo = new JsonObject();
            jo.addProperty("errors", gson.toJson(validator.getErrors()));
            sendJson(422, jo);
            return;
        }

        String newPassword = request.getParameter("password");

        if(newPassword != null && !newPassword.isEmpty())
            user.setRawPassword(request.getParameter("password"));

        String newEmail = request.getParameter("email");
        if(newEmail != null && !newEmail.isEmpty() &&
                !user.getEmail().equalsIgnoreCase(newEmail) && UserManager.getUserByEmail(newEmail) != null){
            List<String> errors = new ArrayList<>();
            errors.add("Email is already taken");
            JsonObject jo = new JsonObject();
            jo.addProperty("errors", gson.toJson(errors));
            sendJson(422, jo);
            return;
        }

        if(newEmail != null && !newEmail.isEmpty())
            user.setEmail(newEmail);

        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String phoneNumber = request.getParameter("phone_number");

        if(firstName != null && !firstName.isEmpty())
            user.setFirstName(firstName);
        if(lastName != null && !lastName.isEmpty())
            user.setLastName(lastName);
        if(phoneNumber != null && !phoneNumber.isEmpty())
            user.setPhoneNumber(phoneNumber);


        if(!UserManager.updateExistingUser(user)){
            sendApiError(500, "Something went wrong during user update!");
            return;
        }
        JsonObject jo = new JsonObject();
        jo.addProperty("message", "User parameters updated");
        jo.addProperty("user", gson.toJson(user));
        sendJson(201, jo);
    }


}
