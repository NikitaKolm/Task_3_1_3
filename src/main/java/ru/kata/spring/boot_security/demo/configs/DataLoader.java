package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DataLoader(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userService.findByUsername("admin").isEmpty()) {
            roleService.saveRole(new Role("ROLE_USER"));
            HashSet<Role> roleHashSet = new HashSet<>();
            roleHashSet.add(new Role("ROLE_ADMIN"));
            userService.saveUser(new User("admin", "admin", "admin@admin.com", "admin",
                    "admin", roleHashSet));
        }
    }
}
