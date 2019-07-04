
package servlets.front;

import controllers.front.AuthController;
import services.RequestValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/register"})
public class RegistrationServlet extends HttpServlet {

	/**
     returned html main components:
     1) fields for registration
     2) link to the front.LoginServlet (POST) (submit new user information)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
		throws ServletException, IOException {
		new AuthController(request, response, this).registerForm();
	}


	/**
	 Checks whenever entered data satisfies registration rules.
	 If satisfies, registers guest.

     returned html:
     if satisfies:
	    dispatch to front.HomeServlet (GET) (home page)
	 else:
	    dispatch to front.RegistrationServlet (GET) (registration form)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
						   HttpServletResponse response)
		throws ServletException, IOException {

		AuthController ac = new AuthController(request, response, this);
		Map<String, List<String>> rules = new HashMap<>();
		//Validation rules
		rules.put("email", Arrays.asList("required", "type:email"));
		rules.put("username", Arrays.asList("required", "min_len:5", "max_len:40"));
		rules.put("password", Arrays.asList("required", "min_len:8", "max_len:128"));
		rules.put("first_name", Arrays.asList("min_len:1", "max_len:128"));
		rules.put("last_name", Arrays.asList("min_len:1", "max_len:128"));
		rules.put("phone_number", Arrays.asList("type:number", "min_len:9", "max_len:32"));


		RequestValidator validator = new RequestValidator(request, rules);
		if(validator.failed()){
			request.setAttribute("errors", validator.getErrors());
			ac.registerForm();
			return;
		}

		String
			username = request.getParameter("username"),
			password = request.getParameter("password"),
			email = request.getParameter("email"),
			firstName = request.getParameter("first_name"),
			lastName = request.getParameter("last_name"),
			phoneNumber = request.getParameter("phone_number");

		ac.register (
			username, password, email, firstName, lastName, phoneNumber
		);
	}
}
