package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtils {

    public Connection connection;
    public Connection getConnection(){


        String dbName="MovieReservation";
        String userName="root";
        String password="35893589";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            connection= DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,userName,password);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return connection;
    }

}
