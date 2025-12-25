package shvalieva.learning_platform.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shvalieva.learning_platform.dto.QuizRequestDTO;
import shvalieva.learning_platform.dto.QuizResponseDTO;
import shvalieva.learning_platform.service.QuizService;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
@AllArgsConstructor
public class QuizController {

    private QuizService quizService;

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponseDTO> getQuizById(@PathVariable Long id) {
        QuizResponseDTO dto = quizService.getQuizById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<QuizResponseDTO>> getAllQuizzes() {
        List<QuizResponseDTO> dto = quizService.getAllQuizzes();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<QuizResponseDTO> createQuiz(@Valid @RequestBody QuizRequestDTO quizRequestDTO) {
        QuizResponseDTO createdDto = quizService.createQuiz(quizRequestDTO);
        return ResponseEntity.ok(createdDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }
}