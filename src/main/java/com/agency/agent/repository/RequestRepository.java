package com.agency.agent.repository;

import com.agency.agent.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByAgentId(Long id);
    List<Request> findByUserId(Long id);
}
