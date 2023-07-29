package com.bookstore.app.service.impl;

import com.bookstore.app.model.User;
import com.bookstore.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author boonkeat.poh
 * @since 20 Jul 2023
 */
@Service
public class BookstoreUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Finding user -> " + username);
        User user = userService.findByUsername(username);
        if (user == null) {
            System.out.println("Unable to find user by username!" + username);
            throw new UsernameNotFoundException("User not found!");
        }
        return user;
    }

    @Autowired
    private UserRepository userService;
}
