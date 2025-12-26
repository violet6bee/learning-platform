package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shvalieva.learning_platform.dto.ModuleRequestDTO;
import shvalieva.learning_platform.dto.ModuleResponseDTO;
import shvalieva.learning_platform.entity.CourseEntity;
import shvalieva.learning_platform.entity.ModuleEntity;
import shvalieva.learning_platform.mapper.ModuleMapper;
import shvalieva.learning_platform.repository.CourseRepository;
import shvalieva.learning_platform.repository.ModuleRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModuleServiceTest {

    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ModuleMapper moduleMapper;

    @InjectMocks
    private ModuleService moduleService;

    @Test
    void getModuleById_success() {
        ModuleEntity entity = new ModuleEntity();
        ModuleResponseDTO dto = new ModuleResponseDTO();

        when(moduleRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(moduleMapper.toDto(entity)).thenReturn(dto);

        ModuleResponseDTO result = moduleService.getModuleById(1L);

        assertNotNull(result);
        verify(moduleRepository).findById(1L);
        verify(moduleMapper).toDto(entity);
    }

    @Test
    void getModuleById_notFound() {
        when(moduleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> moduleService.getModuleById(1L));
    }

    @Test
    void getAllModulesByCourseId_success() {
        ModuleEntity m1 = new ModuleEntity();
        ModuleEntity m2 = new ModuleEntity();

        ModuleResponseDTO d1 = new ModuleResponseDTO();
        ModuleResponseDTO d2 = new ModuleResponseDTO();

        when(moduleRepository.findByCourseId(10L)).thenReturn(asList(m1, m2));
        when(moduleMapper.toDto(m1)).thenReturn(d1);
        when(moduleMapper.toDto(m2)).thenReturn(d2);

        List<ModuleResponseDTO> result =
                moduleService.getAllModulesByCourseId(10L);

        assertEquals(2, result.size());
        verify(moduleRepository).findByCourseId(10L);
    }

    @Test
    void createModule_success() {
        ModuleRequestDTO request = new ModuleRequestDTO();
        request.setCourseId(1L);
        request.setTitle("Module title");

        CourseEntity course = new CourseEntity();
        course.setId(1L);

        ModuleEntity entity = new ModuleEntity();
        ModuleEntity saved = new ModuleEntity();
        ModuleResponseDTO responseDTO = new ModuleResponseDTO();

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(moduleMapper.fromRequestDto(request)).thenReturn(entity);
        when(moduleRepository.save(entity)).thenReturn(saved);
        when(moduleMapper.toDto(saved)).thenReturn(responseDTO);

        ModuleResponseDTO result = moduleService.createModule(request);

        assertNotNull(result);
        verify(courseRepository).findById(1L);
        verify(moduleRepository).save(entity);
        verify(moduleMapper).toDto(saved);
        assertEquals(course, entity.getCourse());
    }

    @Test
    void createModule_courseNotFound() {
        ModuleRequestDTO request = new ModuleRequestDTO();
        request.setCourseId(1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> moduleService.createModule(request));
    }

    @Test
    void deleteModule_success() {
        doNothing().when(moduleRepository).deleteById(1L);

        moduleService.deleteModule(1L);

        verify(moduleRepository).deleteById(1L);
    }
}
