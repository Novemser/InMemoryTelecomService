package web.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Project: InMemDB
 * Package: web.controller
 * Author:  Novemser
 * 2016/12/18
 */

/**
 *  Page query
 * select RECORDID ,
 CALLINGTIME ,
 CALLDUTATION ,
 COST ,
 CALLINGUSER ,
 CALLEDUSER  from ( select  t.*, rownum RN from RECORD  t ) where RN > 11 and RN <= 15;
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
    public JSONObject test() {
        JSONObject object = new JSONObject();
        try {
             object.put("key", ttJdbcTemplate.queryForList("SELECT count(*) FROM TELECOMUSER"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return object;
    }

    @PostMapping("/test/post")
    @ResponseBody
    public JSONObject testPost(@RequestBody JSONObject jsonObject) {
        System.out.println(jsonObject.toJSONString());
        return jsonObject;
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
