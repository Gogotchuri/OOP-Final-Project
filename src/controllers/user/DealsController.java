
package controllers.user;

import controllers.Controller;
import controllers.ResourceController;
import events.DealCyclesFinder;
import managers.DealsManager;
import managers.ItemManager;
import models.Category;
import models.Deal;
import models.Item;
import models.User;
import models.categoryModels.ItemCategory;
import services.RequestValidator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class DealsController extends Controller implements ResourceController {

    private User user;

    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     *
     * @param request request sent by user
     * @param response servlet response
     * @param servlet servlet calling this controller
     */
    public DealsController(HttpServletRequest request,
                            HttpServletResponse response,
                             HttpServlet servlet)
        throws ServletException {
        super(request, response, servlet);
        user = (User) session.getAttribute("user");
        if (user == null)
            throw new ServletException("Unauthorized user!");
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
        Deal deal = DealsManager.getDealByID(id);
        if (!checkOwnership(deal)) return;
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
     Stores entry in database
     */
    public void store() throws IOException, ServletException {

        Map<String, List<String>> rules = new HashMap<>();
        rules.put("item_id", Arrays.asList("type:numeric", "min:1"));
        rules.put("wanted_id", Arrays.asList("type:numeric", "min:1"));
        RequestValidator validator = new RequestValidator(request, rules);

        if (validator.failed()) {
            request.setAttribute("errors", validator.getErrors());
            List<String> errors = new ArrayList<>();
            errors.add("Passed parameters are invalid!");
            request.setAttribute("errors", errors);
            create();
            return;
        }


        List<Integer> ownedIDs = getIntegerListOf("item_id"),
                        wantedIDs = getIntegerListOf("wanted_id");


        List<Item> ownedItems = null; // ItemManager.getItemsByIDs(ownedIDs);
        List<ItemCategory> wantedCategories = null; // CategoryManager.getCategoriesByIDs(wantedIDs);


        Deal deal = new Deal(ownedItems, wantedCategories);
        deal.setDealID(DealsManager.storeDeal(deal));

        int dealID = deal.getDealID();

        if (dealID == -1) { // Deal did not insert into DB
            sendError(500, "Internal server error! \n" +
                "While Inserting new deal to database, 0 was returned as value of deal id" +
                "(User.DealsController:102)");
            return;
        }

        show(dealID);

        // Starts new thread for finding cycles
        new DealCyclesFinder(deal).start();
    }

    /**
     Returns List of Integers
     !!! (with assuming that parameters are numeric type) !!!
     of passed parameter with key 'paramKey'
     */
    private List<Integer> getIntegerListOf(String paramKey) {
        List<Integer> list = new ArrayList<>();
        String[] values = request.getParameterValues(paramKey);
        for (String value : values)
            list.add(Integer.parseInt(value));
        return list;
    }

    /**
     * !!! NOT IMPLEMENTED !!!
     * Makes changes to the resource with given id
     *
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    public void update(int id) throws IOException, ServletException {
        /*
        Deal deal = DealsManager.getDealById(id);
        if(!checkOwnership(deal)) return;
        if(DealsManager.updateDeal(deal)) show(deal.getId());
        else sendError(500,
                "Error occurred while updating a deal. DealsManager.updateDeal returned false! (user.DealsController:100)");
        */
    }

    /**
     * !!! NOT IMPLEMENTED !!!
     * Displays a form for changing resource with given id
     *
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    public void edit(int id) throws IOException, ServletException {
        /*
        Deal deal = DealsManager.getDealById(id);
        if(!checkOwnership(deal)) return;
        request.setAttribute("deal", deal);
        dispatchTo("/pages/user/deals/edit-deal.jsp");
         */
    }

    /**
     * Destroys resource with given id
     *
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    public void destroy(int id) throws IOException, ServletException {
        Deal deal = DealsManager.getDealByID(id);
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
        if(deal.getOwner().getId() != this.user.getId()){
            sendError(401, "Not authorized to edit this deal!");
            return false;
        }

        return true;
    }
}
