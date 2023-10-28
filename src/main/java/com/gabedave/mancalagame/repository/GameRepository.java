package com.gabedave.mancalagame.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gabedave.mancalagame.models.Game;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    List<Game> findByWinnerIsNull();
}
