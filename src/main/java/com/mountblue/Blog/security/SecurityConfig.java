package com.mountblue.Blog.security;

import com.mountblue.Blog.service.UserServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserServices();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(configurer ->
                            configurer
                                    .requestMatchers("/").permitAll()
                                    .requestMatchers("/viewpost").permitAll()
                                    .requestMatchers("/add-filters").permitAll()
                                    .requestMatchers("/search").permitAll()
                                    .requestMatchers("/filtered-blogs").permitAll()
                                    .requestMatchers("/register").permitAll()
                                    .requestMatchers("/addUser").permitAll()
                                    .requestMatchers("/Posts/{id}/comment").permitAll()
                                    .requestMatchers("/Updatepost/**").hasAnyRole("USER","ADMIN")
                                    .anyRequest().authenticated())
                        .formLogin(form ->
                        form
                                .loginPage("/loginform")
                                .loginProcessingUrl("/authenticateTheUser")
                                .defaultSuccessUrl("/")
                                .permitAll()
                )
        .logout(logout -> logout.permitAll().logoutSuccessUrl("/")

                )
                    .exceptionHandling(configurer ->
                            configurer
                                    .accessDeniedPage("/access-denied")

                    );
        return http.build();

    }


}