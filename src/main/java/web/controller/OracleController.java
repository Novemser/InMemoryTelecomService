package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.base.AbstractTelecomServiceImp;
import web.controller.base.BaseController;

/**
 * Created by root on 12/30/16.
 */
@RestController
@RequestMapping("/api/oracle")
public class OracleController extends BaseController {
    @Autowired
    public OracleController(@Qualifier("oracleServiceImp") AbstractTelecomServiceImp serviceImp) {
        this.serviceImp = serviceImp;
    }
}
