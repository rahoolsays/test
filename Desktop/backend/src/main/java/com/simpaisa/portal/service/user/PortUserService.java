package com.simpaisa.portal.service.user;

import com.simpaisa.portal.entity.mongo.PortalRole;
import com.simpaisa.portal.entity.mongo.PortalUser;
import com.simpaisa.portal.entity.mongo.User;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.repository.PortalUserRepository;
import com.simpaisa.portal.utility.Utility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.simpaisa.portal.utility.Utility.*;
import static com.simpaisa.portal.utility.Utility.DIRECTION;

@Service
public class PortUserService {

    @Autowired
    PortalUserRepository portalUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<PortalUser> getAll(Map<String, Object> request){

        int limit = 10;
        int pageNo = 0;
        String orderBy = "firstName";
        String email = null;
        Pageable pageable = null;

        if (request.containsKey(LIMIT) && StringUtils.isNumeric(request.get(LIMIT).toString())) {
            limit = Integer.valueOf(request.get(LIMIT).toString());

        }
        if (request.containsKey(Utility.PAGE_NO) && StringUtils.isNumeric(request.get(PAGE_NO).toString())) {
            pageNo = Integer.valueOf(request.get(PAGE_NO).toString());
        }
        if (request.containsKey(Utility.ORDER_BY) && StringUtils.isNotEmpty(request.get(ORDER_BY).toString())) {
            orderBy = request.get(ORDER_BY).toString();
        }
        if (request.containsKey(DIRECTION) && request.get(DIRECTION).toString().equalsIgnoreCase("desc"))
        {
            pageable = PageRequest.of(pageNo,limit, Sort.by(Sort.Direction.DESC,orderBy));
        }
        else {
            pageable = PageRequest.of(pageNo,limit, Sort.by(Sort.Direction.ASC,orderBy));
        }

        if(request.containsKey(EMAIL) && StringUtils.isNotEmpty(request.get(EMAIL).toString())){
            email = request.get(EMAIL).toString();
        }

        Page<PortalUser> users = null;

        try{
             users = portalUserRepository.findAllPageable(pageable, email);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public List<PortalRole> getAllRoles(){

        List<PortalRole> roles = null;

        try {
            roles = portalUserRepository.getAllRolesForUser();

        }
        catch(Exception e){
            e.printStackTrace();
        }

        return roles;
    }

    public Map<String, Object> saveUser(PortalUser user){

        Map<String, Object> response = null;

        try {
            PortalUser portalUser = portalUserRepository.findByEmail(user.getEmail());

            if (portalUser != null) {
                return getResponse(Responses.USER_EXISTS);
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
            user.setVerified(true);

            PortalUser savedUser = portalUserRepository.save(user);

            if(savedUser != null){
                response = getResponse(Responses.SUCCESS);
            }



        }
        catch (Exception e){
            e.printStackTrace();
            response = getResponse(Responses.SYSTEM_ERROR);
        }

        return response;
    }

    public Map<String, Object> updateUser(PortalUser user){

        Map<String, Object> response = null;

        try {

            PortalUser savedUser = portalUserRepository.save(user);

            if(savedUser != null){
                response = getResponse(Responses.SUCCESS);
            }



        }
        catch (Exception e){
            e.printStackTrace();
            response = getResponse(Responses.SYSTEM_ERROR);
        }

        return response;
    }

    public PortalUser getUser(String emailId){

        try {

            return portalUserRepository.findByEmail(emailId);

        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public Map<String, Object> deleteUser(PortalUser user){

        Map<String, Object> response = null;
        try {
            long count =  portalUserRepository.delete(user);

            if(count > 0){
                response = getResponse(Responses.SUCCESS);
            }
            else {
                response = getResponse(Responses.INVALID_CALL);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            response = getResponse(Responses.SYSTEM_ERROR);
        }
        return response;
    }
}
