package com.neirinzaralwin.spring_ecommerce.service;
import com.neirinzaralwin.spring_ecommerce.dto.LoginUserDto;
import com.neirinzaralwin.spring_ecommerce.dto.RegisterUserDto;
import com.neirinzaralwin.spring_ecommerce.entity.User;
import com.neirinzaralwin.spring_ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    public User signup(RegisterUserDto input) {
        User user = new User();
                user.setName(input.getFullName());
                user.setEmail(input.getEmail());
                user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        User foundUser = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new BadCredentialsException("No user found with this email"));

        if (passwordEncoder.matches(input.getPassword(), foundUser.getPassword())) {
            return foundUser;
        } else {
            throw new BadCredentialsException("Wrong password");
        }
    }

}