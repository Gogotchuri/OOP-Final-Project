package controllers.front;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controllers.Controller;
import managers.CategoryManager;
import models.categoryModels.ItemBrand;
import models.categoryModels.ItemSeries;
import models.categoryModels.ItemType;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ItemCategoryController extends Controller {
    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     *
     * @param req sent request
     * @param res httpServlet response
     * @param servlet servlet on which request was sent
     */
    public ItemCategoryController(HttpServletRequest req, HttpServletResponse res, HttpServlet servlet) {
        super(req, res, servlet);
    }

    /**
     * If type_name and manufacturer_name parameters isn't specified
     * Returns json object with arrays of types and manufacturers representing all currently known entries in db
     * Otherwise returns models/series json object which has those references
     * */
    public void index() throws IOException {
        String type_name = request.getParameter("type_name");
        String manufacturer_name = request.getParameter("manufacturer_name");
        JsonObject jo = new JsonObject();
        Gson gson = new Gson();
        if(type_name != null && manufacturer_name != null && !type_name.isEmpty() && !manufacturer_name.isEmpty()){
            List<ItemSeries> series = CategoryManager.getSeriesWithBrandAndType(type_name, manufacturer_name);
            jo.addProperty("models", gson.toJson(series));
            sendJson(200, jo);
            return;
        }
        List<ItemType> types = CategoryManager.getAllTypes();
        List<ItemBrand> brands = CategoryManager.getAllBrands();
        jo.addProperty("types", gson.toJson(types));
        jo.addProperty("manufacturers", gson.toJson(brands));
        sendJson(200,jo);
    }
}
