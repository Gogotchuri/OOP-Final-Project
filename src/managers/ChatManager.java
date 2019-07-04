package managers;

import database.DatabaseAccessObject;
import generalManagers.DeleteManager;
import models.Chat;
import models.Message;
import models.ProcessStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class ChatManager {
    private static final String GET_CHAT_BY_CYCLE_QUERY = "SELECT * FROM chats WHERE cycle_id = ?;";
    private static final String GET_CHAT_BY_ID_QUERY = "SELECT * FROM chats WHERE id = ?;";
    private static final String GET_MESSAGES_QUERY = "SELECT * FROM messages WHERE chat_id = ?;";
    private static final String INSERT_MESSAGE_QUERY = "INSERT INTO messages (chat_id, body, created_at) VALUES (?, ?, ?);";
    private static final String INSERT_CHAT_QUERY = "INSERT INTO chats (cycle_id, updated_at) VALUES(?, ?);";
    private static DatabaseAccessObject DBO = DatabaseAccessObject.getInstance();


    /**
     * Adds a message to dataBase
     * @param msg
     * @return false if insertion fails
     */
    public static boolean addMessageToDB(Message msg) {
        try {
            PreparedStatement st = DBO.getPreparedStatement(INSERT_MESSAGE_QUERY);
            st.setInt(1, msg.getChatID());
            st.setString(2, msg.getBody());
            st.setTimestamp(3, msg.getDate());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Deletes message from database using its id
     * @param messageID
     * @return returns false if operation fails
     */
    public static boolean removeMessageFromDB(int messageID){
        return DeleteManager.delete("messages", "id", messageID);
    }

    /**
     * Deletes every message from database which has specified chat_id
     * @param chatID
     * @return returns false if operation fails
     */
    public static boolean removeMessagesByChatID(int chatID){
        return DeleteManager.delete("messages", "chat_id", chatID);
    }

    /**
     * Deletes chat from database using its id
     * @param chatID
     * @return returns false if operation fails
     */
    public static boolean removeChatFromDB(int chatID){
        return removeMessagesByChatID(chatID) &&
                DeleteManager.delete("chats", "id", chatID);
    }


    /**
     * Adds a chat to dataBase
     * Returns false if it fails
     * @param chat
     */
    public static boolean addChatToDB(Chat chat){
        try {
            PreparedStatement st = DBO.getPreparedStatement(INSERT_CHAT_QUERY);
            st.setInt(1, chat.getCycleID());
            st.setTimestamp(2, chat.getLastUpdateDate());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     *
     * @param cycleID id of the cycle, which the chat is related to
     * @return chat related to cycle with cycleID
     */
    public static Chat getChatByCycleID(int cycleID){
        Chat ch = null;
        try {
            PreparedStatement st = DBO.getPreparedStatement(GET_CHAT_BY_CYCLE_QUERY);
            st.setInt(1, cycleID);
            ResultSet set = st.executeQuery();
            set.next();
            ch = new Chat(set.getBigDecimal("id").intValue(),
                    cycleID, set.getTimestamp("updated_at"), new Vector<>());

            st = DBO.getPreparedStatement(GET_MESSAGES_QUERY);
            st.setInt(1, ch.getChatID());
            set = st.executeQuery();

            while (set.next()) {
                ch.addMessage(new Message(set.getInt("id"), ch.getChatID(),
                        set.getString("body"), set.getTimestamp("created_at")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ch;
    }

    //TODO WARNING! not tested yet, please test
    public static List<Chat> getUserChats(int user_id){
        List<Chat> chats = new ArrayList<>();
        String query = "SELECT ch.id" +
                "FROM chats ch" +
                "JOIN cycles ON cycles.id = ch.cycle_id"+
                "JOIN offered_cycles cyc ON cycles.id = cyc.cycle_id" +
                "JOIN deals d on d.id = cyc.deal_id" +
                "WHERE cycles.status id = " + ProcessStatus.Status.ONGOING.getId() +
                "AND d.user_id = " + user_id +
                "ORDER BY ch.updated_at";
        //TODO is this right?? no idea

        try {
            PreparedStatement st = DBO.getPreparedStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Chat chat = getChatByID(rs.getInt("id"));
                chats.add(chat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chats;
    }
    /**
     *
     * @param chatID id of the chat
     * @return chat with passed id
     */
    public static Chat getChatByID(int chatID){
        Chat ch = null;
        try {
            PreparedStatement st = DBO.getPreparedStatement(GET_CHAT_BY_ID_QUERY);
            st.setInt(1, chatID);
            ResultSet set = st.executeQuery();
            set.next();
            ch = new Chat(chatID, set.getBigDecimal("cycle_id").intValue(),
                    set.getTimestamp("updated_at"), new Vector<>());

            st = DBO.getPreparedStatement(GET_MESSAGES_QUERY);
            st.setInt(1, ch.getChatID());
            set = st.executeQuery();

            while (set.next()) {
                ch.addMessage(new Message(set.getInt("id"), ch.getChatID(),
                        set.getString("body"), set.getTimestamp("created_at")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ch;
    }
}
