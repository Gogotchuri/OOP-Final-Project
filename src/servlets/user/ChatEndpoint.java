package servlets.user;

import controllers.user.ChatSessionController;
import managers.UserManager;
import models.Message;
import models.User;
import services.decoders.MessageDecoder;
import services.encoders.MessageEncoder;

import javax.naming.AuthenticationException;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.rmi.ServerException;

@ServerEndpoint(value="/user/chats/{chat_id}/{token}", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
public final class ChatEndpoint {
    private static final String USER_KEY = "user";
    private static final String SESSION_CONTROLLER_KEY = "ChatSessionController";
    @OnOpen
    public void onOpen(@PathParam("chat_id")final int chat_id, @PathParam("token") final String token,final Session session) throws Exception {
        System.out.println("OPEN! for " + chat_id);
        User user = UserManager.getUserByUsername(token);

        if(user == null) throw new AuthenticationException("User authentication failed!");
        else session.getUserProperties().put(USER_KEY, user);

        ChatSessionController csc = ChatSessionController.getInstanceForChat(chat_id, session);
        if(!csc.getChat().isParticipant(user.getUserID())) throw new AuthenticationException("User isn't allowed to participate!");

        try {
            session.getUserProperties().put(SESSION_CONTROLLER_KEY, csc);
            session.getBasicRemote().sendText("{\"body\":\"Endpoint OPEN!\"}");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                session.getBasicRemote().sendText("Endpoint is not open!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @OnMessage
    public void onMessage(final String message, final Session session){
        User user = (User) session.getUserProperties().get(USER_KEY);
        System.out.println("Message received: "+ message +"; From: " + user.getUsername());
        ChatSessionController csc = (ChatSessionController)session.getUserProperties().get(SESSION_CONTROLLER_KEY);
        try {
            csc.sendMessage(new Message(csc.getChat().getChatID(), user.getUserID(), message), session);
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(final Session session){
        ChatSessionController csc = (ChatSessionController)session.getUserProperties().get(SESSION_CONTROLLER_KEY);
        if(csc != null) csc.removeSession(session);
        System.out.println("Session closed!");
    }

    @OnError
    public void onError(final Session session, final Throwable throwable){
        System.out.println("IN ON ERROR!");
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUserKey() {
        return USER_KEY;
    }

    public static String getSessionControllerKey() {
        return SESSION_CONTROLLER_KEY;
    }
}
