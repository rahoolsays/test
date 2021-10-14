package com.simpaisa.portal.service.bank;

import com.simpaisa.portal.entity.mysql.bank.Bank;
import com.simpaisa.portal.repository.bank.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    BankRepository bankRepository;

    public List<Bank> getAll(){
        List<Bank> banks = null;
        try{
            banks = bankRepository.getAll();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return banks;
    }
}
