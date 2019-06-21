package models;

import java.sql.Timestamp;
import java.util.List;

import managers.DealsManager;
import managers.ImagesManager;

public class User {
    private int id;
    private String username;
    private String password; //Encrypted password
    private String firstName;
    private String lastName;
    private String email;
    private String phone_number;
    private Timestamp created_at;
    private Timestamp updated_at;


    public User(String username, String password, String firstName, String lastName, String email,
                String phone_number){
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone_number = phone_number;
        this.created_at =  new Timestamp(System.currentTimeMillis());
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public User(int id, String username, String password, String firstName, String lastName, String email,
                String phone_number){
        this.id = id;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone_number = phone_number;
        this.created_at =  new Timestamp(System.currentTimeMillis());
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public User(int id, String username, String password, String firstName, String lastName, String email,
                String phone_number, Timestamp updated_at, Timestamp created_at){
        this.id = id;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone_number = phone_number;
        this.created_at =  created_at;
        this.updated_at = updated_at;
    }

    public Image getProfilePicture(){
        return ImagesManager.getUserProfileImage(id);
    }

    public List <Image> getImages(){return ImagesManager.getUserImages(id);};

    public List <Deal> getDeals(){ return DealsManager.getUserDeals(this);};

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public Timestamp getCreatedDate(){
        return created_at;
    }

    public Timestamp getUpdatedDate() {
        return updated_at;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public void setId(int id) {
        this.id = id;
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public void setUsername(String username) {
        this.username = username;
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public void setPassword(String password) {
        this.password = password;
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public void setEmail(String email) {
        this.email = email;
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    //Helpers
    public boolean checkPassword(String unprotected){
        String encrypted = encryptPassword(unprotected); //TODO should encrypt here appropriately
        //then check
        return encrypted.equals(getPassword());
    }

    /**
     * Given a string password returns encrypted version
     * @param password
     * @return
     */
    public static String encryptPassword(String password){
        //TODO encryption logic here
        return password;
    }

}
