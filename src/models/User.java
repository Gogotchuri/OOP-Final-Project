
package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;

public class User {


    /* Comments means default values
     * if user does not initializes it. */

    private int userID;           // 0

    private String
        username,                 // null
        password,                 // null
        firstName,                // null
        lastName,                 // null
        email,                    // null
        phoneNumber;              // null

    private Image profilePicture; // null

    private List<Deal> deals;     // null

    private List<Chat> chats;     // null

    private Timestamp createDate, updateDate; // null


    /**
     * Main Constructor
     * @param userID - ID of User in DB
     * @param username - User Name of User
     * @param password - Encrypted Password of User
     * @param firstName - First Name of User
     * @param lastName - Last Name of User
     * @param email - E-mail of User
     * @param phoneNumber - Phone Number of User
     * @param profilePicture - Profile Picture of User
     * @param deals - List of Deals of User
     * @param chats - Chats of User
     */
    public User (int userID,
                 String username,
                 String password,
                 String firstName,
                 String lastName,
                 String email,
                 String phoneNumber,
                 Image profilePicture,
                 List<Deal> deals,
                 List<Chat> chats,
                 Timestamp createDate,
                 Timestamp updateDate)
    {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        this.deals = deals;
        this.chats = chats;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }


    /**
     * Constructor.
     * @param username - User Name of User
     * @param password - Encrypted Password of User
     * @param firstName - First Name of User
     * @param lastName - Last Name of User
     * @param email - E-mail of User
     * @param phoneNumber - Phone Number of User
     */
    public User (String username,
                 String password,
                 String firstName,
                 String lastName,
                 String email,
                 String phoneNumber)
    {
        this (
            0,
            username,
            password,
            firstName,
            lastName,
            email,
            phoneNumber,
            null,
            null,
            null,
            null,
            null
        );
    }


    /**
     * @return ID of User in DB
     */
    public int getUserID() { return userID; }


    /**
     * Updates user's id
     */
    public void setUserID(int userID) { this.userID = userID; }


    /**
     * @return User Name of User
     */
    public String getUsername() { return username; }


    /**
     * @return Encrypted Password of User
     */
    public String getPassword() { return password; }


    /**
     * @return First Name of User
     */
    public String getFirstName() { return firstName; }


    /**
     * @return Last Name of User
     */
    public String getLastName() { return lastName; }


    /**
     * @return E-mail of User
     */
    public String getEmail() { return email; }


    /**
     * @return Phone Number of User
     */
    public String getPhoneNumber() { return phoneNumber; }


    /**
     * @return Profile Picture of User
     */
    public Image getProfilePicture() { return profilePicture; }


    /**
     * @return List of Deals of User
     */
    public List<Deal> getDeals() { return deals; }


    /**
     * @return Chats of User
     */
    public List<Chat> getChats() { return chats; }


    /**
     * @return User's Create Date
     */
    public Timestamp getCreateDate() { return createDate; }


    /**
     * @return User's Update Date
     */
    public Timestamp getUpdateDate() { return updateDate; }


    /**
     * Sets set encrypted Password.
     * @param password - Raw password
     */
    public void setRawPassword(String password) {
        this.password = encryptPassword(password);
    }


    /**
     * Sets new E-mail for User.
     * @param email - New E-mail of User
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
     * @param unprotected - Unprotected Password
     * @return Whether passed password equals of User's password
     */
    public boolean checkPassword(String unprotected) {
        return encryptPassword(unprotected).equals(password);
    }


    /**
     * Given a string password returns encrypted version
     * @param password - Unprotected password
     * @return Encrypted password
     */
    public static String encryptPassword(String password) {
        String hashed = password;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            hashed = hexToString(md.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashed;
    }

    private static String hexToString(byte[] hex) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hex) {
            int val = ((int)b) & 0xff;
            if (val < 16) sb.append('0');
            sb.append(Integer.toString(val, 16));
        }
        return sb.toString();
    }


    /**
     * @param other - other User
     * @return Whether two Users are equal or not
     */
    @Override public boolean equals(Object other) {

        if (this == other) return true;
        if (!(other instanceof User)) return false;

        User otherUser = (User) other;

        return userID == otherUser.userID;
    }

}
