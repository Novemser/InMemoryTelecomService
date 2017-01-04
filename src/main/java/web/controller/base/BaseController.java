package web.controller.base;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.web.bind.annotation.*;
import service.base.AbstractTelecomServiceImp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 12/21/16.
 */
@RestController
public class BaseController {

    protected AbstractTelecomServiceImp serviceImp;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private long start, end;

    @PostMapping("/signin")
    public JSONObject signIn(@RequestBody JSONObject input) {
        String phoneNumber = input.getString("phone_number");
        String password = input.getString("password");
        start = System.currentTimeMillis();
        boolean result = serviceImp.verifyUser(phoneNumber, password);
        end = System.currentTimeMillis();
        JSONObject res = new JSONObject();
        res.put("result", result);
        res.put("queryTime", end - start);
        return res;
    }

    @PostMapping("/balance")
    public JSONObject getBalance(@RequestBody JSONObject req) {
        String phone = req.getString("phone_number");
        JSONObject res = new JSONObject();

        start = System.currentTimeMillis();
        JSONObject object = serviceImp.getBalance(phone);
        end = System.currentTimeMillis();
        res.put("queryTime", end - start);
        res.put("result", object);
        return res;
    }

    @PostMapping("/recharge")
    public JSONObject rechargeFor(@RequestBody JSONObject req) {
        String phone = req.getString("phone_number");
        Integer amount = req.getInteger("amount");
        start = System.currentTimeMillis();

        boolean result = serviceImp.recharge(phone, amount);
        end = System.currentTimeMillis();

        JSONObject res = new JSONObject();
        res.put("result", result);
        res.put("queryTime", end - start);

        return res;
    }

    @PostMapping("/rechargerecord")
    public JSONObject getRechargeRecord(@RequestBody JSONObject req) {
        String phone = req.getString("phone_number");
        Integer begin = req.getInteger("begin");
        JSONObject res = new JSONObject();

        start = System.currentTimeMillis();
        List<JSONObject> result = serviceImp.getRechargeRecord(phone, begin);
        end = System.currentTimeMillis();
        res.put("result", result);
        res.put("queryTime", end - start);
        return res;
    }

    @PostMapping("/record")
    public JSONObject getRecord(@RequestBody JSONObject req) {
        String phone = req.getString("phone_number");
        Integer begin = req.getInteger("begin");

        JSONObject res = new JSONObject();
        start = System.currentTimeMillis();
        List<JSONObject> result = serviceImp.getRecords(phone, begin);
        end = System.currentTimeMillis();
        res.put("result", result);
        res.put("queryTime", end - start);
        return res;
    }

    @PostMapping("/calltotaltime")
    public JSONObject getCallTotalTime(@RequestBody JSONObject req) throws ParseException {
        Date from = df.parse(req.getString("from"));
        Date to = df.parse(req.getString("to"));

        JSONObject res = new JSONObject();
        start = System.currentTimeMillis();
        List<JSONObject> result = serviceImp.getCallTotalTime(from, to);
        end = System.currentTimeMillis();
        res.put("result", result);
        res.put("queryTime", end - start);
        return res;
    }

    @PostMapping("/calltotalamount")
    public Object getCallTotalAmount(@RequestBody JSONObject req) throws ParseException {
        Date from = df.parse(req.getString("from"));
        Date to = df.parse(req.getString("to"));
        JSONObject res = new JSONObject();
        start = System.currentTimeMillis();
        List<JSONObject> result = serviceImp.getCallTotalAmount(from, to);
        end = System.currentTimeMillis();
        res.put("result", result);
        res.put("queryTime", end - start);
        return res;
    }

    @PostMapping("/callduration")
    public JSONObject getCallDuration(@RequestBody JSONObject req) throws ParseException {
        Integer from = req.getInteger("from");
        Integer to = req.getInteger("to");
        JSONObject res = new JSONObject();
        start = System.currentTimeMillis();
        List<JSONObject> result = serviceImp.getCallDuration(from, to);
        end = System.currentTimeMillis();
        res.put("result", result);
        res.put("queryTime", end - start);
        return res;
    }

    @PostMapping("/newuser")
    public JSONObject getNewUserCount(@RequestBody JSONObject req) throws ParseException {
        Date from = df.parse(req.getString("from"));
        Date to = df.parse(req.getString("to"));
        JSONObject res = new JSONObject();
        start = System.currentTimeMillis();
        List<JSONObject> result = serviceImp.getNewUserCount(from, to);
        end = System.currentTimeMillis();
        res.put("result", result);
        res.put("queryTime", end - start);
        return res;
    }

    @PostMapping("/newuser/noplsql")
    public JSONObject getNewUserCountWithoutPLSQL(@RequestBody JSONObject req) throws ParseException {
        Date from = df.parse(req.getString("from"));
        Date to = df.parse(req.getString("to"));
        JSONObject res = new JSONObject();
        start = System.currentTimeMillis();
        List<JSONObject> result = serviceImp.getNewUserCountWithOutPLSQL(from, to);
        end = System.currentTimeMillis();
        res.put("result", result);
        res.put("queryTime", end - start);
        return res;
    }

    @PostMapping("/combine/1")
    public JSONObject combine1(@RequestBody JSONObject req) throws Exception {
        Date from = df.parse(req.getString("from"));
        Date to = df.parse(req.getString("to"));

        JSONObject res = new JSONObject();

        start = System.currentTimeMillis();
        serviceImp.getCallTotalTime(from, to);
        serviceImp.getNewUserCount(from, to);
        serviceImp.getCallDuration(5, 10);
        end = System.currentTimeMillis();

        res.put("info", "Query getCallTotalTime->getNewUserCount->getCallDuration for 1 time");
        res.put("queryTime", end - start);
        return res;
    }

    @GetMapping("/transaction/test/recharge/{num}")
    public JSONObject test1(@PathVariable int num) {
        return serviceImp.testRecharge(num);
    }

    @GetMapping("/transaction/test/recharge/m/{num}")
    public JSONObject test2(@PathVariable int num) {
        return serviceImp.testRechargeMultiThread(num);
    }

    @GetMapping("/transaction/test/update/remaintime")
    public JSONObject test3() {
        return serviceImp.testUpdateInfo();
    }

    @GetMapping("/transaction/test/call")
    public JSONObject test4( ) {
        return serviceImp.testCall();
    }

    @GetMapping("/transaction/test/call/m")
    public JSONObject test5() {
        return serviceImp.testCallMultiThread();
    }
}
