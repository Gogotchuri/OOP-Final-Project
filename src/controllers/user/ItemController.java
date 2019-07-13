package controllers.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controllers.ApiResourceController;
import controllers.Controller;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Mainly used as an api crud class
public class ItemController extends Controller implements ApiResourceController {
    //The local user
    private User user;

    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     */
    public ItemController(HttpServletRequest req, HttpServletResponse res, HttpServlet servlet) throws ServletException {
        super(req, res, servlet);
        user = (User) session.getAttribute("user");
        if (user == null)
            throw new ServletException("Unauthorized user!");
    }

    /**
     * Return all items for given deal, json formatted
     */
    @Override
    public void index() throws IOException{
        int deal_id;
        try {
            deal_id = Integer.parseInt(request.getParameter("deal_id"));
        }catch (NumberFormatException e){
            sendApiError(404, "This path should be called with parameter (positive integer) 'deal_id'");
            return;
        }

        if(!checkDealOwnedShip(deal_id)) return;

        List<Item> items = ItemManager.getItemsByDealID(deal_id);
        if(items == null){
            sendApiError(500, "Items returned as null! (user.ItemController)");
            return;
        }
        JsonObject jo = new JsonObject();
        Gson gson = new Gson();
        jo.addProperty("items", gson.toJson(items));

        sendJson(200, jo);
    }

    /**
     * Return single item with given id, json formatted
     *
     * @param id item id
     */
    @Override
    public void show(int id) throws IOException{
        if(!checkItemOwnership(id)) return;
        Item item = ItemManager.getItemByID(id);
        Gson gson = new Gson();
        JsonObject jo = new JsonObject();
        jo.addProperty("item", gson.toJson(item));
        sendJson(200, jo);
    }

    /**
     * Parses request parameters
     * Validates request parameters And stores resource in db,
     * if stored with success return json representation of the resource otherwise returns error
     */
    @Override
    public void store() throws IOException{
        System.out.println(request.getParameterNames());
        System.out.println(request.getHeaderNames());
        if(!validateRequestForStore()) return;
        Gson gson = new Gson();
        JsonObject jo = new JsonObject();

        ItemCategory cat = new ItemCategory(
                request.getParameter("series"), request.getParameter("brand"),
                request.getParameter(""));
        cat.setId(CategoryManager.insertCategory(cat));
        if(cat.getId() < 1){
            sendApiError(500, "Something went wrong during adding an category to the database! (user.ItemController:store)");
            return;
        }

        Item item = new Item(user.getUserID(), cat,
                request.getParameter("name"), request.getParameter("description"));

        if(!ItemManager.addItemToDB(item)){
            sendApiError(500, "Something went wrong during adding an item to the database! (user.ItemController:store)");
            return;
        }

        jo.addProperty("item", gson.toJson(item));
        sendJson(201, jo); //Entity created return item
    }

    /**
     * Should update a deal
     * @param id of the resource
     */
    @Override
    public void update(int id) throws IOException{
        if(!checkItemOwnership(id)) return;
        //Not needed for now

    }

    /**
     * Destroys item with given id
     * Return errors/response with json
     *
     * @param id of the item which should be destroyed
     */
    @Override
    public void destroy(int id) throws IOException{
        if(!checkItemOwnership(id)) return;
        if(!ItemManager.deleteItemById(id)) {
            sendApiError(500, "Couldn't delete item from database, internal error! (user.ItemController:destroy)");
            return;
        }

        JsonObject jo = new JsonObject();
        jo.addProperty("message", "success");
        sendJson(200, jo);
    }

    private boolean checkDealOwnedShip(int deal_id) throws IOException {
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

    private boolean checkItemOwnership(int item_id) throws IOException {
        Item item = ItemManager.getItemByID(item_id);
        if(item == null){
            sendApiError(404, "No Item found with the given id!");
            return false;
        }

        if(item.getOwnerID()!= user.getUserID()){
            sendApiError(401, "No Item with the given id belongs to you!");
            return false;
        }

        return true;
    }

    /**
     * Validates a request, intended for storing purposes
     * @return true if validation was a success otherwise send errors to client and return false;
     * @throws IOException Print Writer exception
     */
    private boolean validateRequestForStore() throws IOException {
        Gson gson = new Gson();
        JsonObject jo = new JsonObject();

        Map<String, List<String>> rules = new HashMap<>();
        //Validation rules
        rules.put("name", Arrays.asList("required", "min_len:3", "max_len:35"));
        rules.put("description", Arrays.asList("required", "min_len:10", "max_len:250"));
        rules.put("type_name", Arrays.asList("required", "min_len:3", "max_len:35"));
        rules.put("manufacturer_name", Arrays.asList("required", "min_len:3", "max_len:35"));
        rules.put("model_name", Arrays.asList("required", "min_len:3", "max_len:35"));

        RequestValidator validator = new RequestValidator(request, rules);
        if(validator.failed()){
            List<String> errors = validator.getErrors();
            jo.addProperty("errors", gson.toJson(errors));
            sendJson(422,jo);
            return false;
        }
        return true;
    }

}
