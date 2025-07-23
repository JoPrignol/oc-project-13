package com.your_car_your_way.chat_poc.configurations;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  // @Bean
  // public CorsFilter corsFilter() {
  //     CorsConfiguration config = new CorsConfiguration();
  //     config.setAllowedOrigins(List.of("http://localhost:4200"));
  //     config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
  //     config.setAllowedHeaders(List.of("Content-Type", "Authorization"));
  //     config.setAllowCredentials(true);
  //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  //     source.registerCorsConfiguration("/**", config);
  //     return new CorsFilter(source);
  // }

  @Bean
  public CorsFilter corsFilter() {
      CorsConfiguration config = new CorsConfiguration();
      config.addAllowedOriginPattern("http://localhost:4200");
      config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
      config.setAllowedHeaders(List.of("*"));
      config.setAllowCredentials(true);
      config.setExposedHeaders(List.of("Set-Cookie"));
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", config);
      return new CorsFilter(source);
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return http
              .cors(Customizer.withDefaults())
              .csrf(csrf -> csrf.disable())
              .authorizeHttpRequests(auth -> auth
                      .requestMatchers("/api/login").permitAll()
                      .requestMatchers("/ws/**").authenticated()
                      .requestMatchers("/api/**").authenticated()
                      .anyRequest().permitAll()
              )
              .sessionManagement(session -> session
                      .maximumSessions(1)
                      .maxSessionsPreventsLogin(true)
              )
              .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                    BCryptPasswordEncoder passwordEncoder) {
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
      provider.setUserDetailsService(userDetailsService);
      provider.setPasswordEncoder(passwordEncoder);
      return new ProviderManager(provider);
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
