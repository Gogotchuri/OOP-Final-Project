package controllers.user;

import models.Chat;
import models.Message;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class ChatSessionController{
    private Chat chat;
    private final Set<Session> sessions = new CopyOnWriteArraySet<>();
    //Chat ids to controllers
    private static final Map<Integer, ChatSessionController> chatSessionControllerMap = new ConcurrentHashMap<>();

    private ChatSessionController(int chat_id) throws Exception {
//        chat = new Chat(chat_id, 1);
        //TODO uncomment and check
//        chat = ChatManager.getChatByID(chat_id);
//        if(chat == null) throw new Exception("Chat not found!");
    }

    /**
     * Given a chat id returns a session controller for it
     * @param chat_id chat id for which controller should be returned
     * @throws Exception if no chat has been found on provided chat id
     * */
    public static ChatSessionController getInstanceForChat(int chat_id, Session session) throws Exception {
        synchronized (chatSessionControllerMap) {
            ChatSessionController csc = null;
            if (chatSessionControllerMap.containsKey(chat_id))
                csc = chatSessionControllerMap.get(chat_id);
            else {
                csc = new ChatSessionController(chat_id);
                chatSessionControllerMap.put(chat_id, csc);
            }
            //TODO require use privileges on chat
            csc.addSession(session);
            return csc;
        }
    }

    private void addSession(Session session) {
        sessions.add(session);
    }

    public synchronized void sendMessage(Message msg, Session session) throws ServerException {
        chat.addMessage(msg);
        //TODO check and uncomment
//        if(!ChatManager.addMessageToDB(msg))
//            throw new ServerException("Message couldn't be sent! (ChatSessionController:43)");
        for(Session ses : sessions){
            try {
                ses.getBasicRemote().sendObject(msg);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void removeSession(Session session){
        sessions.remove(session);
    }

    public Chat getChat(){
        return chat;
    }
}
