package com.simpaisa.portal.repository.interfaces;

import com.simpaisa.portal.entity.mysql.reporting.Recursion;

import java.util.List;

public interface NewSubscribersRepository {
    public List<Recursion> newSubscribersList(long merchantId, String from_date, String to_date, String operatorID);
}
