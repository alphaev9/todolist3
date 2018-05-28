package com.alpha.repository.rdb.singleTable.typeHandler;

import com.alpha.repository.PersistId;
import com.alpha.repository.rdb.singleTable.RdbId;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RdbIDTypeHandler implements TypeHandler<PersistId> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, PersistId rdbId, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, rdbId.getRealId());
    }

    @Override
    public RdbId getResult(ResultSet resultSet, String s) throws SQLException {
        int id = resultSet.getInt(s);
        return new RdbId(id);
    }

    @Override
    public RdbId getResult(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt(i);
        return new RdbId(id);
    }

    @Override
    public RdbId getResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
