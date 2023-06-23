package com.screenspot.authenticationservice.repository;

import com.screenspot.authenticationservice.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {

}
