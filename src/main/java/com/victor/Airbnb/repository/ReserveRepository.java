package com.victor.Airbnb.repository;

import com.victor.Airbnb.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    List<Reserve> findByUserEmail(String userEmail);

    Optional<Reserve> findByIdAndUserEmail(Long id, String userEmail);
}
