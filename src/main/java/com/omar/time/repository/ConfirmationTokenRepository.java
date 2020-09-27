package com.omar.time.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.omar.time.model.ConfirmationToken;
import com.omar.time.model.User;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {

    Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);
    
    Optional<ConfirmationToken> findByUser(User user);
    
    void deleteByTokenid(long id);
}
