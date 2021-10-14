package com.simpaisa.portal.service;

import com.simpaisa.portal.entity.mongo.revenue.RevenueShare;
import com.simpaisa.portal.entity.mysql.NumberPrefix;
import com.simpaisa.portal.repository.interfaces.NumerPrefixRepository;
import com.simpaisa.portal.repository.interfaces.OperatorRepository;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.simpaisa.portal.utility.Utility.SQL_DESC;

@Service
public class PrefixService {

    @Autowired
    OperatorRepository prefixRepo;
    @Autowired
    NumerPrefixRepository numerPrefixRepo;


    public Page<NumberPrefix> listPrefixes(String orderBy, String direction, int pageNo, int size){
        Page<NumberPrefix> numberPrefixes = null;
        Pageable pageable = null;

        try{
            if(direction.equalsIgnoreCase(SQL_DESC)){
                pageable =  PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC,orderBy));
            }
            else {
                pageable =  PageRequest.of(pageNo, size, Sort.by(Sort.Direction.ASC,orderBy));
            }
            numberPrefixes= prefixRepo.listPrefix(pageable, Utility.JDBC_STAGING);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return numberPrefixes;
    }

    public void deleteById(Long id){
        try{
            numerPrefixRepo.deleteById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public NumberPrefix save(NumberPrefix prefix) {
        NumberPrefix result = null;
        try{

            result = numerPrefixRepo.save(prefix);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  result;
    }

    public NumberPrefix findById(Long id) {
        NumberPrefix result = null;
        try{
            result = numerPrefixRepo.findById(id).orElse(null);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

}
