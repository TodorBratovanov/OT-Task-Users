package com.example.ot.otusers.db.repository;

import com.example.ot.otusers.db.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByFirstNameStartsWithOrLastNameStartsWith(String firstNamePrefix, String lastNamePrefix);

    List<User> findByEmail(String email);

}
