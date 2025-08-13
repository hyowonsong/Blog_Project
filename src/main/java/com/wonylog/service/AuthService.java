package com.wonylog.service;


import com.wonylog.domain.Session;
import com.wonylog.domain.User;
import com.wonylog.exception.InvalidSigninInformation;
import com.wonylog.repository.UserRepository;
import com.wonylog.request.Login;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public String signin(Login login){
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);
        Session session = user.addSession();

        return session.getAccessToken();
    }
}