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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        request.setAttribute("chats", chats);
        dispatchTo("/pages/user/chats.jsp");
    }

    public void show(int chat_ID) throws IOException, ServletException {
        PrintWriter pw = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject resp = new JsonObject();

        Chat chat = ChatManager.getChatByID(chat_ID);
        if(chat == null || !chat.isParticipant(user.getUserID())){
            response.setStatus(404);
            resp.addProperty("error", "Chat with given id doesn't exist or doesn't belong to you!");
            pw.print(resp.toString());
            pw.flush();
            return;
        }
        List<Message> messages = chat.getMessages();

        resp.addProperty("messages", gson.toJson(messages));
        resp.addProperty("chat_id", chat_ID);
        sendJson(200, resp);
    }


    //Not important for now, session controller handles message sending
//    /**
//     * Return api response, should be called through js as ajax
//     * @param cycle_id
//     * @throws IOException
//     * @throws ServletException
//     */
//    public void sendMessage(int cycle_id) throws IOException{
//        String body = request.getParameter("body");
//        PrintWriter out = response.getWriter();
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        if(body == null || body.isEmpty()){
//            response.setStatus(422);
//            out.print("Unprocessable entity! Should contain a body field!");
//            out.flush();
//            return;
//        }
//
//        Chat chat = ChatManager.getChatByCycleID(cycle_id);
//        if(chat == null){
//            response.setStatus(404);
//            out.print("No chat found with given cycle id!");
//            out.flush();
//            return;
//        }
//
//        Message msg = new Message(chat.getChatID(), user.getUserID(), body);
//
//        if(!ChatManager.addMessageToDB(msg)) {
//            response.setStatus(500);
//            out.print("Internal Server Error! Message Could't be sent");
//            out.flush();
//            return;
//        }
//
//        response.setStatus(201);
//        out.print("Message sent Successfully");
//        out.flush();
//    }
}
