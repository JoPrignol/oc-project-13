package com.your_car_your_way.chat_poc.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain( HttpSecurity http) throws Exception {

    return http.authorizeHttpRequests(auth -> {
                auth.requestMatchers("/login").permitAll();
                auth.anyRequest().authenticated();
            }).formLogin(Customizer.withDefaults())
              .build();
  }

  // Création d'utilisateurs inMemory pour les tests
  @Bean
  public UserDetailsService users() {
    UserDetails user = User.builder()
                          .username("user")
                          .password(passwordEncoder()
                          .encode("password"))
                          .roles("USER")
                          .build();
    UserDetails support = User.builder()
                              .username("support")
                              .password(passwordEncoder()
                              .encode("password"))
                              .roles("USER")
                              .build();
    return new InMemoryUserDetailsManager(user, support);
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
