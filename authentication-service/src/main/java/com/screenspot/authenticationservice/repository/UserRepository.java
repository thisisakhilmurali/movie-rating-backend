package com.screenspot.authenticationservice.repository;

import com.screenspot.authenticationservice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}