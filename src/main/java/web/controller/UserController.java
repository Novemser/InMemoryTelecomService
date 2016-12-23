package web.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by root on 12/21/16.
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @PostMapping("/signin")
    public JSONObject signIn(@RequestBody JSONObject input) {
        String phoneNumber = input.getString("phone_number");
        String password = input.getString("password");
        return new JSONObject();
    }
}
