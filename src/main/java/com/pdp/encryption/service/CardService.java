package com.pdp.encryption.service;

import com.pdp.encryption.model.Card;
import com.pdp.encryption.model.User;
import com.pdp.encryption.repository.CardRepository;
import com.pdp.encryption.repository.UserRepository;
import com.pdp.encryption.utils.Asymmetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static com.pdp.encryption.utils.Symmetric.decrypt;
import static com.pdp.encryption.utils.Symmetric.encrypt;

@Service
public class CardService {
    String publicServerKey = "MIIBHDANBgkqhkiG9w0BAQEFAAOCAQkAMIIBBAKB/DcfxiXBXyC1O/mfPs2dGMCME3FRo5aKYsQyZCncmHU1slfkzwpTVNNCz5jpzp7/OdGMERRy3fi2C+23o3RCnDhIA5TIYxGH2lGj1OpSupYML+8IOxgz7FtiKknQlaMgRwXwWJpNVOVFOZB60YuYSl/YaWe90ULxiL8bHRAwnhenCz8u5g4zdVSGniZq7/ZWwTPvD6ZZGq0YGb9/BVuJc05FBRAZOxqOmBGWfr+qXlgIFwBKwmqK2um0yt47mb6jPGhK49so1huaTI9IrTLA2x6RqpimKALjvvIVhTp7HCwLXNR0FqQ+Yda+Wbwg8HMruHKs4HTS2jKLREnmVQIDAQAB";
    String privateMobileKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALtKtHum+ynWswNWfhH1eNmnirohVKK31cAq8AyBMiaXu8CnYfzVED1XiMPKsWUBKu/GrS4aLlb9tmP4B1ga7Vx5MN4srw68f3QbIpdo+IeO48B5EIbJMGSf7ueiBXGqmasZUZQl9yyWvglrBT/1Wi7suN2to1K0KeV1EnMq9xj7AgMBAAECgYA1bwtN6eZnWd8puR/vrLplUnT0RHUalVsw368ryiJOO2TwAnEkyn6/GX2l6mqw9+PRk7ut1v9Yhzrli4pA2zHTfrZjNF8ZCu98HG7A9FduMr0r+l1GwtP7kdaOiA4+2rvKO0+dtL3j7tvpfpSy3u+YjAhuxR6GgkW3VItVOXYcUQJBAN14CQ1CtaXPOHG6cJ+ru2Lv4vBflRGQctYZ3X31oRoYon3hsMG8DWSgSumRwQOBRqF4H3lg2VKI0CiHx+v2r2sCQQDYfnyRfcIVLpRC/PfCz8yEvO5xUqehVpe/YjrasrEptHsFLg73m1SxSxtO0tTmMHWCEVoah2qzJzgWAWnBO3CxAkB0rG4oXEKSDa8wywXWOITJxJMMC27fB/hXrtyvJR4wP1YrdyDFj6qGnqOYlT7X990b58q9CTa0cnsnPxEl0JntAkEAx4Vy6mdbKkheTYp0EK+QDjlNC1WgtflnKvOtyAZtbkEuK2zyUFJwcGEGztNRcL/yXCf/wXlR5cBcWTN+Mp7EUQJBANxtSV2QFThKIP0TRwfsb8pRGWxok6zuf2DRSfjb1e63SVB3cftHNkDivJAgjFVEFzIQDWE8XpJPWVSwefc7/dA=";

    @Autowired
    CardRepository cardRepository;

    public List<Card> getCards() {
        return cardRepository.findAll();
    }

    public Card getCardById(int id) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Optional<Card> optionalCard = cardRepository.findById(id);
        Card card = null;
        if (optionalCard.isPresent()) {
            card = optionalCard.get();
            card.setCardNumber(encryptCardNumber(card));
        }
        return card;
    }

    public Card createCard(Card card) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        card.setCardNumber(decryptCardNumber(card));

        return cardRepository.save(card);
    }

    private String encryptCardNumber(Card card) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Asymmetric keyPairGenerator = new Asymmetric();
        // Encrypt secret text using public key
        return keyPairGenerator.encryptMessage(card.getCardNumber(), publicServerKey);
    }

    private String decryptCardNumber(Card card) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Asymmetric keyPairGenerator = new Asymmetric();
        // Decrypt secret text using private key
        return keyPairGenerator.decryptMessage(card.getCardNumber(), privateMobileKey);
    }
}

