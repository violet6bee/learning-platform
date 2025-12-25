package shvalieva.learning_platform.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shvalieva.learning_platform.dto.QuizSubmissionRequestDTO;
import shvalieva.learning_platform.dto.QuizSubmissionResponseDTO;
import shvalieva.learning_platform.service.QuizSubmissionService;

import java.util.List;

@RestController
@RequestMapping("/quiz-submissions")
@AllArgsConstructor
public class QuizSubmissionController {

    private QuizSubmissionService quizSubmissionService;

    @GetMapping("/{id}")
    public ResponseEntity<QuizSubmissionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(quizSubmissionService.getById(id));
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuizSubmissionResponseDTO>> getByQuiz(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizSubmissionService.getByQuiz(quizId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<QuizSubmissionResponseDTO>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(quizSubmissionService.getByStudent(studentId));
    }

    @PostMapping
    public ResponseEntity<QuizSubmissionResponseDTO> create(
            @RequestBody QuizSubmissionRequestDTO dto) {
        return ResponseEntity.ok(quizSubmissionService.create(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        quizSubmissionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
