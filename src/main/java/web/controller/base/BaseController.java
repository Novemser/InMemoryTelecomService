package web.controller.base;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.base.AbstractTelecomServiceImp;

import java.util.List;

/**
 * Created by root on 12/21/16.
 */
@RestController
public class BaseController {

    protected AbstractTelecomServiceImp serviceImp;

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
}
