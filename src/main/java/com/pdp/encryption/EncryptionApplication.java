package com.pdp.encryption;

import com.pdp.encryption.utils.Asymmetric;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import static com.pdp.encryption.utils.Symmetric.decrypt;
import static com.pdp.encryption.utils.Symmetric.encrypt;

@SpringBootApplication
public class EncryptionApplication {

    public static void main(String[] args) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        SpringApplication.run(EncryptionApplication.class, args);
        //testSymmetric();
        //testAsymmetric();
    }

    private static void testSymmetric() {
        // secret text
        String originalString = "PDP Academy";
        // Encryption
        String encryptedString = encrypt(originalString);
        // Decryption
        String decryptedString = decrypt(encryptedString);
        // Printing originalString,encryptedString,decryptedString
        System.out.println("Original String: " + originalString);
        System.out.println("Encrypted value: " + encryptedString);
        System.out.println("Decrypted value: " + decryptedString);
    }

    private static void testAsymmetric() throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        String secretText = "PDP University";
        Asymmetric keyPairGenerator = new Asymmetric();
        // Generate private and public key
        String privateKey = Base64.getEncoder().encodeToString(keyPairGenerator.privateKey.getEncoded());
        String publicKey = Base64.getEncoder().encodeToString(keyPairGenerator.publicKey.getEncoded());
        System.out.println("Public Key: " + publicKey);
        System.out.println("Private Key: " + privateKey);

        // Encrypt secret text using public key
        String encryptedValue = keyPairGenerator.encryptMessage(secretText, publicKey);
        System.out.println("Encrypted Value: " + encryptedValue);
        // Decrypt secret text using private key
        String decryptedText = keyPairGenerator.decryptMessage(encryptedValue, privateKey);
        System.out.println("Decrypted output: " + decryptedText);
    }

}
