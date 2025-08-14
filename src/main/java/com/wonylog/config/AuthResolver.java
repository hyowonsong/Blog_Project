package com.wonylog.config;

import com.wonylog.config.data.UserSession;
import com.wonylog.domain.Session;
import com.wonylog.exception.UnAuthorized;
import com.wonylog.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    // supportsParameter(MethodParameter parameter) : 이 ArgumentResolver 가 어떤 파라미터 타입을 지원할지 결정
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    // 목적: 컨트롤러에 전달될 파라미터 객체를 실제로 만들어 반환
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = webRequest.getHeader("Authorization");
        if (accessToken == null || accessToken.equals("")) {
            throw new UnAuthorized();
        }

        // 데이터베이스 사용자 확인작업이 필요하다.
        Session session = sessionRepository.findByAccessToken(accessToken)
               .orElseThrow(UnAuthorized::new);


        return new UserSession(session.getUser().getId());
    }
}
