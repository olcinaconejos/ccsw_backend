package org.practicas.backCapgemini.game.repos;

import org.practicas.backCapgemini.game.model.Game;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author ccsw
 *
 */
public interface GameRepository extends CrudRepository<Game, Long>, JpaSpecificationExecutor<Game> {
    @Override
    @EntityGraph(attributePaths = { "title", "category" })
    List<Game> findAll(Specification<Game> spec);
}
