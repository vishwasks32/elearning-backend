package com.cloudthat.elearningbackend.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.cloudthat.elearningbackend.filter.JwtFilter;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Autowired
	private JwtFilter jwtFilter;
	
	private static final String[] PUBLIC_URLS = {
			"/register", 
			"/verifyRegistration**", 
			"/resendVerifiytoken**",
			"/login",
			"/error",
			"/api-docs",
			"/api-docs/**",
			"/swagger-ui/**",
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
				);
			
//		    .sessionManagement((session) -> session
//		            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		        );
			http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
			
			return http.build();
			
		}
}
