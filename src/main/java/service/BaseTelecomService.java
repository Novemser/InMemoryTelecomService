package service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * Created by root on 12/21/16.
 */

public interface BaseTelecomService {

    boolean verifyUser(String phoneId, String password);

    JSONObject getBanance(String phoneId);

    boolean recharge(String phoneId, Integer amount);

    List<JSONObject> getRechargeRecord(String phoneId, Integer begin);

    List<JSONObject> getRecords(String phoneId, Integer begin);

    List<JSONObject> getCallTotalTime(Date from, Date to);

    List<JSONObject> getCallTotalAmount(Date from, Date to);

    List<JSONObject> getCallDuration(Integer from, Integer to);

    List<JSONObject> getNewUserCount(Date from, Date to);
}
