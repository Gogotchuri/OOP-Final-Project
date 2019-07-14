
package controllers.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controllers.Controller;
import managers.CycleManager;
import managers.DealsManager;
import models.Cycle;
import models.Deal;
import models.User;
import servlets.RoutingConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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


    public void index() throws ServletException, IOException {
        List<Cycle> userCycles = CycleManager.getUserCycles(user.getUserID());

        if (userCycles == null) userCycles = new ArrayList<>();
        else {
            Set<Cycle> uniqueCycles = new TreeSet<>(userCycles);
            userCycles = new ArrayList<>(uniqueCycles);
        }

        request.setAttribute("cycles", userCycles);
        request.setAttribute("userID", user.getUserID());
        dispatchTo("/pages/user/deals/cycles.jsp");
    }
    /**
     * @param cycleID - ID of Cycle in DB
     * Sets Cycle object for cycle.jsp
     */
    public void show(int cycleID) throws ServletException, IOException {
        Cycle cycle = CycleManager.getCycleByCycleID(cycleID);

        if (cycle == null) {
            sendError(404, "Cycle is hasn't been found! " +
                    "(controllers.user.CyclesController:show:47)");
            return;
        }

        //We should restrict access for users who isn't involved in this cycle
        if(!CycleManager.userParticipatesInCycle(user.getUserID(), cycleID)){
            sendError(401, "Cycle doesn't belong to you! " +
                    "(controllers.user.CyclesController:show:53)");
            return;
        }

        Gson gson = new Gson();
        List<Deal> user_deals = cycle.getUserDeals(user.getUserID());
        List<Integer> user_deal_ids = new ArrayList<>(user_deals.size());

        for (Deal d : user_deals)
            user_deal_ids.add(d.getDealID());

        request.setAttribute("user_deal_ids_json", gson.toJson(user_deal_ids));
        request.setAttribute("cycle", cycle);
        request.setAttribute("is_accepted", CycleManager.isOfferedCycleAccepted(cycleID, user.getUserID()));
        dispatchTo("/pages/user/deals/cycle.jsp");
    }


    /**
     * @param dealID - ID of Deal, which Cycles we want
     * return - Suggested Cycles for given Deal
     */
    public void dealCycles(int dealID) throws ServletException, IOException {
        Deal deal = DealsManager.getDealByDealID(dealID);
        if(deal == null){
            sendError(404, "Deal doesn't exist");
            return;
        }
        if(deal.getOwnerID() != user.getUserID()){
            sendError(401, "Deal doesn't belong to you. Hence, no cycles for you!");
        }

        List<Cycle> cycles = CycleManager.getCyclesByDealID(dealID);
        if (cycles == null) {
            sendError(500, "cycles == null");
            return;
        }
        request.setAttribute("cycles", cycles);
        dispatchTo("/pages/user/deals/deal-cycles.jsp");
    }


    /**
     * Accepts
     * @param cycleID - ID of Cycle in DB
     * Accepts cycle
     */
    public void acceptCycle(int cycleID, int dealID) throws IOException {
        if(!CycleManager.userParticipatesInCycle(user.getUserID(), cycleID)){
            sendApiError(401, "Cycle doesn't belong to you! " +
                    "(controllers.user.CyclesController:acceptCycle:105)");
            return;
        }

        if(!CycleManager.acceptCycle(cycleID, dealID)){
            sendApiError(404, "Cycle couldn't be accepted " +
                    "(controllers.user.CyclesController:acceptCycle:115)");
            return;
        }

        //After successful acceptance redirect to all cycles page
        JsonObject jo = new JsonObject();
        jo.addProperty("message", "Cycle Accepted!");
        sendJson(201, jo);

    }


    /**
     * @param cycleID - ID of Cycle in DB
     * Deletes Cycle
     */
    public void rejectCycle(int cycleID) throws IOException {
        if(!CycleManager.userParticipatesInCycle(user.getUserID(), cycleID)){
            sendError(401, "Cycle doesn't belong to you! " +
                    "(controllers.user.CyclesController:acceptCycle:126)");
            return;
        }

        if(!CycleManager.deleteCycle(cycleID)){
            sendError(404, "Cycle couldn't be accepted " +
                    "(controllers.user.CyclesController:acceptCycle:132)");
            return;
        }


        //After successful acceptance redirect to all cycles page
        redirectTo(RoutingConstants.USER_DEALS);
    }

}
