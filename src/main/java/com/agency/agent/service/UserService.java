package com.agency.agent.service;

import com.agency.agent.model.Request;
import com.agency.agent.model.Roles;
import com.agency.agent.model.Users;
import com.agency.agent.repository.RequestRepository;
import com.agency.agent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RequestRepository requestRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public List<Request> allRequest(Long userId){
        return requestRepository.findByUserId(userId);
    }

    public Users get(Long id) {
        return userRepository.findById(id).get();
    }

    public boolean checkRole(Users user, Set<Roles> roles){
        for (Roles role : roles) {
            if (role.getName().equals("ROLE_ADMIN")) {
                break;
            }
            else{
                return false;
            }
        }
        return true;
    }

    public boolean saveUser(Users user) {
        Users userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Roles(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

}
