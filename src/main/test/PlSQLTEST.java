import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import service.OracleServiceImp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                    new java.sql.Date(System.currentTimeMillis()), random.nextInt(100)));
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



    @Test
    public void test_totaltime()
    {
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date fromdate,todate;
        try {
            fromdate = df.parse("2016-12-29");
            System.out.println(fromdate);
            todate=df.parse("2017-1-5");
            System.out.println(todate);
            List<JSONObject> jsonObjectList = serviceImp.getCallTotalAmount(fromdate,todate);
            Assert.assertNotEquals(0,jsonObjectList.size());
            for (JSONObject callingTotal:jsonObjectList
                    ) {
                System.out.println(callingTotal.toJSONString());
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }




    @Test
    public void test_totalAmount()
    {
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date fromdate,todate;
        try {
            fromdate = df.parse("2016-12-29");
            System.out.println(fromdate);
            todate=df.parse("2017-1-5");
            System.out.println(todate);
            List<JSONObject> jsonObjectList = serviceImp.getCallTotalAmount(fromdate,todate);
            Assert.assertNotEquals(0,jsonObjectList.size());
            for (JSONObject callingTotal:jsonObjectList
                    ) {
                System.out.println(callingTotal.toJSONString());
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    @Test
    public void test_callduration()
    {
        int from,to;
        from=0;
        to=999;
        List<JSONObject> jsonObjects = serviceImp.getCallDuration(from,to);
        System.out.println(jsonObjects.toString());
    }

    @Test
    public void test_newusercount()
    {
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromdate,todate;
        try {
            fromdate = df.parse("2015-12-20 19:20:23");
            System.out.println(fromdate);
            todate=df.parse("2017-1-10 20:15:12");

            List<JSONObject>objects = serviceImp.getNewUserCount(fromdate,todate);
            System.out.println(objects);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
