package ru.work.graduatework.config;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

  @Autowired
  private DataSource dataSource;

  private static final String[] AUTH_WHITELIST = {
      "/swagger-resources/**",
      "/swagger-ui.html",
      "/v3/api-docs",
      "/webjars/**",
      "/login", "/register",
      "/ads"
  };

  @Bean
  public UserDetailsManager userDetailsService(DataSource dataSource) {
    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
    jdbcUserDetailsManager.setDataSource(dataSource);
    jdbcUserDetailsManager
        .setUserExistsSql("select email as username from users WHERE email=?");
    jdbcUserDetailsManager.setUsersByUsernameQuery(
        "select email as username,password,'true' from users where email=?");
    jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
        "select email as username, role as authority from users WHERE email=?");
    return jdbcUserDetailsManager;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http,
      DataSource dataSource) throws Exception {
    http
        .authenticationManager(http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService(dataSource))
            .passwordEncoder(passwordEncoder())
            .and()
            .build())
        .csrf().disable()
        .authorizeHttpRequests((authz) ->
            authz
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .mvcMatchers(AUTH_WHITELIST).permitAll()
                .mvcMatchers("/ads/**", "/users/**").authenticated()
                .mvcMatchers("/users/**").hasAnyAuthority("ADMIN", "USER")
        )
        .httpBasic(withDefaults())
        .cors();
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

