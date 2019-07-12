package managers;

import database.DatabaseAccessObject;
import generalManagers.DeleteManager;
import models.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ChatManager {
    private static final String GET_CHAT_BY_CYCLE_QUERY = "SELECT * FROM chats WHERE cycle_id = ?;";
    private static final String GET_CHAT_BY_ID_QUERY = "SELECT * FROM chats WHERE id = ?;";
    private static final String GET_MESSAGES_QUERY = "SELECT * FROM messages WHERE chat_id = ?;";
    private static final String INSERT_MESSAGE_QUERY = "INSERT INTO messages (chat_id," +
            " body, author_id, created_at) VALUES (?, ?, ?, ?);";
    private static final String INSERT_CHAT_QUERY = "INSERT INTO chats (cycle_id) VALUES(?);";
    private static DatabaseAccessObject DBO = DatabaseAccessObject.getInstance();


    /**
     * Adds a message to dataBase, updates ID in passed object
     * @param msg
     * @return false if insertion fails
     */
    public static boolean addMessageToDB(Message msg) {
        try {
            PreparedStatement st = DBO.getPreparedStatement(INSERT_MESSAGE_QUERY, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, msg.getChatID());
            st.setString(2, msg.getBody());
            st.setInt(3, msg.getUserId());
            st.setTimestamp(4, msg.getDate());
            if (st.executeUpdate() == 0)
                throw new SQLException("Creating message failed, no rows affected.");
            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next())
                    msg.setMessageID(generatedKeys.getInt(1));
                else
                    throw new SQLException("Creating message failed, no ID obtained.");
            }
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
     * Adds a chat to dataBase, changes ID in passed chat
     * Returns false if it fails
     * @param chat
     */
    public static boolean addChatToDB(Chat chat){
        try {
            PreparedStatement st = DBO.getPreparedStatement(INSERT_CHAT_QUERY, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, chat.getCycle().getCycleID());
            if (st.executeUpdate() == 0)
                throw new SQLException("Creating chat failed, no rows affected.");
            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next())
                    chat.setChatID(generatedKeys.getInt(1));

                else
                    throw new SQLException("Creating chat failed, no ID obtained.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Fills chat with messages
     * @param ch
     */
    private static void getMessagesForChat(Chat ch){
        try {
            PreparedStatement st = DBO.getPreparedStatement(GET_MESSAGES_QUERY);
            st.setInt(1, ch.getChatID());
            ResultSet set = st.executeQuery();

            while (set.next()) {
                ch.addMessage(new Message(set.getInt("id"), ch.getChatID(),set.getInt("author_id"),
                        set.getString("body"), set.getTimestamp("created_at")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
            if(set.next())
            ch = new Chat(set.getBigDecimal("id").intValue(),
                    new Cycle(cycleID), set.getTimestamp("updated_at"), new Vector<>());
            getMessagesForChat(ch);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ch;
    }

    /**
     * Finds user's active chats in database
     * @param user_id
     * @return list of chats
     */
    public static List<Chat> getUserChats(int user_id){
        List<Chat> chats = new ArrayList<>();
        String query = "SELECT ch.id " +
                "FROM chats ch " +
                "JOIN cycles ON cycles.id = ch.cycle_id "+
                "JOIN offered_cycles cyc ON cycles.id = cyc.cycle_id " +
                "JOIN deals d on d.id = cyc.deal_id " +
                "WHERE cycles.status_id = " + ProcessStatus.Status.ONGOING.getId() +
                " AND d.user_id = " + user_id +
                " ORDER BY ch.updated_at";

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
            ch = new Chat(chatID, new Cycle(set.getBigDecimal("cycle_id").intValue()),
                    set.getTimestamp("updated_at"), new Vector<>());
            getMessagesForChat(ch);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ch;
    }

    /**
     *
     * @param chat_id id of the chat
     * @return List<User> users who participate in given chat
     */
    public static List<User> getChatParticipants(int chat_id){
        List<User> result = new ArrayList<>();
        List<String> userNames = getChatUserNames(chat_id);
        for(String a : userNames){
            result.add(UserManager.getUserByUsername(a));
        }

        return result;
    }

    /**
     *
     * @param chat_id id of the chat
     * @return List<String> usernames of users who participate
     * in given chat
     */
    public static List<String> getChatUserNames(int chat_id){
        List<String> result = new ArrayList<>();

        String statement = "SELECT user_name FROM cycles c " +
                "JOIN offered_cycles oc ON c.id = oc.cycle_id " +
                "JOIN deals d ON oc.deal_id = d.id " +
                "WHERE id = " + chat_id +";";

        try {
            PreparedStatement st = DBO.getPreparedStatement(statement);
            ResultSet set = st.executeQuery();

            while(set.next()){
                result.add(set.getString("user_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
