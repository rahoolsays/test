package com.simpaisa.portal.entity.mysql.merchant;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MerchantMapperIdAndName implements RowMapper<MerchantIdAndName> {
    @Override
    public MerchantIdAndName mapRow(ResultSet rs, int i) throws SQLException {
        MerchantIdAndName merchant = null;
        try{
            merchant = new MerchantIdAndName();
            merchant.setId(rs.getLong("merchantId"));
            merchant.setFirstName(rs.getString("firstName"));
            merchant.setLastName(rs.getString("lastName"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return merchant;
    }
}
