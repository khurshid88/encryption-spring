package com.pdp.encryption.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class Asymmetric {
    public  PrivateKey privateKey;
    public  PublicKey publicKey;

    public Asymmetric() throws NoSuchAlgorithmException {
        var keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2014);
        var pair = keyGen.generateKeyPair();
        privateKey = pair.getPrivate();
        publicKey = pair.getPublic();
    }

    public PublicKey loadPublicKey(String stored) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] data = Base64.getDecoder().decode(stored.getBytes());
        var spec = new X509EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return fact.generatePublic(spec);
    }

    public PrivateKey loadPrivateKey(String stored) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] clear = Base64.getDecoder().decode(stored.getBytes());
        var keySpec = new PKCS8EncodedKeySpec(clear);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PrivateKey priv = fact.generatePrivate(keySpec);
        Arrays.fill(clear, (byte) 0);
        return priv;
    }

    public String encryptMessage(String plainText, String publickey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, loadPublicKey(publickey));
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }

    public String decryptMessage(String encryptedText, String privatekey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, loadPrivateKey(privatekey));
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
    }

}
