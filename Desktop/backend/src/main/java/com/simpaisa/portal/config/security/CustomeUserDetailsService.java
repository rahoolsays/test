package com.simpaisa.portal.config.security;

import com.simpaisa.portal.entity.mongo.Privilege;
import com.simpaisa.portal.entity.mongo.Role;
import com.simpaisa.portal.entity.mongo.User;
import com.simpaisa.portal.repository.interfaces.PrivilegeRepository;
import com.simpaisa.portal.repository.interfaces.RoleRepository;
import com.simpaisa.portal.repository.interfaces.UserRepository;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomeUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PrivilegeRepository privilegeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User findByToken(String token){
        return userRepository.findByToken(token);
    }
    public long countByBusinessName(String businessName){
        return userRepository.countByBusinessName(businessName);
    }
    public long countByWebsite(String website){
        return userRepository.countByWebsite(website);
    }
    public User saveUser(User user){
        User savedUser = null;
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
            Role role = roleRepository.findByRole(Utility.ROLE_MERCHANT);
            user.setRoles(new HashSet<>(Arrays.asList(role)));
            savedUser = userRepository.save(user);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println("savedUser = " + savedUser);

        return savedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user!=null){
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthenication(user, authorities);
        }else {
            throw new UsernameNotFoundException("username not found");
        }
        //return null;
    }


    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles){
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role)->{
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }


    private UserDetails buildUserForAuthenication(User user, List<GrantedAuthority> authorities){
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
