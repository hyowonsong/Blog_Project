package com.wonylog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    // private final SessionRepository sessionRepository;
    private final AppConfig appConfig;

    /**
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                // 여기에 이렇게 exclude 하면은 이것들은 제외가 된다.
                .addPathPatterns("/..","/")
                .excludePathPatterns("/error", "/favicon.ico", "/foo");
    }**/

    /**
     * // 스프링 시큐리티 사용시 삭제
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthResolver(sessionRepository, appConfig));
    }**/
}
