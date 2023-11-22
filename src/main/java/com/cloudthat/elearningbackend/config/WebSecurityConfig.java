package com.cloudthat.elearningbackend.config;



import java.util.Arrays;

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


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
			"/api/v1/**" // adding this for temporary frontend development ** comment it out afterwards fix on line 118**
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
//			http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
			http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authorize ->
				authorize
				.requestMatchers(PUBLIC_URLS).permitAll()
				.anyRequest().authenticated()
				);
			
			http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
			
			return http.build();
			
		}
		
//		@Bean
//		CorsConfigurationSource corsConfigurationSource() {
//			CorsConfiguration configuration = new CorsConfiguration();
//		    configuration.setAllowedOrigins(Arrays.asList("*"));
//		    configuration.setAllowedMethods(Arrays.asList("*"));
//		    configuration.setAllowedHeaders(Arrays.asList("*"));
//			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//			source.registerCorsConfiguration("/**", configuration);
//			return source;
//		}
		
		@Bean
		    public WebMvcConfigurer corsConfigurer(){
					return new WebMvcConfigurer() {
						@Override
						public void addCorsMappings(CorsRegistry registry){
							registry.addMapping("/**")
									.allowedOrigins("*")
									.allowedOrigins("*") // Allow any origin
		                        	.allowedMethods("GET", "POST", "PUT", "DELETE") // Allow the specified HTTP methods
		                        	.allowedHeaders("*"); // Allow any header
		                         // Allow cookies
							registry.addMapping("/api/v1/**")
									.allowedOrigins("*")
									.allowedOrigins("*") // Allow any origin
			                    	.allowedMethods("GET", "POST", "PUT", "DELETE") // Allow the specified HTTP methods
			                    	.allowedHeaders("*"); // Allow any header
						}
					};
				}
}
