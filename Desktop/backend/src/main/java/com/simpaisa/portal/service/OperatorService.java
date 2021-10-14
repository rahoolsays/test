package com.simpaisa.portal.service;

import com.simpaisa.portal.entity.mysql.operator.Operator;
import com.simpaisa.portal.entity.mysql.operator.OperatorIdAndName;
import com.simpaisa.portal.entity.mysql.productconfiguration.ProductConfiguration;
import com.simpaisa.portal.repository.interfaces.OperatorRepository;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.simpaisa.portal.utility.Utility.SQL_DESC;

@Service
public class OperatorService {
    @Autowired
    private OperatorRepository operatorRepository;



    public List<OperatorIdAndName> getAllOperators(){
        List<OperatorIdAndName> operatorIdAndNames = null;
        try{
            operatorIdAndNames= operatorRepository.getAllOperators();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return operatorIdAndNames;
    }

    public List<OperatorIdAndName> getAllOperatorsNotConfigured(int ProductId){
        List<OperatorIdAndName> operatorIdAndNames = null;
        try{
            operatorIdAndNames= operatorRepository.getAllOperatorsNotConfigured(ProductId);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return operatorIdAndNames;
    }

    public OperatorIdAndName findByOperatorId(int OperatorId){
        OperatorIdAndName operatorIdAndNames = null;
        try{
            operatorIdAndNames= operatorRepository.findByOperatorId(OperatorId);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return operatorIdAndNames;
    }
    public Operator findOperatorId(int OperatorId){
        Operator operator = null;
        try{
            operator= operatorRepository.findOperatorId(OperatorId,Utility.JDBC_LIVE);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return operator;
    }

    public Page<Operator> listOperators(String orderBy, String direction, int pageNo, int size){
        Page<Operator> operator = null;
        Pageable pageable = null;

        try{
            if(direction.equalsIgnoreCase(SQL_DESC)){
                pageable =  PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC,orderBy));
            }
            else {
                pageable =  PageRequest.of(pageNo, size, Sort.by(Sort.Direction.ASC,orderBy));
            }
            operator= operatorRepository.listOperators(pageable,Utility.JDBC_LIVE);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return operator;
    }

    public Operator save(Operator operator) {
        Operator result = null;
        System.out.println(operator);
        try{
            if(operator!=null){
                if(operator.getOperatorID()!=null && operator.getOperatorID()>0){
                    operator.setUpdatedDate(new Date());
                }else {
                    operator.setCreatedDate(new Date());
                }
            }
            operatorRepository.save(operator,Utility.JDBC_LIVE);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  result;
    }
}
