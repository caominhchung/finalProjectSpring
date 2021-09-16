package com.training.service.impl;

import com.training.dto.UserPrinciple;
import com.training.entities.PasswordResetToken;
import com.training.entities.User;
import com.training.repository.PasswordResetTokenRepository;
import com.training.repository.UserRepository;
import com.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ChungCM
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;


    @Override
    public void addOrUpdateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void changePassword(User user) {

        userRepository.save(user);

    }

    @Override
    public User findUserById(Integer id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserByAccount(String account) {
        return userRepository.findUserByAccount(account);
    }

    @Override
    public User findUserByAccountAndPassword(String userName, String password) {
        return userRepository.findUserByAccountAndPassword(userName, password);
    }

    @Override
    public User findUserByTelPhone(String telPhone) {
        return userRepository.findUserByTelNumber(telPhone);
    }

    @Override
    public User findUserByFacebook(String facebook) {
        return userRepository.findUserByFacebook(facebook);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByAccountAndActive(s, true)
                .orElseThrow(() -> new UsernameNotFoundException("account not correct or account is not active: " + s));
        return UserPrinciple.build(user);
    }

    @Override
    @Transactional
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }


}
