package controllers.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controllers.Controller;
import controllers.ResourceController;
import managers.DealsManager;
import managers.ItemManager;
import models.Deal;
import models.Item;
import models.User;
import services.RequestValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Mainly used as an api crud class
public class ItemController extends Controller implements ResourceController {
    //The local user
    private User user;

    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     *
     * @param req
     * @param res
     * @param servlet
     */
    public ItemController(HttpServletRequest req, HttpServletResponse res, HttpServlet servlet) throws ServletException {
        super(req, res, servlet);
        user = (User) session.getAttribute("user");
        if (user == null)
            throw new ServletException("Unauthorized user!");
    }

    /**
     * Return all items for given deal, json formatted
     *
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void index() throws IOException, ServletException {
        PrintWriter pw = response.getWriter();
        int deal_id;
        try {
            deal_id = Integer.parseInt(request.getParameter("deal_id"));
        }catch (NumberFormatException e){
            response.setStatus(404);
            pw.println("{\"error\":\"This path should be called with parameter (positive integer) 'deal_id'\"}");
            pw.flush();
            return;
        }

        if(!checkDealOwnedShip(deal_id)) return;

        List<Item> items = ItemManager.getItemsByDealID(deal_id);
        JsonObject jo = new JsonObject();
        Gson gson = new Gson();
        jo.addProperty("items", gson.toJson(items));
        pw.print(jo.toString());
        pw.flush();
    }

    /**
     * Return single item with given id, json formatted
     *
     * @param id item id
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void show(int id) throws IOException, ServletException {
        if(!checkItemOwnedShip(id)) return;
        Item item = ItemManager.getItemByID(id);
        Gson gson = new Gson();
        JsonObject jo = new JsonObject();
        jo.addProperty("item", gson.toJson(item));
        PrintWriter pw = response.getWriter();
        pw.print(jo.toString());
        pw.flush();
    }

    /**
     * This method usually returns create form for the resource
     * No need to return create form while using through api
     *
     * @throws IOException
     * @throws ServletException
     */
    @Override
    @Deprecated
    public void create() throws IOException, ServletException {}

    /**
     * Parses request parameters
     * Validates request parameters
     * And stores resource in db,
     * if stored with success return json representation of the resource
     * otherwise returns error
     *
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void store() throws IOException, ServletException {
        PrintWriter pw = response.getWriter();
        Gson gson = new Gson();

        Map<String, List<String>> rules = new HashMap<>();
        //Validation rules
        rules.put("name", Arrays.asList("required", "min_len:3", "max_len:35"));
        rules.put("description", Arrays.asList("required", "min_len:10", "max_len:250"));
        rules.put("type_id", Arrays.asList("required", "type:integer", "min_num:1"));
        rules.put("manufacturer_id", Arrays.asList("required", "type:integer", "min_num:1"));
        rules.put("model_id", Arrays.asList("type:integer","min_num:1"));
        rules.put("model_name", Arrays.asList("min_len:3", "max_len:35"));

        RequestValidator validator = new RequestValidator(request, rules);
        if(validator.failed()){
            List<String> errors = validator.getErrors();
            JsonObject jo = new JsonObject();
            response.setStatus(422); //Unprocessable entity
            jo.addProperty("errors", gson.toJson(errors));
            pw.println(jo.toString());
            pw.flush();
            return;
        }

        //TODO create and store Category and Item, return stored item
        Item item = null;
        response.setStatus(201); //Entity created
        JsonObject jo = new JsonObject();
        jo.addProperty("item", gson.toJson(item));
        pw.print(jo.toString());
        pw.flush();
    }

    @Override
    public void update(int id) throws IOException, ServletException {
        if(!checkItemOwnedShip(id)) return;
        //Not needed for now

    }

    /**
     * This method usually returns edit form
     * No need for that when using api
     *
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    @Override
    @Deprecated
    public void edit(int id) throws IOException, ServletException {}

    /**
     * Destroys item with given id
     * Return errors/response with json
     *
     * @param id
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void destroy(int id) throws IOException, ServletException {
        if(!checkItemOwnedShip(id)) return;
        //TODO ItemManager need a method for deleting an item
        PrintWriter pw = response.getWriter();
        //if deleting fails:
        if(true) {
            response.setStatus(500);
            pw.print("{\"Error\":\"Couldn't delete item from database, internal error! (user.ItemController:destroy)\"}");
            pw.flush();
            return;
        }

        response.setStatus(200);
        pw.print("{\"message\":\"success\"}");
        pw.flush();
    }

    private boolean checkDealOwnedShip(int deal_id) throws IOException {
        Deal deal = DealsManager.getDealByDealID(deal_id);
        PrintWriter pw = response.getWriter();
        if(deal == null){
            response.setStatus(404);
            pw.println("{\"error\":\"No Deal found with the given id!\"}");
            pw.flush();
            return false;
        }

        if(deal.getOwnerID() != user.getUserID()){
            response.setStatus(401);
            pw.println("{\"error\":\"No Deal with the given id belongs to you!\"}");
            pw.flush();
            return false;
        }

        return true;
    }

    private boolean checkItemOwnedShip(int item_id) throws IOException {
        Item item = ItemManager.getItemByID(item_id);
        PrintWriter pw = response.getWriter();
        if(item == null){
            response.setStatus(404);
            pw.println("{\"error\":\"No Item found with the given id!\"}");
            pw.flush();
            return false;
        }

        if(item.getOwnerID()!= user.getUserID()){
            response.setStatus(401);
            pw.println("{\"error\":\"No Item with the given id belongs to you!\"}");
            pw.flush();
            return false;
        }

        return true;
    }
}
