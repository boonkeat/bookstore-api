package com.bookstore.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class SecurityConfig {
    /*@Configuration
    @Order(1)
    public class BookstoreAPISecurityConfig extends WebSecurityConfigurerAdapter {
            @Override
        protected void configure(HttpSecurity http) throws Exception {
            List<String> PATHS_WEB_PERMIT_ALL = new ArrayList<String>();
            PATHS_WEB_PERMIT_ALL.add("/bookstore/login");

                http.authorizeRequests()
                        .antMatchers("/").hasAnyAuthority("USER", "ADMIN")
                        .antMatchers("/add").hasAnyAuthority("USER", "ADMIN")
                        .antMatchers("/update/**").hasAnyAuthority("USER", "ADMIN")
                        .antMatchers("/delete/**").hasAuthority("ADMIN")
                        .antMatchers(StringUtils.toStringArray(PATHS_WEB_PERMIT_ALL)).permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .formLogin().permitAll()
                        .and()
                        .logout().permitAll()
                        .and()
                        .exceptionHandling().accessDeniedPage("/403")
                ;
        }
    }*/

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }
}
