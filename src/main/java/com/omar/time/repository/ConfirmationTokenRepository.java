package com.omar.time.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.omar.time.model.ConfirmationToken;
import com.omar.time.model.User;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
<<<<<<< HEAD
    Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);
=======
    ConfirmationToken findByConfirmationToken(String confirmationToken);
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
    
    Optional<ConfirmationToken> findByUser(User user);
    
    void deleteByTokenid(long id);
}
