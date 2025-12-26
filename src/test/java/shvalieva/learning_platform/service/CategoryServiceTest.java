package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shvalieva.learning_platform.dto.CategoryRequestDTO;
import shvalieva.learning_platform.dto.CategoryResponseDTO;
import shvalieva.learning_platform.entity.CategoryEntity;
import shvalieva.learning_platform.mapper.CategoryMapper;
import shvalieva.learning_platform.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getAllCategories_success() {
        CategoryEntity entity = new CategoryEntity();
        entity.setName("Programming");

        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setName("Programming");

        when(categoryRepository.findAll()).thenReturn(List.of(entity));
        when(categoryMapper.toDto(entity)).thenReturn(dto);

        List<CategoryResponseDTO> result = categoryService.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Programming", result.get(0).getName());
        verify(categoryRepository).findAll();
    }

    @Test
    void getCategoryById_success() {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(1L);
        entity.setName("Math");

        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setName("Math");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(categoryMapper.toDto(entity)).thenReturn(dto);

        CategoryResponseDTO result = categoryService.getCategoryById(1L);

        assertEquals("Math", result.getName());
        verify(categoryRepository).findById(1L);
    }

    @Test
    void getCategoryById_notFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> categoryService.getCategoryById(1L));
    }

    @Test
    void createCategory_success() {
        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName("Science");

        CategoryEntity entity = new CategoryEntity();
        entity.setName("Science");

        CategoryEntity saved = new CategoryEntity();
        saved.setId(10L);
        saved.setName("Science");

        CategoryResponseDTO response = new CategoryResponseDTO();
        response.setName("Science");

        when(categoryMapper.fromRequestDto(request)).thenReturn(entity);
        when(categoryRepository.save(entity)).thenReturn(saved);
        when(categoryMapper.toDto(saved)).thenReturn(response);

        CategoryResponseDTO result = categoryService.createCategory(request);

        assertEquals("Science", result.getName());
        verify(categoryRepository).save(entity);
    }

    @Test
    void updateCategory_success() {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(1L);
        entity.setName("Old");

        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName("New");

        CategoryResponseDTO response = new CategoryResponseDTO();
        response.setName("New");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(categoryMapper.toDto(entity)).thenReturn(response);

        CategoryResponseDTO result = categoryService.updateCategory(1L, request);

        assertEquals("New", result.getName());
        verify(categoryRepository).save(entity);
    }

    @Test
    void updateCategory_notFound() {
        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName("New");

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> categoryService.updateCategory(1L, request));
    }

    @Test
    void deleteCategory_success() {
        doNothing().when(categoryRepository).deleteById(1L);

        categoryService.deleteCategory(1L);

        verify(categoryRepository).deleteById(1L);
    }
}
