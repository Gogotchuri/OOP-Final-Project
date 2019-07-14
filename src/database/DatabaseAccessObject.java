package database;


import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;

public class DatabaseAccessObject {
    private static final String MYSQL_USERNAME = "root";
    private static final String MYSQL_PASSWORD = "luka1234";
    private static final String MYSQL_DATABASE_SERVER = "localhost";
    private static final String MYSQL_DATABASE_NAME = "oop";

    private static DatabaseAccessObject dao = null;

    private Connection con;
    private Statement st;


    //Change to newer way of connecting to database
    private DatabaseAccessObject(){
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName(MYSQL_DATABASE_SERVER);
        ds.setDatabaseName(MYSQL_DATABASE_NAME);
        ds.setUser(MYSQL_USERNAME);
        ds.setPassword(MYSQL_PASSWORD);
        try {
            ds.setVerifyServerCertificate(false);
            con = ds.getConnection();
            st = con.createStatement();
            st.executeQuery("USE " + MYSQL_DATABASE_NAME);
        } catch (SQLException e) {
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

    public PreparedStatement getPreparedStatement(String pr_stmt, int option) throws SQLException {
        return con.prepareStatement(pr_stmt, option);
    }

}
