package com.pdp.encryption.repository;

import com.pdp.encryption.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

