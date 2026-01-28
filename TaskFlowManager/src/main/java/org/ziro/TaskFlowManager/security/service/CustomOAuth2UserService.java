package org.ziro.TaskFlowManager.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.ziro.TaskFlowManager.model.ERole;
import org.ziro.TaskFlowManager.model.User;
import org.ziro.TaskFlowManager.repository.UserRepository;
import org.ziro.TaskFlowManager.security.UserDetailsImpl;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OAuth2AuthenticationException(e.getMessage());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        String email;
        if ("github".equals(registrationId)) {

            email= oAuth2User.getAttribute("email") != null ?
                    oAuth2User.getAttribute("email") :
                    oAuth2User.getAttribute("login") + "@github.com";
        } else {
            email = oAuth2User.getAttribute("email");
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            user = updateExistingUser(user,oAuth2User);
        } else {
            user= registerNewUser(oAuth2UserRequest,oAuth2User);
        }

        return UserDetailsImpl.build(user, oAuth2User.getAttributes());

    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        User user = new User();
        user.setEmail(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        user.setProviderId(oAuth2User.getName());

        String githubLogin = oAuth2User.getAttribute("login");
        if (githubLogin == null) githubLogin = oAuth2User.getAttribute("name");

        String uniqueUsername = githubLogin;
        int counter = 1;

        while (userRepository.existsByUsername(uniqueUsername)) {
            uniqueUsername = githubLogin + "_" + counter;
            counter++;
        }

        user.setUsername(uniqueUsername);

        String email = oAuth2User.getAttribute("email");
        if (email==null && "github".equals(oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
            email = oAuth2User.getAttribute("login") + "@github.com";
        }
        user.setEmail(email);

        user.setRoles(Set.of(ERole.ROLE_USER));
        return userRepository.save(user);

    }

    private User updateExistingUser(User user, OAuth2User oAuth2User) {
        String name = oAuth2User.getAttribute("name");
        if (name == null) {
            name = oAuth2User.getAttribute("login");
        }
        return userRepository.save(user);
    }


}
