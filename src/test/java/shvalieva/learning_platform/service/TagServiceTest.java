package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shvalieva.learning_platform.dto.TagRequestDTO;
import shvalieva.learning_platform.dto.TagResponseDTO;
import shvalieva.learning_platform.entity.TagEntity;
import shvalieva.learning_platform.mapper.TagMapper;
import shvalieva.learning_platform.repository.TagRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagService tagService;

    @Test
    void getAllTags_success() {
        TagEntity entity = new TagEntity();
        entity.setName("Java");

        TagResponseDTO dto = new TagResponseDTO();
        dto.setName("Java");

        when(tagRepository.findAll()).thenReturn(List.of(entity));
        when(tagMapper.toDto(entity)).thenReturn(dto);

        List<TagResponseDTO> result = tagService.getAllTags();

        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getName());
        verify(tagRepository).findAll();
    }

    @Test
    void getTagById_success() {
        TagEntity entity = new TagEntity();
        entity.setId(1L);
        entity.setName("Spring");

        TagResponseDTO dto = new TagResponseDTO();
        dto.setName("Spring");

        when(tagRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(tagMapper.toDto(entity)).thenReturn(dto);

        TagResponseDTO result = tagService.getTagById(1L);

        assertEquals("Spring", result.getName());
        verify(tagRepository).findById(1L);
    }

    @Test
    void getTagById_notFound() {
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> tagService.getTagById(1L));
    }

    @Test
    void createTag_success() {
        TagRequestDTO request = new TagRequestDTO();
        request.setName("Hibernate");

        TagEntity entity = new TagEntity();
        entity.setName("Hibernate");

        TagEntity saved = new TagEntity();
        saved.setId(10L);
        saved.setName("Hibernate");

        TagResponseDTO response = new TagResponseDTO();
        response.setName("Hibernate");

        when(tagMapper.fromRequestDto(request)).thenReturn(entity);
        when(tagRepository.save(entity)).thenReturn(saved);
        when(tagMapper.toDto(saved)).thenReturn(response);

        TagResponseDTO result = tagService.createTag(request);

        assertNotNull(result);
        assertEquals("Hibernate", result.getName());
        verify(tagRepository).save(entity);
    }

    @Test
    void updateTag_success() {
        TagEntity entity = new TagEntity();
        entity.setId(1L);
        entity.setName("Old");

        TagRequestDTO request = new TagRequestDTO();
        request.setName("New");

        TagResponseDTO response = new TagResponseDTO();
        response.setName("New");

        when(tagRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(tagMapper.toDto(entity)).thenReturn(response);

        TagResponseDTO result = tagService.updateTag(1L, request);

        assertEquals("New", result.getName());
        verify(tagRepository).save(entity);
    }

    @Test
    void updateTag_notFound() {
        TagRequestDTO request = new TagRequestDTO();
        request.setName("New");

        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> tagService.updateTag(1L, request));
    }

    @Test
    void deleteTag_success() {
        doNothing().when(tagRepository).deleteById(1L);

        tagService.deleteTag(1L);

        verify(tagRepository).deleteById(1L);
    }
}
