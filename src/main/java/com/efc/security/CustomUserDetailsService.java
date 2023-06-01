package com.efc.security;

import com.efc.dto.LoginDTO;
import com.efc.entity.User;
import com.efc.exception.PasswordNotFoundException;
import com.efc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("Invalid login");
        }

        return new UserPrincipal(user);
    }

    public void verifyUserCredentials(LoginDTO login) {
        UserDetails user = loadUserByUsername(login.getUsername());
        boolean passwordIsEqual =  SecurityConfig
                                    .passwordEncoder()
                                    .matches(login.getPassword(), user.getPassword());

        if(!passwordIsEqual) {
            throw new PasswordNotFoundException("Invalid password");
        }
    }
}
