package com.simpaisa.portal.repository.interfaces;

import com.simpaisa.portal.entity.mysql.merchant.Merchant;
import com.simpaisa.portal.entity.mysql.merchant.MerchantIdAndName;
import com.simpaisa.portal.entity.mysql.merchant.MerchantMapperIdAndName;

import java.util.List;

public interface MerchantRepository {
    public Merchant isMerchantValid(long merchantId);
    public long insert(Merchant merchant, short environment);
    public Merchant findByEmail(String email,short environment);
    public Merchant findById(long id,String email, short environment);
    public Merchant findById(int id);
    public Merchant findById(Long id);
    public void updatePostBack(long id, String postbck);
    public void updateLogo(String merchantId, String logoUrl);
    public List<MerchantIdAndName> getAllMerchants();


}
