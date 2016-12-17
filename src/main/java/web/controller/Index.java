package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Project: InMemDB
 * Package: web.controller
 * Author:  Novemser
 * 2016/12/18
 */

@RestController
public class Index {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/index")
    public String test() {
        jdbcTemplate.queryForList("SELECT * FROM asdasdas");
        return "{2:3, 3:23}";
    }

    static {
        Connection connection = null;
        try {
            String tt = "com.timesten.jdbc.TimesTenClientDriver";
            String URL = "jdbc:timesten:client:ttc_server=192.168.52.135;tcp_port=53393;ttc_server_dsn=cachedb;uid=tthr;pwd=tthr";
            Class.forName(tt);

            Connection conn = DriverManager.getConnection(URL);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
