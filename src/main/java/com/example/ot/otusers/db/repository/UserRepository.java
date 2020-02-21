package com.example.ot.otusers.db.repository;

import com.example.ot.otusers.db.entity.User;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, QuerydslPredicateExecutor<User> {

    List<User> findByEmail(String email);

}
