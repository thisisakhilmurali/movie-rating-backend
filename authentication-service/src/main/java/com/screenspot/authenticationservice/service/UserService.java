package com.screenspot.authenticationservice.service;


import com.screenspot.authenticationservice.dto.UserRegistrationRequest;
import com.screenspot.authenticationservice.model.Role;
import com.screenspot.authenticationservice.model.User;
import com.screenspot.authenticationservice.repository.RoleRepository;
import com.screenspot.authenticationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin Role: Top Priority");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("User Role: Restricted Priority");
        roleRepository.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("admin");
        adminUser.setUserPassword(getEncodedPassword("password"));
        adminUser.setUserFirstName("admin_first");
        adminUser.setUserLastName("admin_last");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userRepository.save(adminUser);
    }

    public User registerNewUser(UserRegistrationRequest userRegistrationRequest) {
        User user = new User();
        user.setUserName(userRegistrationRequest.getUserName());
        user.setUserFirstName(userRegistrationRequest.getUserFirstName());
        user.setUserLastName(userRegistrationRequest.getUserLastName());
        user.setUserPassword(getEncodedPassword(userRegistrationRequest.getUserPassword()));

        Role userRole = roleRepository.findById("User").orElseThrow(() -> new RuntimeException("User role not found"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);

        return userRepository.save(user);
    }

    private String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
