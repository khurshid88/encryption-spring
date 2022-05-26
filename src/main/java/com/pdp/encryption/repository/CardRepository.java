package com.pdp.encryption.repository;

import com.pdp.encryption.model.Card;
import com.pdp.encryption.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {
}

