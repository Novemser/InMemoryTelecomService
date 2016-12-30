import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import service.OracleServiceImp;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by qi on 2016/12/28.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/inmemdb-servlet.xml")
@WebAppConfiguration
public class PlSQLTEST {

    @Autowired
    OracleServiceImp serviceImp;

    @Test
    public void test_verifyUser()
    {

        Assert.assertEquals(true,serviceImp.verifyUser("14167281425","TqmVnQFl"));
    //     Assert.assertEquals(,serviceImp.recharge("1234",23));
     //   Boolean flag = serviceImp.recharge("12",34);
        //  JSONObject banance =  serviceImp.getBalance("18217798283");
      //    System.out.println(banance.toJSONString());
   //     List<JSONObject> records = serviceImp.getRechargeRecord("18217798283",0);
   //   List<JSONObject> callingRrecord =  serviceImp.getRecords("18217798283",0);
  /*     for (JSONObject o :
               records ) {
            System.out.println(o.toJSONString());
        }*/
    }


    @Test
    public void test_queryReChargeRecord()
    {
        List<JSONObject> records = serviceImp.getRechargeRecord("14167281425",0);
        for (JSONObject record :
                records) {
            System.out.println(record.toJSONString());
        }
    }


    @Test
    public void test_queryCallingRecord()
    {
        List<JSONObject> callingRecords = serviceImp.getRecords("14167281425",0);
        for (JSONObject callingRecord:callingRecords)
        {
            System.out.println(callingRecord.toJSONString());
        }
    }

    @Test
    public void test_havaCall()
    {
        Random random = new Random();
        for(int i=0;i<10;i++)
        {
            Assert.assertEquals(true,serviceImp.userHaveACall("18217798283","18217798284",
                    new Date(), random.nextInt(100)));
        }
    }


    @Test
    public void test_checkUserCanCall()
    {
        Assert.assertEquals(false,serviceImp.checkUserCanCall("18217798284"));
    }


    @Test
    public void test_recharge()
    {
        Assert.assertEquals(true,serviceImp.recharge("18217798284",100));
    }



}
