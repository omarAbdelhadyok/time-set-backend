package com.omar.time.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.omar.time.security.CustomUserDetailsService;
import com.omar.time.security.JwtAuthenticationEntryPoint;
import com.omar.time.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity //This is the primary spring security annotation that is used to enable web security in a project.

//This is used to enable method level security based on annotations.
//You can use following three types of annotations for securing your methods

//securedEnabled: It enables the @Secured annotation using which you can protect
//your controller/service methods like so -
//@Secured("ROLE_ADMIN")
//public User getAllUsers() {}
//@Secured({"ROLE_USER", "ROLE_ADMIN"})
//public User getUser(Long id) {}

//jsr250Enabled: It enables the @RolesAllowed annotation that can be used like this
//@RolesAllowed("ROLE_ADMIN")
//public Poll createPoll() {}  

//prePostEnabled: It enables more complex expression based access control syntax
//with @PreAuthorize and @PostAuthorize annotations -
//@PreAuthorize("isAnonymous()")
//public boolean isUsernameAvailable() {}
//@PreAuthorize("hasRole('USER')")
//public Poll createPoll() {}


@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)

//WebSecurityConfigurerAdapter
//This class implements Spring Security’s WebSecurityConfigurer interface.
//It provides default security configurations and allows other classes to extend it and
//customize the security configurations by overriding its methods.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = {
	        "/swagger-resources/**",
	        "/swagger-ui.html",
	        "/v2/api-docs",
	        "/webjars/**"
	};
	
	//implementation for UserDetailsService that 
	//has a method: UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	//that should allow spring security to load user details
	//Note that, the loadUserByUsername() method returns a UserDetails object
	//that Spring Security uses for performing various authentication and role based validations.
	//In our implementation, We’ll also define a custom UserPrincipal class that will implement UserDetails interface,
	//and return the UserPrincipal object from loadUserByUsername() method.
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    //This class is used to return a 401 unauthorized error to clients that try to access
    //a protected resource without proper authentication. It implements Spring Security’s AuthenticationEntryPoint interface.
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    //We’ll use JWTAuthenticationFilter to implement a filter that -
    //reads JWT authentication token from the Authorization header of all the requests
    //validates the token
    //loads the user details associated with that token.
    //Sets the user details in Spring Security’s SecurityContext.
    //Spring Security uses the user details to perform authorization checks.
    //We can also access the user details stored in the SecurityContext in our controllers to perform our business logic.
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    //AuthenticationManagerBuilder is used to create an AuthenticationManager instance
    //which is the main Spring Security interface for authenticating a user.
    
    //You can use AuthenticationManagerBuilder to build in-memory authentication,
    //LDAP authentication, JDBC authentication, or add your custom authentication provider.
    //In our example, we’ve provided our customUserDetailsService and a passwordEncoder to build the AuthenticationManager.
    //We’ll use the configured AuthenticationManager to authenticate a user in the login API.
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //The HttpSecurity configurations are used to configure security
    //functionalities like csrf, sessionManagement, and add rules to protect resources based on various conditions
    //In our example, we’re permitting access to static resources and
    //few other public APIs to everyone and restricting access to other APIs to authenticated users only.
	//We’ve also added the JWTAuthenticationEntryPoint and the custom JWTAuthenticationFilter in the HttpSecurity configuration.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
                .and()
            .csrf()
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
                .antMatchers("/api/auth/**", "/api/users/role", "/uploads/**")
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
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }
}