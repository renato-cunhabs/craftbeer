package com.beerhouse.repository;

import com.beerhouse.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CraftBeerRepository extends JpaRepository<Beer, Long> {
    Beer findById(long id);
}
