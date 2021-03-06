
package controllers.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import controllers.Controller;
import controllers.ResourceController;
import events.DealCyclesFinder;
import managers.CategoryManager;
import managers.DealsManager;
import managers.ItemManager;
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
        List<Deal> deals = DealsManager.getDealsByUserID(user.getUserID());
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
        Deal deal = DealsManager.getDealByDealID(id);
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
        Gson gson = new Gson();
        JsonObject jo = new JsonObject();
        Map<String, List<String>> rules = new HashMap<>();
        rules.put("name", Arrays.asList("required", "min_len:5", "max_len:35"));
        rules.put("description", Arrays.asList("required", "min_len:10", "max_len:250"));
        rules.put("wanted_ids", Arrays.asList("required", "type:array"));
        rules.put("owned_ids", Arrays.asList("required", "type:array"));
        RequestValidator validator = new RequestValidator(request, rules);

        if (validator.failed()) {
            jo.addProperty("errors", gson.toJson(validator.getErrors()));
            sendJson(422, jo);
            return;
        }

        List<Integer> ownedIDs = gson.fromJson(request.getParameter("owned_ids"), new TypeToken<List<Integer>>(){}.getType());
        List<Integer> wantedIDs = gson.fromJson(request.getParameter("wanted_ids"), new TypeToken<List<Integer>>(){}.getType());


        List<Item> ownedItems = ItemManager.getItemsByItemIDs(ownedIDs);
        if (ownedItems == null) {
            sendApiError(500, "Something went wring wrong during getting items(user.DealsController:store)");
            return;
        }

        List<ItemCategory> wantedCategories = CategoryManager.getItemCategoriesByItemCategoryIDs(wantedIDs);
        if (wantedCategories == null) {
            sendApiError(500, "Something went wring wrong during getting categories(user.DealsController:store)");
            return;
        }

        Deal deal = new Deal(user.getUserID(), ownedItems, wantedCategories,
                request.getParameter("name"), request.getParameter("description"));
        deal.setDealID(DealsManager.storeDeal(deal));
        if(deal.getDealID() < 1){
            sendApiError(500, "Deal couldn't be saved! (user.DealsController:store)");
            return;
        }

        jo.addProperty("message", "deal saved!");
        jo.addProperty("deal", gson.toJson(deal));
        sendJson(201, jo);
        // Starts new thread for finding cycles
        new DealCyclesFinder(deal).start();
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
        Deal deal = DealsManager.getDealByDealID(id);
        if(!checkOwnership(deal)) return;
        if(!DealsManager.deleteDeal(id)) {
            sendApiError(500, "Internal ERROR! Something went wrong while trying to delete Deal through Deal Manager!");
            return;
        }
        JsonObject jo = new JsonObject();
        jo.addProperty("message", "success");
        sendJson(201, jo);
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
        if(deal.getOwnerID() != this.user.getUserID()){
            sendError(401, "Not authorized to edit this deal!");
            return false;
        }

        return true;
    }
}
