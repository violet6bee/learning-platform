package shvalieva.learning_platform.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shvalieva.learning_platform.dto.LessonRequestDTO;
import shvalieva.learning_platform.dto.LessonResponseDTO;
import shvalieva.learning_platform.repository.LessonRepository;
import shvalieva.learning_platform.service.LessonService;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@AllArgsConstructor
public class LessonController {

    private LessonService lessonService;
    private LessonRepository lessonRepository;

    @GetMapping
    public List<LessonResponseDTO> getAllTags() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{id}")
    public LessonResponseDTO getLessonById(@PathVariable("id") Long id) {
        return lessonService.getLessonById(id);
    }

    @PostMapping
    public LessonResponseDTO createLesson(@Valid @RequestBody LessonRequestDTO dto) {
        return lessonService.createLesson(dto);
    }

    @PutMapping("/{id}")
    public LessonResponseDTO updateLesson(@PathVariable("id") Long id, @Valid @RequestBody LessonRequestDTO dto) {
        return lessonService.updateLesson(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteLesson(@PathVariable Long id) {
        lessonRepository.deleteById(id);
    }

}
