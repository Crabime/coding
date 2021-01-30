package cn.crabime.practice.mybatis.handler;

import org.apache.ibatis.type.IntegerTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NullToZeroTypeHandler extends IntegerTypeHandler {

    @Override
    public void setParameter(PreparedStatement ps, int i, Integer parameter, JdbcType jdbcType) throws SQLException {
        parameter = parameter == null ? 0 : parameter;
        super.setParameter(ps, i, parameter, jdbcType);
    }
}
