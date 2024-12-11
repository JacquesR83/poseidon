package com.nnk.springboot.security;

import com.nnk.springboot.domain.Role;
import com.nnk.springboot.service.AuthenticationService;
import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.Base64;

import static com.nnk.springboot.domain.Role.ADMIN;
import static com.nnk.springboot.domain.Role.USER;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Configuration
@EnableWebSecurity

public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;

    @Autowired
    public ApplicationSecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    /**
     * Define authorizations per http request
     * @param http
     * @throws Exception
     */
    @Override //
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login", "/css/*", "/login/**").permitAll()
                .antMatchers("/admin/**").hasAuthority(ADMIN.name())
                .antMatchers(("/user/**")).hasAnyAuthority(USER.name(), ADMIN.name())
//                .antMatchers(HttpMethod.GET, "/user/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
//                .loginPage("/login");
                ;
    }

    /**
     * Configuration of AuthenticationManagerBuilder inherited from Spring Security allowing access
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
        //Links to AuthenticationService/UserDetailsService to verify DataBase content
        auth.userDetailsService(authenticationService);
    }

    /**
     * Encode password
     * @return
     */
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



//    /**
//     * Connect through in Memory User
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password(passwordEncoder().encode("user"))
//                .roles("USER")
//                ;
//    }



//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder = http
//                .getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(userDetailsService());
//        return authenticationManagerBuilder.build();
//    }


}
