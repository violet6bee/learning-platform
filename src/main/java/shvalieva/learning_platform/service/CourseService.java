package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {

    private CourseRepository courseRepository;
    private CourseMapper courseMapper;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    public CourseResponseDTO getCourseById(Long id) {
        CourseEntity entity = courseRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Курс с id: " + id + " не найден"));
        return courseMapper.toDto(entity);
    }

    public CourseResponseDTO createCourse(Long teacherId, CourseRequestDTO dto) {
        UserEntity teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Преподаватель не найден"));
        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Категория не найдена"));
        CourseEntity course = courseMapper.fromRequestDto(dto);
        course.setTeacher(teacher);
        course.setCategory(category);
        courseRepository.save(course);
        return courseMapper.toDto(course);
    }
}
