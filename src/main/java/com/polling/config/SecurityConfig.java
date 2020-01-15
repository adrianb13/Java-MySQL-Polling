package com.polling.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.polling.security.CustomUserDetailsService;
import com.polling.security.JwtAuthenticationEntryPoint;
import com.polling.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// Used to find current user by username or email then loads user id if found.
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	// Checks to see if user is authorized for sections of application
	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	// Validates JWT and loads user detail and role.
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	// Primary Spring Authentication. Uses user details and JWT for authentication for login.
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
			.userDetailsService(customUserDetailsService)
			.passwordEncoder(passwordEncoder());
	}
	
	// Used in the builder above to authenticate user.
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	// Encodes password with JWT
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Http restrictions based on user authentication
	@Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .cors() //Crosss-Origin Resource Sharing
         .and()
      .csrf() //Cross-Site Request Forgery
         .disable()
      .exceptionHandling()
         .authenticationEntryPoint(unauthorizedHandler)
         .and()
      .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
      .authorizeRequests()
         .antMatchers("/",
            "/favicon.ico",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.svg",
            "/**/*.jpg",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js")
            .permitAll()
         .antMatchers("/api/auth/**")
            .permitAll()
         .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
            .permitAll()
         .antMatchers(HttpMethod.GET, "/api/polls/**", "/api/users/**")
            .permitAll()
         .anyRequest()
            .authenticated();

      // Add our custom JWT security filter
      http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
