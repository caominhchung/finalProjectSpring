package com.training.service;

import com.training.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author ChungCM
 */
public interface UserService extends UserDetailsService {
    void addOrUpdateUser(User user);
    void changePassword(User user);
    User findUserById(Integer id);
    User findUserByAccount(String account);

    User findUserByAccountAndPassword(String userName, String password);

    User findUserByTelPhone(String telPhone);
    User findUserByFacebook(String facebook);
    User findUserByEmail(String email);
    void createPasswordResetTokenForUser(User user, String token);

}
