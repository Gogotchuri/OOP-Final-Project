
package controllers.user;

import controllers.Controller;
import managers.CycleManager;
import models.Cycle;
import models.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CyclesController extends Controller {

    private User user;

    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     *
     * @param request request sent by user
     * @param response servlet response
     * @param servlet servlet calling this controller
     */
    public CyclesController(HttpServletRequest request,
                             HttpServletResponse response,
                              HttpServlet servlet)
        throws ServletException {
        super(request, response, servlet);
        user = (User) session.getAttribute("user");
        if (user == null)
            throw new ServletException("Unauthorized user!");
    }


    /**
     * @param cycleID - ID of Cycle in DB
     * Sets Cycle object for cycle.jsp
     */
    public void show(int cycleID) throws ServletException, IOException {
        Cycle cycle = CycleManager.getCycleByCycleID(cycleID);
        if (cycle == null) {
            sendError(500, "cycle == null");
            return;
        }
        request.setAttribute("cycle", cycle);
        dispatchTo("/pages/user/deals/cycle.jsp");
    }


    /**
     * @param dealID - ID of Deal, which Cycles we want
     * return - Suggested Cycles for given Deal
     */
    public void dealCycles(int dealID) throws ServletException, IOException {
        List<Cycle> cycles = CycleManager.getCyclesByDealID(dealID);
        if (cycles == null) {
            sendError(500, "cycles == null");
            return;
        }
        request.setAttribute("cycles", cycles);
        dispatchTo("/pages/user/deals/deal-cycles.jsp");
    }


    /**
     * @param cycleID - ID of Cycle in DB
     * Accepts cycle
     */
    public boolean acceptCycle(int cycleID, int dealID) {
        return CycleManager.acceptCycle(cycleID, dealID);
    }


    /**
     * @param cycleID - ID of Cycle in DB
     * Deletes Cycle
     */
    public boolean rejectCycle(int cycleID){
        return CycleManager.deleteCycle(cycleID);
    }

}
