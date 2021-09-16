package com.training.repository;

import com.training.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ChungCM
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    User findUserByAccount(String account);
    User findUserById(Integer id);
    User findUserByAccountAndPassword(String account, String password);

    User findUserByTelNumber(String telPhone);
    User findUserByFacebook(String facebook);
    User findUserByEmail(String email);

    Optional<User> findUserByAccountAndActive(String account, boolean active);

}
