package com.stimulusfake.springboot.config.auth;

import com.stimulusfake.springboot.config.auth.dto.OAuthAttributes;
import com.stimulusfake.springboot.domain.user.User;
import com.stimulusfake.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;


// 이 클래스는 구글 로그인 이후 가져온 사용자의 정보(email, name, picture등)을 기반으로 가입 및 정보수정, 세션 저장 등의 기능을 지원


@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 1
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); // 2

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes()); // 3

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user)); // 4

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    // 구글 사용자 정보가 업데이트 되었을 때를 대비하여 update기능도 같이 구현. 사용자의 이름이나 사진이 변경되면 User 엔티이에도 반영됨.
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }


    /*
    1 registrationId : 현재 로그인 진행 중인 서비스를 구분하는 코드 (구글인지 네이버인지 카카오인지). 지금은 구글만 사용하는 불필요한
                        값이지만, 이후 네이버 로그인 연동 시에 구분하기 위해 사용
    2 userNameAttributeName : OAuth2 로그인 진행 시 키가 되는 필드값. Primary Key와 같은 의미. 구글의 경우 기본적으로 코드를 지원하지만
                         네이버, 카카오 등은 기본 지원하지 않음. 구글의 기본 코드는 "sub". 이후 네이버, 구글 로긍인을 동시 지원할 때 사용
    3 OAuthAttributes : OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스.
                        이후 네이버 등 다른 소셜 로그인도 이 클래스 사용
    4 SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스.
                - 왜 User 클래스를 쓰지 않고 새로 만들어서 쓰는지?
                -> 만약 User클래스를 그대로 사용했다면 다음과 같은 에러가 발생한다.
                         Failed to Convert from type [java.lang.Object] to type[byte[]] for value
                         'com.stimulusfake.springboot.domain.user.User@4a43a6'
                   이는 세션에 저장하기 위해 User 클래스를 세션에 저장하려고 하니, User 클래스에 직렬화를 구현하지 않았다는 의미의 에러.
                   직렬화를 하면 엔티티들간에 관계에서 따라오는 부수적인 문제들이 발생 할 수도 있어서 직력화기능을 가진 Dto를 하나 만드는 것이
                   이후 운영 및 유지보수에 도움이 된다.
     */

}