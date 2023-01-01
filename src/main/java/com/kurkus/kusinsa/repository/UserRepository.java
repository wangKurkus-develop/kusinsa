package com.kurkus.kusinsa.repository;

import com.kurkus.kusinsa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
