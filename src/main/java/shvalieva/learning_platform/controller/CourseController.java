package shvalieva.learning_platform.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shvalieva.learning_platform.dto.CourseRequestDTO;
import shvalieva.learning_platform.dto.CourseResponseDTO;
import shvalieva.learning_platform.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    private CourseService courseService;

    @GetMapping
    public List<CourseResponseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById (@PathVariable("id") Long id) {
        CourseResponseDTO courseResponseDTO = courseService.getCourseById(id);
        return ResponseEntity.ok(courseResponseDTO);
    }

    @PostMapping("/users/{userId}/courses")
    public ResponseEntity<CourseResponseDTO> createCourse(@PathVariable Long userId,
                                                          @Valid @RequestBody CourseRequestDTO courseRequestDTO) {
        CourseResponseDTO createdCourse = courseService.createCourse(userId, courseRequestDTO);
        return ResponseEntity.ok(createdCourse);
    }
}
