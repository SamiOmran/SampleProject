package com.exalt.sampleproject.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.sql.DataSource;
import java.util.Locale;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter  {
    @Autowired DataSource dataSource;
    private final String PASSWORD = "pass";

    // Authorization
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/restaurants/**").hasAnyRole("ADMIN", "USER")
                    .antMatchers("/h2-console/**").hasRole("ADMIN")//permitAll()
                .and()
                    .csrf()
                    .disable()
                    .headers()
                    .frameOptions()
                    .disable()
                .and()
                .httpBasic();
    }

    /**
     * use this method when using h2 database
     */
    //Authentication
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .withUser(User.withUsername("user")
                        .password(PASSWORD)
                        .roles("USER"))
                .withUser(User.withUsername("admin")
                        .password(PASSWORD)
                        .roles("ADMIN"));
    }

    /**
     * use this method when using mysql database
     */
//Authentication
//    @Autowired
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder
//                .jdbcAuthentication()
//                .dataSource(dataSource);
//    }

    @Bean
    public PasswordEncoder getPassWordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
