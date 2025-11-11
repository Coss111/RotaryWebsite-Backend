package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.model.User;
import com.rotarywebsite.backend.model.UserRole;
import com.rotarywebsite.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        // Process OAuth2 user information
        processOAuth2User(oAuth2User);
        
        return oAuth2User;
    }

    private void processOAuth2User(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String login = (String) attributes.get("login"); // GitHub username

        Optional<User> userOptional = userRepository.findByEmail(email);
        
        if (userOptional.isEmpty()) {
            // Register new user from OAuth2
            User newUser = new User();
            newUser.setEmail(email != null ? email : login + "@github.com");
            newUser.setRol(UserRole.MEMBER);
            newUser.setActivo(true);
            newUser.setFechaRegistro(LocalDateTime.now());
            newUser.setUltimoLogin(LocalDateTime.now());
            
            userRepository.save(newUser);
            
            // Here you could also create a Member record
            System.out.println("New user registered via OAuth2: " + email);
        } else {
            // Update existing user
            User user = userOptional.get();
            user.setUltimoLogin(LocalDateTime.now());
            userRepository.save(user);
        }
    }
}