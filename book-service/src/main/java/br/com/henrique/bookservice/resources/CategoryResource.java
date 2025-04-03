package br.com.henrique.bookservice.resources;

import br.com.henrique.bookservice.models.Category;
import br.com.henrique.bookservice.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Categorias")
@RestController
@RequestMapping("/api/categories/v1")
public class CategoryResource extends GenericResource<Category, Long>  {

    public CategoryResource(CategoryService categoryService) {
        super(categoryService);
    }

    @Override
    @Operation(summary = "Busca uma categoria pelo ID e caso exista atualiza a mesma")
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category entity) {
        return super.update(id, entity);
    }

}
