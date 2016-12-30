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
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import service.base.AbstractTelecomServiceImp;
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
public class OracleServiceImp extends AbstractTelecomServiceImp {

    @Autowired
    public OracleServiceImp(@Qualifier(value = "oracleJdbcTemplate") JdbcTemplate oracleJdbcTemplate) {
        this.jdbcTemplate = oracleJdbcTemplate;
        initPlans();
    }

}
