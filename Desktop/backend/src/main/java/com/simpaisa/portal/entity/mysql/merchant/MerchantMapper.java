package com.simpaisa.portal.entity.mysql.merchant;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MerchantMapper implements RowMapper<Merchant>{

	public Merchant mapRow(ResultSet rs, int rowNum) throws SQLException {
		Merchant merchant = null;
		try {
			

			merchant = new Merchant();
			merchant.setId(rs.getLong("merchantId"));
			merchant.setPostbackUrl(rs.getString("postbackUrl"));
			merchant.setThreshold(rs.getInt("threshold"));
			merchant.setAllowWalletOtp(rs.getBoolean("allowWalletOtp"));
			merchant.setSync(rs.getBoolean("sync_call"));
			merchant.setLogo(rs.getString("logo"));
			merchant.setWebsite(rs.getString("website"));
			merchant.setTitle(rs.getString("title"));
			merchant.setInstantDisburse(rs.getBoolean("inst_disburse"));
			merchant.setMaxAmountLimit(rs.getDouble("max_amount_disb"));

		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return merchant;
	}

}
