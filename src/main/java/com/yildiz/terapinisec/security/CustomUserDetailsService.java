package com.yildiz.terapinisec.security;

import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {


        User user = userRepository.findByUserNameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));


        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getUserRole().name());


        return new CustomUserDetails(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
