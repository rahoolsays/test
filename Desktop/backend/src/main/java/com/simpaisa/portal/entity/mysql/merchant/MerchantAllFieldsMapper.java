package com.simpaisa.portal.entity.mysql.merchant;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MerchantAllFieldsMapper implements RowMapper<Merchant> {
    @Override
    public Merchant mapRow(ResultSet rs, int i) throws SQLException {
        Merchant merchant = null;
        try {

            merchant = new Merchant();
//            merchant.setId(rs.getLong("merchantId"));
            merchant.setPostbackUrl(rs.getString("postbackUrl"));
            merchant.setThreshold(rs.getInt("threshold"));
            merchant.setAllowWalletOtp(rs.getBoolean("allowWalletOtp"));
            merchant.setSync(rs.getBoolean("sync_call"));
            merchant.setTitle(rs.getString("title"));
            merchant.setFirstName(rs.getString("firstName"));
            merchant.setLastName(rs.getString("lastName"));
            merchant.setWebsite(rs.getString("website"));
            merchant.setEmail(rs.getString("email"));


        } catch(Exception exception) {
            exception.printStackTrace();
        }
        return merchant;
    }
}
