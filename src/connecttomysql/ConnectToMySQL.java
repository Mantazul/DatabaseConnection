package connecttomysql;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class ConnectToMySQL {
    public static Connection connect = null;
    public static Statement statement = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;

    public static Properties loadProperties() throws IOException {
        Properties prop = new Properties();
        InputStream ism = new FileInputStream("src/secret.properties");
        prop.load(ism);
        ism.close();
        return prop;
    }

    public static Connection connectToMySQL() throws IOException, SQLException, ClassNotFoundException {
        Properties prop = loadProperties();
        String driverClass = prop.getProperty("MYSQLJDBC.driver");
        String url = prop.getProperty("MYSQLJDBC.url");
        String userName = prop.getProperty("MYSQLJDBC.user");
        String password = prop.getProperty("MYSQLJDBC.password");
        Class.forName(driverClass);
        connect = DriverManager.getConnection(url, userName, password);
        System.out.println("Database is connected");
        return connect;
    }

    public static void main(String[] args) {
        try {
            //get the connection to the database
            Connection mycon = connectToMySQL();
            //create a statement
            Statement statement = mycon.createStatement();
            //write the sql query and execute it
            ResultSet rs = statement.executeQuery("Select * From Students");
            //process the result set
            while (rs.next()) {
                System.out.println("Data: " + rs.getString("studentID") + " " + rs.getString("studentName") + " " + rs.getString("studentDOB"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
