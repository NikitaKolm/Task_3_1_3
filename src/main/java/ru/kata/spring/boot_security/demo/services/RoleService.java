package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    void saveRole(Role role);

    void removeRoleById(long id);

    Role getRoleById(long id);

    List<Role> getAllRoles();
}
