package com.cloudthat.elearningbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.cloudthat.elearningbackend.entity.User;
import com.cloudthat.elearningbackend.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	private static final String[] PUBLIC_URLS = {
			"/register", "/verifyRegistration**", "/resendVerifiytoken**","/login"
		};
    
	private static final String[] WHITE_LIST_URLS = {
			
		};
		
		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
			
		}
		
		@Bean
		public AuthenticationManager  authenticationProvider() {
			DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
			provider.setUserDetailsService(userDetailsService);
			provider.setPasswordEncoder(new BCryptPasswordEncoder());
			return new ProviderManager(provider);
		}
		
		
		@Bean
		SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			http.cors(cors -> cors.disable())
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authorize ->
				authorize
				.requestMatchers(PUBLIC_URLS).permitAll()
				.anyRequest().authenticated()
				)
			.httpBasic(Customizer.withDefaults());
			
			
			return http.build();
			
		}
}
