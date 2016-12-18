package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Project: InMemDB
 * Package: web.controller
 * Author:  Novemser
 * 2016/12/18
 */

@RestController
public class Index {

    @Autowired
    @Qualifier(value = "ttJdbcTemplate")
    private JdbcTemplate ttJdbcTemplate;

    @Autowired
    @Qualifier(value = "oracleJdbcTemplate")
    private JdbcTemplate oracleJdbcTemplate;

    @GetMapping("/index/tt")
    public String test() {

        try {

            ttJdbcTemplate.queryForList("SELECT * FROM employee");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "{msg: \"not found\"}";
        }

        return "{2:3, 3:23}";
    }

    @GetMapping("/index/oracle")
    public String testOracle() {
        List list = null;
        try {

            list = oracleJdbcTemplate.queryForList("SELECT * FROM NOVA.RECORD");
            return list.size() + " in total";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "{msg: \"not found\"}";
        }

//        return "{2:3, 3:23}";
    }

//    static {
//        Connection connection = null;
//        try {
//            String tt = "com.timesten.jdbc.TimesTenDriver";
//            String URL = "jdbc:timesten:direct:dsn=cachedb;uid=tthr;pwd=tthr";
//            Class.forName(tt);
//            System.out.println("Working Directory = " +
//                    System.getProperty("user.dir"));
//
//            Connection conn = DriverManager.getConnection(URL);
//
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


}
