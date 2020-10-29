package cn.crabime.practice.mybatis.handler;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.type.DateTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class TruncDateTypeHandler extends DateTypeHandler {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
        Date date = DateUtils.truncate(parameter, Calendar.SECOND);
        super.setNonNullParameter(ps, i, date, jdbcType);
    }
}
