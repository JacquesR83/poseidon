package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.*;
import java.util.stream.Collectors;

@Service
// User wants to connect and is registered in database
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   @Override
   // Check repository credentials and returns them as UserDetails
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }

    // Methode initiale sans override dans " User "
//    // Check repository credentials and returns them
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user =  userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
//           return new org.springframework.security.core.userdetails.User(
//                   user.getUsername(),
//                   user.getPassword(),
//                   getAuthorities("ROLE_" + user.getRole())
////               List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
//           );
//    }

//    //method to
//    private Collection<?extends GrantedAuthority> getAuthorities(String role){
//        return Arrays.asList(new SimpleGrantedAuthority(role));
//    }

}
