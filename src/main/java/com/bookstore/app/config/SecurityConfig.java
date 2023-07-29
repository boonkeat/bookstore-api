package com.bookstore.app.config;


import com.bookstore.app.service.impl.BookstoreUserDetailsServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.issuer}")
    private String jwtIssuer;
    @Value("${jwt.type}")
    private String jwtType;
    @Value("${jwt.audience}")
    private String jwtAudience;
    @Resource
    private BookstoreUserDetailsServiceImpl userDetailsService;
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authz) -> authz
                                .requestMatchers("/bookstore/**").permitAll()
//                        .requestMatchers("/bookstore/user/add").permitAll()
//                        .requestMatchers("/bookstore/user/login").permitAll()
//                        .requestMatchers("/bookstore/**").authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder.encode("password"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder.encode("admin"))
//                .roles("USER", "ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//        return http.build();
//    }


}
