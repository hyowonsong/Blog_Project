package com.wonylog.service;


import com.wonylog.crypto.PasswordEncoder;

import com.wonylog.domain.User;
import com.wonylog.exception.AlreadyExistsEmailException;
import com.wonylog.exception.InvalidSigninInformation;
import com.wonylog.repository.UserRepository;
import com.wonylog.request.Signup;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.wonylog.domain.QUser.user;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    /**
    // 시큐리티 도입 위해 주석 처리
    @Transactional
    public Long signin(Login login) {
        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSigninInformation::new);

        PasswordEncoder encoder = new PasswordEncoder();

        var matches = encoder.matches(login.getPassword(), user.getPassword());
        if (!matches) {
            throw new InvalidSigninInformation();
        }

        return user.getId();
    }**/

    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        PasswordEncoder encoder = new PasswordEncoder();
        String encryptedPassword = encoder.encrpyt(signup.getPassword());

        var user = User.builder()
                .email(signup.getEmail())
                .password(encryptedPassword)
                .name(signup.getName())
                .build();
        userRepository.save(user);
    }
}