package com.routes.explorations.repository;

import com.routes.explorations.entity.Destinations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationsRepository extends JpaRepository<Destinations, Long> {
}

