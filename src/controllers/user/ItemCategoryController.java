package controllers.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controllers.ApiResourceController;
import controllers.Controller;
import managers.CategoryManager;
import managers.DealsManager;
import models.Deal;
import models.User;
import models.categoryModels.ItemCategory;
import services.RequestValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemCategoryController extends Controller implements ApiResourceController {

    private User user;
    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     *
     * @param req
     * @param res
     * @param servlet
     */
    public ItemCategoryController(HttpServletRequest req, HttpServletResponse res, HttpServlet servlet) throws ServletException {
        super(req, res, servlet);
        user = (User) session.getAttribute("user");
        if (user == null)
            throw new ServletException("Unauthorized user!");
    }

    /**
     * Given a parameter deal_id of the deal returns
     * all wanted categories for it
     *
     */
    @Override
    public void index() throws IOException {
        int deal_id;
        try{
            deal_id = Integer.parseInt(request.getParameter("deal_id"));
        }catch (NumberFormatException e){
            sendApiError(404, "This path should be called with parameter 'deal_id'");
            return;
        }

        if(!checkDealOwnership(deal_id)) return;

        List<ItemCategory> categories = CategoryManager.getWantedCategoriesByDealID(deal_id);
        if(categories == null){
            sendApiError(500, "Something went wrong during retrieving categories from database. " +
                    "(user.ItemCategoryController:index");
        }
        Gson gson = new Gson();
        JsonObject jo = new JsonObject();
        jo.addProperty("categories", gson.toJson(categories));
        sendJson(200, jo);
    }

    @Override
    public void show(int id) throws IOException {
        ItemCategory cat = CategoryManager.getCategoryByID(id);
        Gson gson = new Gson();
        JsonObject jo = new JsonObject();
        jo.addProperty("category", gson.toJson(cat));
        sendJson(200, jo);
    }

    @Override
    public void store() throws IOException {
        Gson gson = new Gson();
        JsonObject jo = new JsonObject();
        Map<String, List<String>> rules = new HashMap<>();
        //Validation rules
        rules.put("type_name", Arrays.asList("required", "min_len:3", "max_len:35"));
        rules.put("manufacturer_name", Arrays.asList("required", "min_len:1", "max_len:35"));
        rules.put("model_name", Arrays.asList("required", "min_len:1", "max_len:35"));
        RequestValidator validator = new RequestValidator(request, rules);
        if(validator.failed()){
            jo.addProperty("errors", gson.toJson(validator.getErrors()));
            sendJson(422, jo);
            return;
        }

        ItemCategory cat = new ItemCategory(
                request.getParameter("model_name"), request.getParameter("type_name"),
                request.getParameter("manufacturer_name"));

        cat.setId(CategoryManager.insertCategory(cat));
        if(cat.getId() < 1){
            sendApiError(500, "Something went wrong during adding an category to the database! (user.ItemController:store)");
            return;
        }

        jo.addProperty("category", gson.toJson(cat));
        sendJson(201, jo);
    }

    @Override
    public void update(int id) throws IOException {
        //Not really needed yet
    }

    /**Removes wanted category from the item*/
    @Override
    public void destroy(int id) throws IOException {
        int deal_id;
        try{
            deal_id = Integer.parseInt(request.getParameter("deal_id"));
        }catch (NumberFormatException e){
            sendApiError(404, "This path should be called with parameter 'deal_id'");
            return;
        }

        if(!checkDealOwnership(deal_id)) return;

        Deal deal = DealsManager.getDealByDealID(deal_id);
        List<ItemCategory> wantedList = deal.getWantedCategories();
        if(wantedList != null){
            int bla = -69; //TODO if deal contains wanted item/category delete from it, need method in DealController
        }
    }

    private boolean checkDealOwnership(int deal_id) throws IOException {
        Deal deal = DealsManager.getDealByDealID(deal_id);
        if(deal == null){
            sendApiError(404, "No Deal found with the given id!");
            return false;
        }

        if(deal.getOwnerID() != user.getUserID()){
            sendApiError(401, "No Deal with the given id belongs to you!");
            return false;
        }

        return true;
    }
}
