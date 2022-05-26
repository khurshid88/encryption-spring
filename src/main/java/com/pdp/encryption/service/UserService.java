package com.pdp.encryption.service;

import com.pdp.encryption.model.User;
import com.pdp.encryption.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import static com.pdp.encryption.utils.Symmetric.decrypt;
import static com.pdp.encryption.utils.Symmetric.encrypt;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            user.setFullName(encrypt(user.getFullName()));
            user.setPhoneNumber(encrypt(user.getPhoneNumber()));
        }
        return user;
    }

    public User createUser(User user) {
        user.setFullName(decrypt(user.getFullName()));
        user.setPhoneNumber(decrypt(user.getPhoneNumber()));
        return userRepository.save(user);
    }
}

