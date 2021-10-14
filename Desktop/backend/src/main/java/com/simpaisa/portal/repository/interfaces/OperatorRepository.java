package com.simpaisa.portal.repository.interfaces;

import com.simpaisa.portal.entity.mysql.NumberPrefix;
import com.simpaisa.portal.entity.mysql.operator.Operator;
import com.simpaisa.portal.entity.mysql.operator.OperatorIdAndName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OperatorRepository {
    public List<OperatorIdAndName> getAllOperators();
    public List<OperatorIdAndName> getAllOperatorsNotConfigured(int productId);
    public OperatorIdAndName findByOperatorId(int productId);
    public Operator findOperatorId(int productId,short environment);
    public Page<Operator> listOperators(Pageable pageable, short environment);
    public void save(Operator operator, short environment);
    public Page<NumberPrefix> listPrefix(Pageable pageable, short environment);
}
