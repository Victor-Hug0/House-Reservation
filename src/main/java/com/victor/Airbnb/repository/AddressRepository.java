package com.victor.Airbnb.repository;

import com.victor.Airbnb.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
