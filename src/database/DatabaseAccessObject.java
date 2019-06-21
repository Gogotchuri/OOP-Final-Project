package database;

import models.Image;
import java.sql.*;

public class DatabaseAccessObject {
    public static final String MYSQL_USERNAME = "root";
    public static final String MYSQL_PASSWORD = "bla";
    public static final String MYSQL_DATABASE_SERVER = "127.0.0.1";
    public static final String MYSQL_DATABASE_NAME = "bla";

    private static DatabaseAccessObject dao = null;

    private Connection con;
    private Statement st;


    public DatabaseAccessObject(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection( "jdbc:mysql://" + MYSQL_DATABASE_SERVER, MYSQL_USERNAME, MYSQL_PASSWORD);
            st = con.createStatement();
            st.executeQuery("USE " + MYSQL_DATABASE_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseAccessObject getInstance(){
        if(dao == null) dao = new DatabaseAccessObject();
        return dao;
    }

    public Connection getConnection(){
        return con;
    }

    public Statement getStatement(){
        return st;
    }

    public PreparedStatement getPreparedStatement(String pr_stmt) throws SQLException {
        return con.prepareStatement(pr_stmt);
    }

}
