package web.controller.base;

import com.alibaba.fastjson.JSONObject;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/signin")
    public JSONObject signIn(@RequestBody JSONObject input) {
        String phoneNumber = input.getString("phone_number");
        String password = input.getString("password");

        boolean result = serviceImp.verifyUser(phoneNumber, password);

        JSONObject res = new JSONObject();
        res.put("result", result);
        return res;
    }

    @PostMapping("/balance")
    public JSONObject getBalance(@RequestBody JSONObject req) {
        String phone = req.getString("phone_number");
        return serviceImp.getBalance(phone);
    }

    @PostMapping("/recharge")
    public JSONObject rechargeFor(@RequestBody JSONObject req) {
        String phone = req.getString("phone_number");
        Integer amount = req.getInteger("amount");

        boolean result = serviceImp.recharge(phone, amount);

        JSONObject res = new JSONObject();
        res.put("result", result);
        return res;
    }

    @PostMapping("/rechargerecord")
    public List<JSONObject> getRechargeRecord(@RequestBody JSONObject req) {
        String phone = req.getString("phone_number");
        Integer begin = req.getInteger("begin");

        return serviceImp.getRechargeRecord(phone, begin);
    }

    @PostMapping("/record")
    public Object getRecord(@RequestBody JSONObject req) {
        String phone = req.getString("phone_number");
        Integer begin = req.getInteger("begin");

        return serviceImp.getRecords(phone, begin);
    }

    @PostMapping("/calltotaltime")
    public Object getCallTotalTime(@RequestBody JSONObject req) throws ParseException {
        Date from = df.parse(req.getString("from"));
        Date to = df.parse(req.getString("to"));
        return serviceImp.getCallTotalTime(from, to);
    }

    @PostMapping("/calltotalamount")
    public Object getCallTotalAmount(@RequestBody JSONObject req) throws ParseException {
        Date from = df.parse(req.getString("from"));
        Date to = df.parse(req.getString("to"));
        return serviceImp.getCallTotalAmount(from, to);
    }

    @PostMapping("/callduration")
    public Object getCallDuration(@RequestBody JSONObject req) throws ParseException {
        Integer from = req.getInteger("from");
        Integer to = req.getInteger("to");
        return serviceImp.getCallDuration(from, to);
    }

    @PostMapping("/newuser")
    public Object getNewUserCount(@RequestBody JSONObject req) throws ParseException {
        Date from = df.parse(req.getString("from"));
        Date to = df.parse(req.getString("to"));
        return serviceImp.getNewUserCount(from, to);
    }
}
