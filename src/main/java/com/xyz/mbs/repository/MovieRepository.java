package com.xyz.mbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.xyz.mbs.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
