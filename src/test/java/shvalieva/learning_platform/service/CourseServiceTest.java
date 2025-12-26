package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shvalieva.learning_platform.dto.CourseRequestDTO;
import shvalieva.learning_platform.dto.CourseResponseDTO;
import shvalieva.learning_platform.entity.CategoryEntity;
import shvalieva.learning_platform.entity.CourseEntity;
import shvalieva.learning_platform.entity.UserEntity;
import shvalieva.learning_platform.mapper.CourseMapper;
import shvalieva.learning_platform.repository.CategoryRepository;
import shvalieva.learning_platform.repository.CourseRepository;
import shvalieva.learning_platform.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void getAllCourses_success() {
        CourseEntity course1 = new CourseEntity();
        CourseEntity course2 = new CourseEntity();

        CourseResponseDTO dto1 = new CourseResponseDTO();
        CourseResponseDTO dto2 = new CourseResponseDTO();

        when(courseRepository.findAll()).thenReturn(List.of(course1, course2));
        when(courseMapper.toDto(course1)).thenReturn(dto1);
        when(courseMapper.toDto(course2)).thenReturn(dto2);

        List<CourseResponseDTO> result = courseService.getAllCourses();

        assertEquals(2, result.size());
        verify(courseRepository).findAll();
    }

    @Test
    void getCourseById_success() {
        CourseEntity course = new CourseEntity();
        course.setId(1L);

        CourseResponseDTO dto = new CourseResponseDTO();

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseMapper.toDto(course)).thenReturn(dto);

        CourseResponseDTO result = courseService.getCourseById(1L);

        assertNotNull(result);
        verify(courseRepository).findById(1L);
    }

    @Test
    void getCourseById_notFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> courseService.getCourseById(1L));
    }

    @Test
    void createCourse_success() {
        UserEntity teacher = new UserEntity();
        teacher.setId(1L);

        CategoryEntity category = new CategoryEntity();
        category.setId(2L);

        CourseRequestDTO request = new CourseRequestDTO();
        request.setTitle("Java");
        request.setDescription("Java course");
        request.setCategoryId(2L);

        CourseEntity entity = new CourseEntity();
        CourseEntity saved = new CourseEntity();
        saved.setId(10L);

        CourseResponseDTO response = new CourseResponseDTO();

        when(userRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));
        when(courseMapper.fromRequestDto(request)).thenReturn(entity);
        when(courseRepository.save(entity)).thenReturn(saved);
        when(courseMapper.toDto(entity)).thenReturn(response);

        CourseResponseDTO result =
                courseService.createCourse(1L, request);

        assertNotNull(result);
        verify(userRepository).findById(1L);
        verify(categoryRepository).findById(2L);
        verify(courseRepository).save(entity);
    }

    @Test
    void createCourse_teacherNotFound() {
        CourseRequestDTO request = new CourseRequestDTO();
        request.setCategoryId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> courseService.createCourse(1L, request));
    }

    @Test
    void createCourse_categoryNotFound() {
        UserEntity teacher = new UserEntity();
        teacher.setId(1L);

        CourseRequestDTO request = new CourseRequestDTO();
        request.setCategoryId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> courseService.createCourse(1L, request));
    }
}
