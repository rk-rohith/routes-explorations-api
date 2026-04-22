package com.routes.explorations.repository;

import com.routes.explorations.entity.Routes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutesRepository extends JpaRepository<Routes, Long> {
}

