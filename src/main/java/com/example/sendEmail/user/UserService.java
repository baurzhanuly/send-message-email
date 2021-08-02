package com.example.sendEmail.user;

import com.example.sendEmail.exceptions.ServiceException;
import com.example.sendEmail.registration.token.ConfirmationToken;
import com.example.sendEmail.registration.token.ConfirmationTokenService;
import com.example.sendEmail.utils.codes.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByEmail(s)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("user with email %s not found", s)));
    }

    public String signUpUser(User user) throws ServiceException {
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if (userExists){
            throw ServiceException.builder()
                    .message("user email exists")
                    .code(ErrorCode.ALREADY_EXISTS)
                    .build();
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.save(confirmationToken);
        return token;
    }

    public User enableUser(User user) throws ServiceException {
        if (user.getId() == null){
            throw ServiceException.builder()
                    .message("confirmationToken not found")
                    .code(ErrorCode.EMPTY_CODE)
                    .build();
        }
        return userRepository.save(user);
    }
}
