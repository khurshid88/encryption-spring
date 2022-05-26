package com.pdp.encryption.controller;

import com.pdp.encryption.model.Card;
import com.pdp.encryption.model.User;
import com.pdp.encryption.service.CardService;
import com.pdp.encryption.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
public class CardController {

    @Autowired
    CardService cardService;

    @GetMapping("api/cards")
    public HttpEntity<?> getCards() {
        List<Card> cardList = cardService.getCards();
        return ResponseEntity.ok(cardList);
    }

    @GetMapping("api/cards/{id}")
    public HttpEntity<?> getCardById(@PathVariable(value = "id") int cardId) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Card card = cardService.getCardById(cardId);
        return ResponseEntity.ok(card);
    }

    @PostMapping("/api/cards")
    public HttpEntity<?> createCard(@RequestBody Card card) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Card savedCard = cardService.createCard(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
    }
}
