package com.mappingexercise.softunigame.repositories;

import com.mappingexercise.softunigame.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Game  g set g.price = ?1, g.size = ?2 WHERE g.id = ?3")
    void updatePriceInGame(BigDecimal price, Double size,Long id);
}
