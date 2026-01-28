package se.lexicon.db;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // Option 1
    private static Connection connection;

    // Option 2
    private static DataSource dataSource;

    // Option 1
    public static Connection getMySQLConnection() throws SQLException {
        if (connection == null) {
            //connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection = getMySQLDataSource().getConnection();
        }
        // throw custom exception as needed
        return connection;
    }

    // Option 2
    public static DataSource getMySQLDataSource() {
        if (dataSource == null){
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setUrl(URL);
            mysqlDataSource.setUser(USER);
            mysqlDataSource.setPassword(PASSWORD);
            dataSource = mysqlDataSource;
        }
        return dataSource;
    }






}
