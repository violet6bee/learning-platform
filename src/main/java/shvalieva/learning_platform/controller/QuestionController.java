package shvalieva.learning_platform.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shvalieva.learning_platform.dto.QuestionRequestDTO;
import shvalieva.learning_platform.dto.QuestionResponseDTO;
import shvalieva.learning_platform.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/questions")
@AllArgsConstructor
public class QuestionController {

    private QuestionService questionService;

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getById(id));
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuestionResponseDTO>> getByQuizId(@PathVariable Long quizId) {
        return ResponseEntity.ok(questionService.getByQuizId(quizId));
    }

    @PostMapping
    public ResponseEntity<QuestionResponseDTO> createQuestion(
            @Valid @RequestBody QuestionRequestDTO dto) {
        QuestionResponseDTO created = questionService.createQuestion(dto);
        return ResponseEntity.status(201).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
