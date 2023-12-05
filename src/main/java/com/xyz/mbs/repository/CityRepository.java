package com.xyz.mbs.repository;

import com.xyz.mbs.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
