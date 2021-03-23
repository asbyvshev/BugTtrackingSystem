package sample.connectionDB;

import sample.entity.User;

import java.sql.*;

public class DataBaseHandler {
    private static Connection connection;
    private static Statement stmt;
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:BugTtrackingSystemDB.db";

    public static void connect() throws SQLException {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL);
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createUser(User user) {
        String sql = String.format("INSERT INTO '%s'('%s','%s','%s')VALUES(?,?,?)",
                Const.USERS_TABLE, Const.USERS_LOGIN, Const.USERS_PASSWORD, Const.USERS_NAME);
        try {
            PreparedStatement prSt = connection.prepareStatement(sql);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, user.getName());
            prSt.executeUpdate();
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
