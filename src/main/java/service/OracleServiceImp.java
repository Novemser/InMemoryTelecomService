package service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by root on 12/22/16.
 */
@Service
public class OracleServiceImp implements BaseTelecomService {
    @Autowired
    @Qualifier(value = "oracleJdbcTemplate")
    private JdbcTemplate oracleJdbcTemplate;

    public boolean verifyUser(String phoneId, String password) {

        return false;
    }

    public JSONObject getBanance(String phoneId) {
        return null;
    }

    public boolean recharge(String phoneId, Integer amount) {
        return false;
    }

    public List<JSONObject> getRechargeRecord(String phoneId, Integer begin) {
        return null;
    }

    public List<JSONObject> getRecords(String phoneId, Integer begin) {
        return null;
    }

    public List<JSONObject> getCallTotalTime(Date from, Date to) {
        return null;
    }

    public List<JSONObject> getCallTotalAmount(Date from, Date to) {
        return null;
    }

    public List<JSONObject> getCallDuration(Integer from, Integer to) {
        return null;
    }

    public List<JSONObject> getNewUserCount(Date from, Date to) {
        return null;
    }
}
