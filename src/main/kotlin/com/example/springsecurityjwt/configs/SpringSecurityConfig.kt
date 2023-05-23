package com.example.springsecurityjwt.configs

import com.example.springsecurityjwt.jwt.JwtAuthorizationFilter
import com.example.springsecurityjwt.services.CustomAuthenticationProvider
import com.example.springsecurityjwt.services.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
class SpringSecurityConfig(
    private val customUserDetailsService: CustomUserDetailsService,
    private val passwordEncoder: PasswordEncoder,
) {
    /**
     * 인증/인가 검사를 수행하지 않도록 설정
     */
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(
                "/",
                "/sign-up", // 회원가입 Endpoint
                "/sign-in", // 로그인 Endpoint
            )
        }
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic().disable()
            .csrf().disable()
            .formLogin().disable()

            // 그외 모든 경로는 어떤 식으로든 인증을 수행해야 한다
            .authorizeHttpRequests {
                it.requestMatchers("/**")
                it.anyRequest().authenticated()
            }
            .addFilterBefore(
                JwtAuthorizationFilter(
                    customUserDetailsService,
                    CustomAuthenticationProvider(customUserDetailsService, passwordEncoder),
                ),
                UsernamePasswordAuthenticationFilter::class.java,
            )


            // 세션은 사용하지 않음
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        return http.build()
    }

    @Bean
    fun authenticationProvider(): CustomAuthenticationProvider? {
        return CustomAuthenticationProvider(customUserDetailsService, passwordEncoder)
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
}
