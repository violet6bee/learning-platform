package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shvalieva.learning_platform.dto.CategoryRequestDTO;
import shvalieva.learning_platform.dto.CategoryResponseDTO;
import shvalieva.learning_platform.entity.CategoryEntity;
import shvalieva.learning_platform.mapper.CategoryMapper;
import shvalieva.learning_platform.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Категория с id: " + id + " не найдена"));
        return categoryMapper.toDto(entity);
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        CategoryEntity entity = categoryMapper.fromRequestDto(categoryRequestDTO);
        entity.setId(null);
        CategoryEntity savedEntity = categoryRepository.save(entity);
        return categoryMapper.toDto(savedEntity);
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Категория с id: " + id + " не найдена"));
        entity.setName(categoryRequestDTO.getName());
        categoryRepository.save(entity);
        return categoryMapper.toDto(entity);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
