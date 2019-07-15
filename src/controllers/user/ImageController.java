package controllers.user;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controllers.Controller;
import managers.ImagesManager;
import managers.ItemManager;
import models.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

public class ImageController extends Controller {
    //TODO might need to think of something else!
    private static final String GLOBAL_PATH_TO_WEB = "/home/gogotchuri/Workspace/OOP/OOP-Final-Project/web";
    private User user;
    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     *
     * @param req
     * @param res
     * @param servlet
     */
    public ImageController(HttpServletRequest req, HttpServletResponse res, HttpServlet servlet) throws ServletException {
        super(req, res, servlet);
        user = (User) session.getAttribute("user");
        if(user == null) throw new ServletException("Unauthorized user!");
    }

    public void storeForUser() throws IOException {
        JsonObject jo = new JsonObject();
        String url = storeItemLocally();
        Image img = new Image(user.getUserID(), url);
        if(!ImagesManager.addImage(img)){
            //TODO find and fix image displaying error
            sendApiError(500,"Couldn't add image to database!");
            return;
        }
        jo.addProperty("image_url", url);
        sendJson(201, jo);
    }

    public void storeForItem(int itemID) throws IOException {
        if(!checkItemOwnership(itemID)) return;
        JsonObject jo = new JsonObject();
        String url = storeItemLocally();
        ItemImage image = new ItemImage(url, user.getUserID(), itemID, ImageCategories.ImageCategory.FEATURED);
        if(!ImagesManager.addImage(image)){
            sendApiError(500,"Couldn't add image to database!");
            return;
        }
        jo.addProperty("image_url", url);
        sendJson(201, jo);
    }

    private String storeItemLocally() throws IOException {
        String result_path;
        String base64_image;
        try{
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = request.getReader().readLine()) != null) {
                sb.append(s);
            }
            JsonObject jo = new JsonParser().parse(sb.toString()).getAsJsonObject();
            base64_image = jo.get("base64_image").getAsString();
        }catch (Exception e) {
            e.printStackTrace();
            sendApiError(422, "Error while parsing Json");
            return null;
        }

        base64_image = base64_image.split(",")[1];
        byte[] bytes = DatatypeConverter.parseBase64Binary(base64_image);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(bis);

        String filename = new Timestamp(System.currentTimeMillis()).toString() + base64_image.substring(0, 15);

        //Using same hashing method as in password, tho it is confusing!
        filename = User.encryptPassword(filename);
        result_path = "/storage/images/"+filename+".png";

        //TODO should come up with smthg else
        String pathToOut = request.getServletContext().getRealPath("/");
        try{
            //storing on the real location
            File output1 = new File(GLOBAL_PATH_TO_WEB+result_path);
            ImageIO.write(image, "png", output1);
            //Saving in the current resource folder
            File output2 = new File(pathToOut+result_path);
            ImageIO.write(image, "png", output2);
        }catch (Exception e){
            e.printStackTrace();
            sendApiError(500, "File couldn't be written!");
        }

        return result_path;
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

}
