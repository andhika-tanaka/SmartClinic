package com.mitrais.SmartClinic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.sql.DataSource;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AccessDeniedHandler accessDeniedHandler;

    private final DataSource dataSource;

    public SpringSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, AccessDeniedHandler accessDeniedHandler, DataSource dataSource) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.accessDeniedHandler = accessDeniedHandler;
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery("SELECT email, password, active FROM clinic_user WHERE email = ?")
                .authoritiesByUsernameQuery("SELECT email, roles from clinic_user WHERE email = ?")
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
        auth.
                inMemoryAuthentication()
                .withUser("user").password(bCryptPasswordEncoder.encode("user")).roles("USER")
                .and()
                .withUser("admin").password(bCryptPasswordEncoder.encode("admin")).roles("ADMIN");
    }

    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/assets/**","/index","/login/**","/system", "/about","/build/**","/css/**","/images/**","/js/**","/vendors/**","/fragments/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }
}
