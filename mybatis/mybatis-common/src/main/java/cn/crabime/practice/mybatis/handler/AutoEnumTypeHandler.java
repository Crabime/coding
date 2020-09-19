package cn.crabime.practice.mybatis.handler;

import cn.crabime.practice.mybatis.EnumCode;
import cn.crabime.practice.mybatis.Grade;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(value = JdbcType.INTEGER)
@MappedTypes(Grade.class)
public class AutoEnumTypeHandler extends BaseTypeHandler<Grade> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Grade parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public Grade getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return (Grade) codeOf(Grade.class, code);
    }

    @Override
    public Grade getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return (Grade) codeOf(Grade.class, code);
    }

    @Override
    public Grade getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return (Grade) codeOf(Grade.class, code);
    }

    private <E extends EnumCode> EnumCode codeOf(Class<E> clazz, int code) {
        E[] enumConstants = clazz.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}
