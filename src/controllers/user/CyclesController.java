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
     * @param req
     * @param res
     * @param servlet
     */
    public CyclesController(HttpServletRequest req, HttpServletResponse res, HttpServlet servlet) throws ServletException {
        super(req, res, servlet);
        user = (User) session.getAttribute("user");
        if(user == null) throw new ServletException("Unauthorized user!");
    }

    public void show(int cycle_id) throws ServletException, IOException {
        //TODO if cycle not found send error
        Cycle cycle = CycleManager.getCycleByID(cycle_id);
        request.setAttribute("cycle", cycle);
        dispatchTo("/pages/user/deals/cycle.jsp");
    }
    /**
     * Return suggested cycles for given deal
     * @param deal_id
     */
    public void dealCycles(int deal_id) throws ServletException, IOException {
        //TODO if cycle not found send error

        List<Cycle> cycles = CycleManager.getCyclesByDealID(deal_id);
        request.setAttribute("cycles", cycles);
        dispatchTo("/pages/user/deals/deal-cycles.jsp");
    }

    public void acceptCycle(int cycle_id){
        //TODO implement accepting logic
    }

    public void rejectCycle(int cycle_id){
        CycleManager.deleteCycle(cycle_id);
    }
}
