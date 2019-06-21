
package servlets.front;

import controllers.front.DealsController;
import controllers.front.DealsController.SearchCriteria;
import controllers.front.DealsController.SearchCriteria.Criteria;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;

@WebServlet(urlPatterns = {"/deals"})
public class DealsServlet extends HttpServlet {

    /**
     Finds collection of the deals with some criteria.

     returned html main components:
     1) list of the links to the front.DealServlet (GET) (view of deal),
        found by some criteria.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /*
         Initialize criteria parameters passed
         by 'request' object for indexing deals.
         */
        SearchCriteria sc = new SearchCriteria();


        Iterator<String> criteria = request.getParameterNames().asIterator();

        while (criteria.hasNext()) {
            String criteriaName = criteria.next();
            Criteria aCriteria = Criteria.getCriteria(criteriaName);

            if (aCriteria == null) {
                /*TODO
                !!!მგონი არ უნდა ერორი აქ, პროსტა თუ არ გამოატანს ვიკიდებთ კრიტერიუმს
                    და მაგის გარეშე გავფილტროთ. ვაფშე რომ არ გამოატანოს ეგეც მოსული უნდა იყოს
                    ვინაიდან ტიპი რომ შემოვა პირდაპირ მაგ ლინკზე, ყველა გამოქვეყნებული დიალი უნდა მიიღოს!!!
                 Such Criteria does not exists.
                 Return error page.
                 */
                return;
            }

            sc.addCriteria(aCriteria, request.getParameter(criteriaName));
        }

        new DealsController(request, response, this).index(sc);
    }
}
