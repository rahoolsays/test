package com.simpaisa.portal.entity.mysql.operator;



import com.simpaisa.portal.entity.mysql.operator.OperatorIdAndName;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperatorMapperIdAndName implements RowMapper<OperatorIdAndName> {
    @Override
    public OperatorIdAndName mapRow(ResultSet rs, int i) throws SQLException {
        OperatorIdAndName operator = null;
        try {
            operator = new OperatorIdAndName();
            operator.setId(rs.getLong("operatorID"));
            operator.setName(rs.getString("operatorName"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return operator;
    }
}