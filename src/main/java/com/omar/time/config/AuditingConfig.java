package com.omar.time.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.omar.time.security.UserPrincipal;

//We will need to create a bean of type AuditorAware and 
//will also need to enable JPA auditing by specifying @EnableJpaAuditing on one of our configuration classes.
//@EnableJpaAuditing accepts one argument, auditorAwareRef, where we need to pass the name of the AuditorAware bean.

@Configuration
@EnableJpaAuditing 
public class AuditingConfig {

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return new SpringSecurityAuditAwareImpl();
    }
}


//to make JPA aware of currently logged in users (createdBy, updatedBy) 
//we will need to provide an implementation of AuditorAware and override the getCurrentAuditor() method.
//And inside getCurrentAuditor(), we will need to fetch a currently logged-in user.

class SpringSecurityAuditAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        return Optional.ofNullable(userPrincipal.getId());
    }
}