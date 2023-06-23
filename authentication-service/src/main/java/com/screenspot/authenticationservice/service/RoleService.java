package com.screenspot.authenticationservice.service;

import com.screenspot.authenticationservice.model.Role;
import com.screenspot.authenticationservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }
}
