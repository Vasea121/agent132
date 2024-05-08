package com.agency.agent.repository;

import com.agency.agent.model.Roles;
import com.agency.agent.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    @Query("SELECT u FROM Users u JOIN u.roles r WHERE r.name = :roleName")
    List<Users> findByRoles(@Param("roleName") String roleName);
}
