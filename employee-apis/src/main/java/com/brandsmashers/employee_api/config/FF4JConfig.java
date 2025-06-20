package com.brandsmashers.employee_api.config;

import org.ff4j.FF4j;
import org.ff4j.web.FF4jDispatcherServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class FF4JConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().formLogin();
    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(
        User.withUsername("admin")
            .password(bCryptPasswordEncoder.encode("admin"))
            .roles("USER", "ADMIN")
            .build());
    return manager;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Create the FF4j Bean with some example features
  @Bean
  public FF4j getFF4j() {
    FF4j ff4j = new FF4j();

    // Enable auto-create for features if they are missing
    ff4j.setAutocreate(true);

    // Define sample features
    ff4j.createFeature("live-score-enabled");
    ff4j.createFeature("experimental-ui");

    return ff4j;
  }

  // Create the FF4jDispatcherServlet bean
  @Bean
  @ConditionalOnMissingBean
  public FF4jDispatcherServlet ff4jDispatcherServlet(FF4j ff4j) {
    FF4jDispatcherServlet ff4jDispatcherServlet = new FF4jDispatcherServlet();
    ff4jDispatcherServlet.setFf4j(ff4j);
    return ff4jDispatcherServlet;
  }

  @Bean
  public ServletRegistrationBean servletRegistrationBean(
      FF4jDispatcherServlet ff4jDispatcherServlet) {
    return new ServletRegistrationBean(ff4jDispatcherServlet, "/ff4j-web-console/*");
  }
}
