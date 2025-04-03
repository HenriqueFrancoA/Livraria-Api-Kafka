package br.com.henrique.bookservice.services;

import br.com.henrique.bookservice.models.Category;
import br.com.henrique.bookservice.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CategoryService extends GenericService<Category, Long> {

    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository);
    }

    private final Logger logger = Logger.getLogger(CategoryService.class.getName());

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category update(Long id, Category category, String simpleName) {
        logger.info("Atualizando Categoria, ID: " + id);
        Category cat = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não encontrado."));

        cat.setName(category.getName());

        return categoryRepository.save(cat);
    }
}
