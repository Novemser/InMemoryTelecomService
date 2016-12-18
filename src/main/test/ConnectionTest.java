import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Project: InMemDB
 * Package: PACKAGE_NAME
 * Author:  Novemser
 * 2016/12/17
 */

public class ConnectionTest {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            String tt = "com.timesten.jdbc.TimesTenDriver";
            String URL = "jdbc:timesten:direct:DSN=cachedb;uid=tthr;pwd=tthr";
            Class.forName (tt);

            Connection conn = DriverManager.getConnection(URL);

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
