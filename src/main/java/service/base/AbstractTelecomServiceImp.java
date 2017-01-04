package service.base;

import com.alibaba.fastjson.JSONObject;
import oracle.jdbc.OracleTypes;
import org.apache.commons.collections.map.HashedMap;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import util.Util;

import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by root on 12/30/16.
 */
public class AbstractTelecomServiceImp implements BaseTelecomService {

    protected List<JSONObject> plans = new ArrayList<JSONObject>();

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(30, 100, 1, TimeUnit.DAYS, new ArrayBlockingQueue<Runnable>(1000000));

    protected JdbcTemplate jdbcTemplate;

    protected void initPlans() {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(
                "SELECT PLANID, DESCRIPTION FROM PLAN"
        );

        while (rowSet.next()) {
            JSONObject object = new JSONObject();
            object.put("id", rowSet.getString(1));
            object.put("description", rowSet.getString(2));
            plans.add(object);
        }
    }

    public boolean verifyUser(final String phoneId, final String password) {
        final String verifyUserProcedure = "{call telecomPLSQL.UserISExsist(?,?,?)}";
        boolean userexsist =
                (Boolean) jdbcTemplate.execute(new CallableStatementCreator() {
                    public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                        CallableStatement callableStatement = connection.prepareCall(verifyUserProcedure);
                        callableStatement.setString(1, phoneId);
                        callableStatement.setString(2, password);
                        callableStatement.registerOutParameter(3, OracleTypes.NUMBER);
                        return callableStatement;
                    }
                }, new CallableStatementCallback() {

                    public Object doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                        callableStatement.execute();
                        return callableStatement.getInt(3) == 1;
                    }
                });
        return userexsist;
    }

    public JSONObject getBalance(final String phoneId) {
        final String getBalanceProcedure = "{call TELECOMPLSQL.queryBalance(?,?,?)}";
        Map<String, Object> Userbanance =
                jdbcTemplate.execute(new CallableStatementCreator() {
                    public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                        CallableStatement callableStatement = connection.prepareCall(getBalanceProcedure);
                        callableStatement.setString(1, phoneId);
                        callableStatement.registerOutParameter(2, OracleTypes.NUMBER);
                        callableStatement.registerOutParameter(3, Types.CHAR);
                        return callableStatement;
                    }
                }, new CallableStatementCallback<Map<String, Object>>() {
                    public Map<String, Object> doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                        callableStatement.execute();
                        Map<String, Object> map = new HashedMap();
                        String planId = callableStatement.getString(3).trim();
                        map.put("balance", callableStatement.getDouble(2));
                        int pId = Integer.parseInt(planId);
                        map.put("plan", plans.get(pId));
                        return map;
                    }
                });
        return (JSONObject) JSONObject.toJSON(Userbanance);
    }

    public boolean recharge(final String phoneId, final Integer amount) {
        final String rechargeProcesure = "{call TELECOMPLSQL.userRecharge(?,?,?)}";
        return jdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                CallableStatement callableStatement = connection.prepareCall(rechargeProcesure);
                callableStatement.setString(1, phoneId);
                callableStatement.setDouble(2, amount);
                callableStatement.registerOutParameter(3, OracleTypes.NUMBER);
                return callableStatement;
            }
        }, new CallableStatementCallback<Boolean>() {
            public Boolean doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                callableStatement.execute();
                return callableStatement.getInt(3) == 1;
            }
        });
    }

    public boolean recharge(final String phoneId, final Integer amount, final long[] times) {
        final String rechargeProcesure = "{call TELECOMPLSQL.userRecharge(?,?,?)}";
        return jdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                CallableStatement callableStatement = connection.prepareCall(rechargeProcesure);
                callableStatement.setString(1, phoneId);
                callableStatement.setDouble(2, amount);
                callableStatement.registerOutParameter(3, OracleTypes.NUMBER);
                return callableStatement;
            }
        }, new CallableStatementCallback<Boolean>() {
            public Boolean doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                long start = System.currentTimeMillis();
                callableStatement.execute();
                long end = System.currentTimeMillis();
                times[0] = end - start;
                return callableStatement.getInt(3) == 1;
            }
        });
    }

    public List<JSONObject> getRechargeRecord(final String phoneId, final Integer begin) {
        final String getRechargeRecordProcesure = "{call  TELECOMPLSQL.queryUserRechargeRecord(?,?,?)}";
        List<JSONObject> rechargeRecords = jdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                CallableStatement callStatement = connection.prepareCall(getRechargeRecordProcesure);
                callStatement.setString(1, phoneId);
                callStatement.setInt(2, begin);
                callStatement.registerOutParameter(3, OracleTypes.CURSOR);
                return callStatement;
            }
        }, new CallableStatementCallback<List<JSONObject>>() {

            public List<JSONObject> doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                List<JSONObject> records = new ArrayList<JSONObject>();
                callableStatement.execute();
                ResultSet rs = (ResultSet) callableStatement.getObject(3);

        /*        for(int i=0;i<rs.getMetaData().getColumnCount();i++)
                {
                    System.out.println(rs.getMetaData().getColumnName(i+1));
                }*/

                while (rs.next()) {
                    Map<String, Object> record = new HashedMap();
                    Timestamp date = rs.getTimestamp(1);
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    String dateString = df.format(date);
                    record.put("time", dateString);
                    record.put("amount", rs.getDouble(2));
                    JSONObject jsonRecord = (JSONObject) JSONObject.toJSON(record);
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
                = jdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                CallableStatement callableStatement = connection.prepareCall(getCallRecordsProcessure);
                callableStatement.setString(1, phoneId);
                callableStatement.setInt(2, begin);
                callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
                return callableStatement;
            }
        }, new CallableStatementCallback<List<JSONObject>>() {
            public List<JSONObject> doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                List<JSONObject> records = new ArrayList<JSONObject>();
                callableStatement.execute();
                ResultSet rs = (ResultSet) callableStatement.getObject(3);
           /*     ResultSetMetaData metaData = rs.getMetaData();
                for(int i=0;i<metaData.getColumnCount();i++)
                {
                    System.out.println(metaData.getColumnName(i+1));
                }*/
                while (rs.next()) {
                    Map<String, Object> record = new HashedMap();
                    record.put("caller", rs.getString(1));
                    record.put("called", rs.getString(2));
                    Date date = rs.getDate(3);
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    record.put("call_time", df.format(date));
                    record.put("dur_time", rs.getInt(4));
                    record.put("cost", rs.getDouble(5));
                    JSONObject JSONCallingRecord = (JSONObject) JSONObject.toJSON(record);
                    records.add(JSONCallingRecord);
                }
                return records;
            }
        });
        return callingRecords;
    }

    public Boolean userHaveACall(final String caller, final String called, final Date callingTime, final Integer duration) {
        return jdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                String haveCallProcesure = "{call TELECOMPLSQL.userHaveCall(?,?,?,?,?)}";
                CallableStatement callableStatement = connection.prepareCall(haveCallProcesure);
                callableStatement.setString(1, caller);
                callableStatement.setString(2, called);
                callableStatement.setDate(3, new java.sql.Date(callingTime.getTime()));
                callableStatement.setInt(4, duration);
                callableStatement.registerOutParameter(5, OracleTypes.NUMBER);
                return callableStatement;
            }
        }, new CallableStatementCallback<Boolean>() {
            public Boolean doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                callableStatement.execute();
                return callableStatement.getInt(5) == 1;
            }
        });
    }

    public Boolean userHaveACall(final String caller, final String called, final Date callingTime, final Integer duration, final long[] times) {
        return jdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                String haveCallProcesure = "{call TELECOMPLSQL.userHaveCall(?,?,?,?,?)}";
                CallableStatement callableStatement = connection.prepareCall(haveCallProcesure);
                callableStatement.setString(1, caller);
                callableStatement.setString(2, called);
                callableStatement.setDate(3, new java.sql.Date(callingTime.getTime()));
                callableStatement.setInt(4, duration);
                callableStatement.registerOutParameter(5, OracleTypes.NUMBER);
                return callableStatement;
            }
        }, new CallableStatementCallback<Boolean>() {
            public Boolean doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                long start = System.currentTimeMillis();
                callableStatement.execute();
                long end = System.currentTimeMillis();
                times[0] = end - start;
                return callableStatement.getInt(5) == 1;
            }
        });
    }

    public Boolean checkUserCanCall(final String caller) {
        return jdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                String checkUserprocess = "{? = call TELECOMPLSQL.checkUserCanCall(?)";
                CallableStatement callableStatement = connection.prepareCall(checkUserprocess);
                callableStatement.registerOutParameter(1, OracleTypes.NUMBER);
                callableStatement.setString(2, caller);
                return callableStatement;
            }
        }, new CallableStatementCallback<Boolean>() {
            public Boolean doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                callableStatement.execute();
                return callableStatement.getInt(1) == 1;
            }
        });
    }

    public List<JSONObject> getCallTotalTime(final Date from, final Date to) {
        final String getCallTTProcedure = "{call TELECOMPLSQL.queryCallTotalTime(?,?,?)}";
        List<JSONObject> callingTotalTime
                = jdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                CallableStatement callableStatement = connection.prepareCall(getCallTTProcedure);
                callableStatement.setDate(1, new java.sql.Date(from.getTime()));
                callableStatement.setDate(2, new java.sql.Date(to.getTime()));
                callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
                return callableStatement;
            }
        }, new CallableStatementCallback<List<JSONObject>>() {
            public List<JSONObject> doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                List<JSONObject> ttrecords = new ArrayList<JSONObject>();
                callableStatement.execute();
                ResultSet rs = (ResultSet) callableStatement.getObject(3);
                while (rs.next()) {
                    Map<String, Object> record = new HashedMap();
                    record.put("totalTime", rs.getInt(1));
                    Date date = rs.getDate(2);
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    record.put("time", df.format(date));
                    JSONObject JSONTTRecord = (JSONObject) JSONObject.toJSON(record);
                    ttrecords.add(JSONTTRecord);
                }
                return ttrecords;
            }
        });
        return callingTotalTime;
    }

    public List<JSONObject> getCallTotalAmount(final Date from, final Date to) {
        final String getCallTAProcedure = "{call TELECOMPLSQL.queryCallTotalAmount(?,?,?)}";
        List<JSONObject> callingTotalAmount
                = jdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                CallableStatement callableStatement = connection.prepareCall(getCallTAProcedure);
                callableStatement.setDate(1, new java.sql.Date(from.getTime()));
                callableStatement.setDate(2, new java.sql.Date(to.getTime()));
                callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
                return callableStatement;
            }
        }, new CallableStatementCallback<List<JSONObject>>() {
            public List<JSONObject> doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                List<JSONObject> tarecords = new ArrayList<JSONObject>();
                callableStatement.execute();
                ResultSet rs = (ResultSet) callableStatement.getObject(3);
                while (rs.next()) {
                    Map<String, Object> callingTotalTimeRecord = new HashedMap();
                    Double amount = rs.getDouble(2);
                    callingTotalTimeRecord.put("amount", amount.intValue());
                    Date date = rs.getDate(1);
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    callingTotalTimeRecord.put("time", df.format(date));
                    JSONObject JSONTARecord = (JSONObject) JSONObject.toJSON(callingTotalTimeRecord);
                    tarecords.add(JSONTARecord);
                }
                return tarecords;
            }
        });
        return callingTotalAmount;
    }

    public List<JSONObject> getCallDuration(final Integer from, final Integer to) {
        final String getCDProcedure = "{call TELECOMPLSQL.queryCallDuration(?,?,?)}";
        List<JSONObject> calldurationRecords
                = jdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                CallableStatement callableStatement = connection.prepareCall(getCDProcedure);
                callableStatement.setInt(1, from);
                callableStatement.setInt(2, to);
                callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
                return callableStatement;
            }
        }, new CallableStatementCallback<List<JSONObject>>() {
            public List<JSONObject> doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                List<JSONObject> cdrecords = new ArrayList<JSONObject>();
                callableStatement.execute();
                ResultSet rs = (ResultSet) callableStatement.getObject(3);
                while (rs.next()) {
                    Map<String, Object> record = new HashedMap();
                    record.put("duration", rs.getInt(1));
                    record.put("quantity", rs.getInt(2));
                    JSONObject JSONCDRecord = (JSONObject) JSONObject.toJSON(record);
                    cdrecords.add(JSONCDRecord);
                }
                return cdrecords;
            }
        });
        return calldurationRecords;
    }

    public List<JSONObject> getNewUserCount(final Date from, final Date to) {

        final String getNUCProcedure = "{call TELECOMPLSQL.queryNewUserQ(?,?,?)}";
        List<JSONObject> newUsersRecords
                = jdbcTemplate.execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                CallableStatement callableStatement = connection.prepareCall(getNUCProcedure);
                callableStatement.setDate(1, new java.sql.Date(from.getTime()));
                callableStatement.setDate(2, new java.sql.Date(to.getTime()));
                callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
                return callableStatement;
            }
        }, new CallableStatementCallback<List<JSONObject>>() {
            public List<JSONObject> doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                List<JSONObject> nucrecords = new ArrayList<JSONObject>();
                callableStatement.execute();
                ResultSet rs = (ResultSet) callableStatement.getObject(3);
                while (rs.next()) {
                    Map<String, Object> record = new HashedMap();
                    record.put("time", rs.getString(1));
                    record.put("quantity", rs.getInt(2));
                    JSONObject JSONNUCRecord = (JSONObject) JSONObject.toJSON(record);
                    nucrecords.add(JSONNUCRecord);
                }
                return nucrecords;
            }
        });
        return newUsersRecords;
    }

    public List<JSONObject> getNewUserCountWithOutPLSQL(final Date from, final Date to) {
        String sql = "SELECT registermon,count(registermon)\n" +
                "    FROM\n" +
                "    (SELECT to_char(t1.registerdate,'yyyy-MM') registermon \n" +
                "    FROM (SELECT (RegisterTime+0) registerdate\n" +

                "\t\t  FROM TelecomUser)t1\n" +
                "\tWHERE trunc(t1.registerdate,'MM')>=trunc(?,'MM') AND\n" +
                "    trunc(t1.registerdate,'MM')<=trunc(?,'MM'))t2\n" +
                "    GROUP BY t2.registermon";
        return jdbcTemplate.query(sql,
                new Object[]{new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime())},
                new int[]{OracleTypes.DATE, OracleTypes.DATE},
                new RowMapper<JSONObject>() {
                    public JSONObject mapRow(ResultSet resultSet, int i) throws SQLException {
                        JSONObject object = new JSONObject();
                        object.put("time", resultSet.getString(1));
                        object.put("quantity", resultSet.getInt(2));
                        return object;
                    }
                });
    }

    public JSONObject testUpdateInfo() {
        JSONObject result = new JSONObject();
        long start, end, all;
        String sql = "UPDATE TELECOMUSER SET REMAININGCALLTIME = 1001";

        start = System.currentTimeMillis();
        jdbcTemplate.execute(sql);
        jdbcTemplate.execute("COMMIT");
        end = System.currentTimeMillis();

        all = end - start;

        result.put("updateTime", all);
        return result;
    }

    public List<String> getAllUsers() {
        List<String> result = new ArrayList<String>(1000000);
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(
                "SELECT PHONENUMBER FROM NOVA.TELECOMUSER"
        );
        while (rowSet.next())
            result.add(rowSet.getString(1));

        return result;
    }

    public JSONObject testRechargeMultiThread(int num) {
        JSONObject result = new JSONObject();
        long start, end, all;

        start = System.currentTimeMillis();
        List<String> phoneNumbers = getAllUsers();
        end = System.currentTimeMillis();
        all = end - start;
        result.put("queryUsersTime", all);

        final int[] chargeAmount = new int[]{
                10, 20, 30, 50, 100, 200, 500, 1000
        };
        final int len = chargeAmount.length;

        final int[] cnt = {0};

        start = System.currentTimeMillis();
        for (final String phoneNum : phoneNumbers) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        cnt[0]++;
                        if (cnt[0] % 1000 == 0)
                            System.out.println("At " + cnt[0]);
                    }
                    recharge(phoneNum, chargeAmount[Util.randIntBetween(0, len)]);
                }
            });
        }

        executor.shutdown();
        try {
            boolean loop;
            do {
                loop = !executor.awaitTermination(500, TimeUnit.MILLISECONDS);
            } while (loop);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        end = System.currentTimeMillis();
        all += end - start;

        result.put("chargeTime", end - start);
        result.put("totalTime", all);
        return result;
    }

    public JSONObject testRecharge(int num) {
        JSONObject result = new JSONObject();
        long start, end, all;

        start = System.currentTimeMillis();
        List<String> phoneNumbers = getAllUsers();
        end = System.currentTimeMillis();
        all = end - start;
        result.put("queryUsersTime", all);

        int[] chargeAmount = new int[]{
                10, 20, 30, 50, 100, 200, 500, 1000
        };
        int len = chargeAmount.length;

        long chargeTime = 0;
        long[] times = new long[1];
        int cnt = 0;

        for (int i = 0; i < num; i++) {
            for (String phoneNum : phoneNumbers) {
                cnt++;
                recharge(phoneNum, chargeAmount[Util.randIntBetween(0, len)], times);
                chargeTime += times[0];
                if (cnt % 2000 == 0)
                    System.out.println("At " + cnt);
            }
        }

        result.put("chargeTime", chargeTime);
        result.put("totalTime", all + chargeTime);
        return result;
    }

    public JSONObject testCall() {
        JSONObject result = new JSONObject();
        long start, end, all;

        start = System.currentTimeMillis();
        List<String> phoneNumbers = getAllUsers();
        end = System.currentTimeMillis();
        all = end - start;
        result.put("queryUsersTime", all);

        int len = phoneNumbers.size();

        long chargeTime = 0;
        long[] times = new long[1];

        for (int i = 0; i < len / 2; i++) {
            String from = phoneNumbers.get(i);
            String to = phoneNumbers.get(len - 1 - i);
            userHaveACall(from, to, new Date(), Util.randIntBetween(60, 60 * 10 + 1), times);
            if (i % 2000 == 0)
                System.out.println("At " + i);
        }

        result.put("chargeTime", times[0]);
        result.put("totalTime", all + times[0]);
        return result;
    }

    public JSONObject testCallMultiThread() {
        JSONObject result = new JSONObject();
        long start, end, all;

        start = System.currentTimeMillis();
        final List<String> phoneNumbers = getAllUsers();
        end = System.currentTimeMillis();
        all = end - start;
        result.put("queryUsersTime", all);

        final int len = phoneNumbers.size();

        start = System.currentTimeMillis();
        for (int i = 0; i < len / 2; i++) {
            final int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        if (finalI % 2000 == 0)
                            System.out.println("At " + finalI);
                    }

                    String from = phoneNumbers.get(finalI);
                    String to = phoneNumbers.get(len - 1 - finalI);
                    userHaveACall(from, to, new Date(), Util.randIntBetween(60, 60 * 10 + 1));
                }
            });

        }

        executor.shutdown();
        try {
            boolean loop;
            do {
                loop = !executor.awaitTermination(500, TimeUnit.MILLISECONDS);
            } while (loop);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        end = System.currentTimeMillis();

        all += end - start;

        result.put("generateCallTime", end - start);
        result.put("totalTime", all);
        return result;
    }
}
