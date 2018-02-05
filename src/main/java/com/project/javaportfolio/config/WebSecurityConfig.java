package com.project.javaportfolio.config;
import com.project.javaportfolio.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    private UserRepo userRepo;

    public WebSecurityConfig(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(UserRepo userRepo) {
		this.userRepo = userRepo;
		return new BCryptPasswordEncoder();
    }
    
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		
		System.out.println("Checking Authorizations...");
		
		http.
        authorizeRequests()
            .antMatchers("/resources/**", "/css/**", "/", "/index", "/overflow/**", "/scriptions/registration").permitAll()
            .antMatchers("/scriptions/admin/**").access("hasRole('ADMIN')")
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/scriptions/login")
            .permitAll()
            .and()
        .logout()
            .permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userRepo).passwordEncoder(bCryptPasswordEncoder(userRepo));
	}
}