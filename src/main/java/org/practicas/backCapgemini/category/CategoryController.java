package org.practicas.backCapgemini.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.practicas.backCapgemini.category.model.Category;
import org.practicas.backCapgemini.category.model.CategoryDto;
import org.practicas.backCapgemini.category.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "API of category")
@RequestMapping(value = "/category")
@RestController
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Find", description = "Method that return a list of Categories")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<CategoryDto> findAll() {
        List<Category> categories = this.categoryService.findAll();

        return categories.stream().map(x -> mapper.map(x, CategoryDto.class)).toList();
    }

    @Operation(summary = "Save or Update", description = "Method to save or update a Category")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody CategoryDto dto) {
        this.categoryService.save(id, dto);
    }

    @Operation(summary = "Delete", description = "Method that deletes a Category")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {
        this.categoryService.delete(id);
    }
}
