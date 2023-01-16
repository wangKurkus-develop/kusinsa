package com.kurkus.kusinsa.repository;

import java.util.Optional;

import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.exception.user.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    default User getById(Long id){
        return findById(id).orElseThrow(()-> new UserNotFoundException());
    }

    default User getByEmail(String email){
        return findByEmail(email).orElseThrow(()-> new UserNotFoundException());
    }

}
