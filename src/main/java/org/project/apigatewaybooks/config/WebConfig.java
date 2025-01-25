package org.project.apigatewaybooks.config;


import lombok.RequiredArgsConstructor;
import org.project.apigatewaybooks.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthenticationFilter)
                .addPathPatterns("/books/**")
                .excludePathPatterns("/auth/**");
    }
}
