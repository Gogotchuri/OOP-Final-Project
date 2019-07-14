package controllers.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controllers.Controller;
import managers.ChatManager;
import models.Chat;
import models.Cycle;
import models.Message;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ChatsController extends Controller {
    private User user;
    /**
     * Creates a controller, usually called from servlet, which is also
     * passed by parameter. servlet method passes taken request and response.
     *
     * @param req
     * @param res
     * @param servlet
     */
    public ChatsController(HttpServletRequest req, HttpServletResponse res, HttpServlet servlet) throws ServletException {
        super(req, res, servlet);
        user = (User) session.getAttribute("user");
        if(user == null) throw new ServletException("Unauthorized user!");
    }

    public void index() throws IOException, ServletException {
        List<Chat> chats = ChatManager.getUserChats(this.user.getUserID());
        if (chats == null) chats = new ArrayList<>();
        else {
            Set<Chat> uniqueCycles = new TreeSet<>(chats);
            chats = new ArrayList<>(uniqueCycles);
        }
        request.setAttribute("chats", chats);
        dispatchTo("/pages/user/chats.jsp");
    }

    public void show(int chat_ID) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject resp = new JsonObject();

        Chat chat = ChatManager.getChatByID(chat_ID);
        if(chat == null || !chat.isParticipant(user.getUserID())){
            sendApiError(404,"Chat with given id doesn't exist or doesn't belong to you!");
            return;
        }
        List<Message> messages = chat.getMessages();

        resp.addProperty("messages", gson.toJson(messages));
        resp.addProperty("chat_id", chat_ID);
        sendJson(200, resp);
    }
}
