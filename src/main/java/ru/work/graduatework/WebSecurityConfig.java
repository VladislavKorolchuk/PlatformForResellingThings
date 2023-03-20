package ru.work.graduatework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig  {
    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login", "/register",
            "/ads"
    };

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails user = users
                .username("user@gmail.com")
                .password("password")
                .roles("USER")
                .build();
        UserDetails admin = users
                .username("admin@gmail.com")
                .password("admin")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.maximumSessions(1)//запрет на лог одного пользователя больше 1 раза
                        .maxSessionsPreventsLogin(true)) //запрет второго логина
                .csrf().disable()
                .authorizeHttpRequests((authz) ->
                        authz
                                .antMatchers(HttpMethod.OPTIONS).permitAll()
                                .mvcMatchers(AUTH_WHITELIST).permitAll()
                                .mvcMatchers("/ads/**", "/users/**").authenticated()
                                .mvcMatchers("/users/**").hasAnyAuthority("ADMIN", "USER")
                        // TODO: Разделение методов Юзера-Админа
                                                )
                .httpBasic(withDefaults())
                .cors();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(20);
    }
}

