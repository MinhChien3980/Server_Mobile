package org.example.server_mobile.repository;

import org.example.server_mobile.entity.AgeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgeGroupRepo extends JpaRepository<AgeGroup, Long> {
}
