package controllers.user;

import controllers.Controller;
import controllers.ResourceController;
import managers.DealsManager;
import models.Deal;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DealsController extends Controller implements ResourceController {
    private User user;
    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     *
     * @param req request sent by user
     * @param res servlet response
     * @param servlet servlet calling this controller
     */
    public DealsController(HttpServletRequest req, HttpServletResponse res, HttpServlet servlet) throws ServletException, IOException {
        super(req, res, servlet);
        user = (User) session.getAttribute("user");
        if(user == null) throw new ServletException("Unauthorized user!");
    }

    /**
     * Displays Collection view of the resource
     * i.e. Deals page.
     * Should be called after request to resource base path
     *
     * @throws IOException caused by dispatching
     * @throws ServletException caused by dispatching
     */
    public void index() throws IOException, ServletException {
        List<Deal> deals = DealsManager.getUserDeals(user);
        request.setAttribute("deals", deals);
        dispatchTo("/pages/user/deals/deals.jsp");
    }

    /**
     * Displays view of the Entity for the given resource
     * i.e. single deal page with a given id.
     *
     * @param id deal id
     * @throws IOException
     * @throws ServletException
     */
    public void show(int id) throws IOException, ServletException {
        Deal deal = DealsManager.getDealById(id);
        if(!checkOwnership(deal)) return;
        request.setAttribute("deal", deal);
        dispatchTo("/pages/user/deals/deal.jsp");
    }

    /**
     * return Creation form page
     *
     * @throws IOException
     * @throws ServletException
     */
    public void create() throws IOException, ServletException {
        dispatchTo("/pages/user/deals/create-deal.jsp");
    }

    /**
     * Stores entry in database
     */
    public void store() throws IOException, ServletException {
        //TODO validator! return if rule violation occurs
        List<Integer> owned_ids = null; //TODO should be assigned after parsing request
        List<Integer> wanted_ids = null; //TODO should be assigned after parsing request
        Deal deal = new Deal(user.getId(), owned_ids, wanted_ids);
        deal.setId(DealsManager.storeDeal(deal));
        //Let him see deal after a successful creation!
        if(deal.getId() != 0) show(deal.getId());
        else sendError(500, "Internal server error! \n" +
                "While Inserting new deal to database, 0 was returned as value of deal id (User.DealsController:83)");
    }


    /**
     * Makes changes to the resource with given id
     *
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    public void update(int id) throws IOException, ServletException {
        Deal deal = DealsManager.getDealById(id);
        if(!checkOwnership(deal)) return;
        //TODO Parse, validate and set deal fields!
        if(DealsManager.updateDeal(deal)) show(deal.getId());
        else sendError(500,
                "Error occurred while updating a deal. DealsManager.updateDeal returned false! (user.DealsController:100)");
    }

    /**
     * Displays a form for changing resource with given id
     *
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    public void edit(int id) throws IOException, ServletException {
        Deal deal = DealsManager.getDealById(id);
        if(!checkOwnership(deal)) return;
        request.setAttribute("deal", deal);
        dispatchTo("/pages/user/deals/edit-deal.jsp");
    }

    /**
     * Destroys resource with given id
     *
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    public void destroy(int id) throws IOException, ServletException {
        Deal deal = DealsManager.getDealById(id);
        if(!checkOwnership(deal)) return;
        if(DealsManager.deleteDeal(id)) {
            index();
            return;
        }
        sendError(500,
                "Internal ERROR! Something went wrong while trying to delete Deal through Deal Manager!");
    }

    /**
     * Checks whether given deal belongs to current user
     * If deal is null, hence not found, returns 404
     * If deal doesn't belong to user returns 401
     * @param deal deal to check
     * @return true if deal belongs to user
     * @throws IOException caused by send error
     */
    private boolean checkOwnership(Deal deal) throws IOException {
        if(deal == null){
            sendError(404, "Deal not found!");
            return false;
        }
        if(deal.getUser_id() != this.user.getId()){
            sendError(401, "Not authorized to edit this deal!");
            return false;
        }

        return true;
    }
}
