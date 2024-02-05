package com.victor.Airbnb.repository;

import com.victor.Airbnb.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
}
