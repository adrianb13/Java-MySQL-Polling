package com.polling.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polling.entity.Role;
import com.polling.entity.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
		Optional<Role> findByName(RoleName roleName);
}
