package service;

import com.alibaba.fastjson.JSONObject;
import oracle.jdbc.OracleTypes;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import service.base.BaseTelecomService;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 12/22/16.
 */
@Service
public class TimesTenServiceImp implements BaseTelecomService {
    @Autowired
    @Qualifier(value = "ttJdbcTemplate")
    private JdbcTemplate ttJdbcTemplate;

    public boolean verifyUser(final String phoneId, final String password) {
        final String verifyUserProcedure = "{call telecomPLSQL.UserISExsist(?,?,?)}";
        boolean userexsist =
                (Boolean) ttJdbcTemplate.execute(new CallableStatementCreator() {
                    public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                        CallableStatement callableStatement = connection.prepareCall(verifyUserProcedure);
                        callableStatement.setString(1,phoneId);
                        callableStatement.setString(2,password);
                        callableStatement.registerOutParameter(3, OracleTypes.NUMBER);
                        return  callableStatement;
                    }
                }, new CallableStatementCallback() {

                    public Object doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                        callableStatement.execute();
                        return callableStatement.getInt(3)==1;
                    }
                });
        return userexsist;
    }

    public JSONObject getBalance(final String phoneId) {
        final String getBalanceProcedure = "{call TELECOMPLSQL.queryBalance(?,?,?)}";
        Map<String,Object> Userbanance =
                ttJdbcTemplate.execute(new CallableStatementCreator() {
                    public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                        CallableStatement callableStatement = connection.prepareCall(getBalanceProcedure);
                        callableStatement.setString(1,phoneId);
                        callableStatement.registerOutParameter(2, OracleTypes.NUMBER);
                        callableStatement.registerOutParameter(3, Types.CHAR);
                        return callableStatement;
                    }
                }, new CallableStatementCallback<Map<String, Object>>() {
                    public Map<String, Object> doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                        callableStatement.execute();
                        Map<String,Object> map = new HashedMap();
                        map.put("balance",callableStatement.getDouble(2));
                        map.put("plan",callableStatement.getString(3).trim());
                        return  map;
                    }
                });
        return (JSONObject) JSONObject.toJSON(Userbanance);
    }

    public boolean recharge(final String phoneId, final Integer amount) {
        final String rechargeProcesure = "{call TELECOMPLSQL.userRecharge(?,?,?)}";
        return ttJdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                CallableStatement callableStatement = connection.prepareCall(rechargeProcesure);
                callableStatement.setString(1,phoneId);
                callableStatement.setDouble(2,amount);
                callableStatement.registerOutParameter(3,OracleTypes.NUMBER);
                return  callableStatement;
            }
        }, new CallableStatementCallback<Boolean>() {
            public Boolean doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                callableStatement.execute();
                return  callableStatement.getInt(3)==1;
            }
        });
    }

    public List<JSONObject> getRechargeRecord(final String phoneId, final Integer begin) {
        final String getRechargeRecordProcesure = "{call  TELECOMPLSQL.queryUserRechargeRecord(?,?,?)}";
        List<JSONObject> rechargeRecords = ttJdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                CallableStatement callStatement = connection.prepareCall(getRechargeRecordProcesure);
                callStatement.setString(1,phoneId);
                callStatement.setInt(2,begin);
                callStatement.registerOutParameter(3, OracleTypes.CURSOR);
                return callStatement;
            }
        },new CallableStatementCallback<List<JSONObject>>(){

            public List<JSONObject> doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                List<JSONObject> records = new ArrayList<JSONObject>();
                callableStatement.execute();
                ResultSet rs = (ResultSet)callableStatement.getObject(3);

        /*        for(int i=0;i<rs.getMetaData().getColumnCount();i++)
                {
                    System.out.println(rs.getMetaData().getColumnName(i+1));
                }*/

                while (rs.next())
                {
                    Map<String,Object> record = new HashedMap();
                    Date date = rs.getDate(1);
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                    String dateString = df.format(date);
                    record.put("time",dateString);
                    record.put("amount",rs.getDouble(2));
                    JSONObject jsonRecord = (JSONObject)JSONObject.toJSON(record);
                    records.add(jsonRecord);
                }
                return records;
            }
        });
        return rechargeRecords;
    }

    public List<JSONObject> getRecords(final String phoneId, final Integer begin) {

        final String getCallRecordsProcessure = "{call TELECOMPLSQL.queryUserCallingRecord(?,?,?)}";
        List<JSONObject> callingRecords
                = ttJdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                CallableStatement callableStatement = connection.prepareCall(getCallRecordsProcessure);
                callableStatement.setString(1,phoneId);
                callableStatement.setInt(2,begin);
                callableStatement.registerOutParameter(3,OracleTypes.CURSOR);
                return  callableStatement;
            }
        }, new CallableStatementCallback<List<JSONObject>>() {
            public List<JSONObject> doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                List<JSONObject> records = new ArrayList<JSONObject>();
                callableStatement.execute();
                ResultSet rs = (ResultSet)callableStatement.getObject(3);
           /*     ResultSetMetaData metaData = rs.getMetaData();
                for(int i=0;i<metaData.getColumnCount();i++)
                {
                    System.out.println(metaData.getColumnName(i+1));
                }*/
                while (rs.next())
                {
                    Map<String,Object>record = new HashedMap();
                    record.put("caller",rs.getString(1));
                    record.put("called",rs.getString(2));
                    Date date = rs.getDate(3);
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                    record.put("call_time",df.format(date));
                    record.put("dur_time",rs.getInt(4));
                    record.put("cost",rs.getDouble(5));
                    JSONObject JSONCallingRecord = (JSONObject) JSONObject.toJSON(record);
                    records.add(JSONCallingRecord);
                }
                return records;
            }
        });
        return callingRecords;
    }

    public Boolean userHaveACall(final String caller, final String called, final Date callingTime, final Integer duration) {
        return ttJdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                String haveCallProcesure = "{call TELECOMPLSQL.userHaveCall(?,?,?,?,?)}";
                CallableStatement callableStatement = connection.prepareCall(haveCallProcesure);
                callableStatement.setString(1,caller);
                callableStatement.setString(2,called);
                callableStatement.setDate(3,new java.sql.Date(callingTime.getTime()));
                callableStatement.setInt(4,duration);
                callableStatement.registerOutParameter(5,OracleTypes.NUMBER);
                return  callableStatement;
            }
        }, new CallableStatementCallback<Boolean>() {
            public Boolean doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                callableStatement.execute();
                return callableStatement.getInt(5)==1;
            }
        });
    }

    public Boolean checkUserCanCall(final String caller) {
        return ttJdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                String checkUserprocess = "{? = call TELECOMPLSQL.checkUserCanCall(?)";
                CallableStatement callableStatement = connection.prepareCall(checkUserprocess);
                callableStatement.registerOutParameter(1,OracleTypes.NUMBER);
                callableStatement.setString(2,caller);
                return  callableStatement;
            }
        }, new CallableStatementCallback<Boolean>() {
            public Boolean doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                callableStatement.execute();
                return callableStatement.getInt(1)==1;
            }
        });
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
