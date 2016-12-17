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
            String tt = "com.timesten.jdbc.TimesTenClientDriver";
            String URL = "jdbc:timesten:client:ttc_server=192.168.52.135;tcp_port=53393;ttc_server_dsn=cachedb;uid=tthr;pwd=tthr";
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
