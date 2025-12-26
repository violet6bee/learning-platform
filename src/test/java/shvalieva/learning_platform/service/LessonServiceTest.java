package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shvalieva.learning_platform.dto.LessonRequestDTO;
import shvalieva.learning_platform.dto.LessonResponseDTO;
import shvalieva.learning_platform.entity.LessonEntity;
import shvalieva.learning_platform.entity.ModuleEntity;
import shvalieva.learning_platform.mapper.LessonMapper;
import shvalieva.learning_platform.repository.LessonRepository;
import shvalieva.learning_platform.repository.ModuleRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private LessonMapper lessonMapper;

    @InjectMocks
    private LessonService lessonService;

    @Test
    void getLessonById_success() {
        LessonEntity entity = new LessonEntity();
        LessonResponseDTO dto = new LessonResponseDTO();

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(lessonMapper.toDto(entity)).thenReturn(dto);

        LessonResponseDTO result = lessonService.getLessonById(1L);

        assertNotNull(result);
        verify(lessonRepository).findById(1L);
        verify(lessonMapper).toDto(entity);
    }

    @Test
    void getLessonById_notFound() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> lessonService.getLessonById(1L));
    }

    @Test
    void getAllLessons_success() {
        LessonEntity e1 = new LessonEntity();
        LessonEntity e2 = new LessonEntity();

        when(lessonRepository.findAll()).thenReturn(List.of(e1, e2));
        when(lessonMapper.toDto(any())).thenReturn(new LessonResponseDTO());

        List<LessonResponseDTO> result = lessonService.getAllLessons();

        assertEquals(2, result.size());
        verify(lessonRepository).findAll();
        verify(lessonMapper, times(2)).toDto(any());
    }

    @Test
    void createLesson_success() {
        LessonRequestDTO request = new LessonRequestDTO();
        request.setModuleId(1L);
        request.setTitle("Lesson title");
        request.setVideoUrl("video-url");

        ModuleEntity module = new ModuleEntity();
        LessonEntity entity = new LessonEntity();
        LessonEntity saved = new LessonEntity();
        LessonResponseDTO responseDTO = new LessonResponseDTO();

        when(moduleRepository.findById(1L)).thenReturn(Optional.of(module));
        when(lessonMapper.fromRequestDto(request)).thenReturn(entity);
        when(lessonRepository.save(entity)).thenReturn(saved);
        when(lessonMapper.toDto(saved)).thenReturn(responseDTO);

        LessonResponseDTO result = lessonService.createLesson(request);

        assertNotNull(result);
        verify(moduleRepository).findById(1L);
        verify(lessonRepository).save(entity);
        verify(lessonMapper).toDto(saved);
    }

    @Test
    void createLesson_moduleNotFound() {
        LessonRequestDTO request = new LessonRequestDTO();
        request.setModuleId(99L);

        when(moduleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> lessonService.createLesson(request));
    }

    @Test
    void updateLesson_success() {
        LessonRequestDTO request = new LessonRequestDTO();
        request.setTitle("New title");
        request.setVideoUrl("new-video");

        LessonEntity entity = new LessonEntity();
        LessonResponseDTO responseDTO = new LessonResponseDTO();

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(lessonMapper.toDto(entity)).thenReturn(responseDTO);

        LessonResponseDTO result = lessonService.updateLesson(1L, request);

        assertNotNull(result);
        assertEquals("New title", entity.getTitle());
        assertEquals("new-video", entity.getVideoUrl());
        verify(lessonRepository).findById(1L);
        verify(lessonMapper).toDto(entity);
    }

    @Test
    void updateLesson_notFound() {
        LessonRequestDTO request = new LessonRequestDTO();

        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> lessonService.updateLesson(1L, request));
    }
}
