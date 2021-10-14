package com.simpaisa.portal.entity.mysql.merchant;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MerchantMapper2  implements RowMapper<Merchant> {

    public Merchant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Merchant merchant = null;
        try {

            merchant = new Merchant();

            merchant.setFirstName(rs.getString("firstName"));
            merchant.setLastName(rs.getString("lastName"));
            merchant.setEmail(rs.getString("email"));
            merchant.setWebsite(rs.getString("website"));
            merchant.setId(rs.getLong("merchantID"));
            merchant.setPostbackUrl(rs.getString("postbackUrl"));
            merchant.setThreshold(rs.getInt("threshold"));
            merchant.setAllowWalletOtp(rs.getBoolean("allowWalletOtp"));
            merchant.setSync(rs.getBoolean("sync_call"));

        } catch(Exception exception) {
            exception.printStackTrace();
        }
        return merchant;
    }
}
