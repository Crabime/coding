package cn.crabime.practice.mybatis.handler;

import org.apache.ibatis.type.BooleanTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BooleanZeroTypeHandler extends BooleanTypeHandler {

    @Override
    public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
        parameter = parameter == null ? false : parameter;
        super.setParameter(ps, i, parameter, jdbcType);
    }
}
