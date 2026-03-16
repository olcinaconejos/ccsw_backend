package org.practicas.backCapgemini.game.services;

import jakarta.transaction.Transactional;
import org.practicas.backCapgemini.author.services.AuthorService;
import org.practicas.backCapgemini.category.services.CategoryService;
import org.practicas.backCapgemini.common.criteria.SearchCriteria;
import org.practicas.backCapgemini.game.GameSpecification;
import org.practicas.backCapgemini.game.model.Game;
import org.practicas.backCapgemini.game.model.GameDto;
import org.practicas.backCapgemini.game.repos.GameRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    AuthorService authorService;

    @Autowired
    CategoryService categoryService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Game> find(String title, Long idCategory) {

        GameSpecification titleSpec = new GameSpecification(new SearchCriteria("title", ":", title));
        GameSpecification categorySpec = new GameSpecification(new SearchCriteria("category.id", ":", idCategory));

        // Desde la versión 3.5.0 de Spring Boot, la nueva manera es
        Specification<Game> spec = titleSpec.and(categorySpec);

        return this.gameRepository.findAll(spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, GameDto dto) {

        Game game;

        if (id == null) {
            game = new Game();
        } else {
            game = this.gameRepository.findById(id).orElse(null);
        }

        BeanUtils.copyProperties(dto, game, "id", "author", "category");

        game.setAuthor(authorService.get(dto.getAuthor().getId()));
        game.setCategory(categoryService.get(dto.getCategory().getId()));

        this.gameRepository.save(game);
    }

}
