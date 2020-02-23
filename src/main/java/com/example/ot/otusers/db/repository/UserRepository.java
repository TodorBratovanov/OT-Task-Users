package com.example.ot.otusers.db.repository;

import com.example.ot.otusers.db.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.QueryHint;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hibernate.annotations.QueryHints.READ_ONLY;
import static org.hibernate.jpa.QueryHints.*;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, QuerydslPredicateExecutor<User> {

    Optional<User> findByEmail(String email);

    @QueryHints(value = {
            @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE),
            @QueryHint(name = HINT_CACHEABLE, value = "false"),
            @QueryHint(name = READ_ONLY, value = "true"),
            @QueryHint(name = SPEC_HINT_TIMEOUT, value = "1000000")
    })
    @Query(value = "SELECT u FROM User u")
    Stream<User> getAll();

}
