package shvalieva.learning_platform.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shvalieva.learning_platform.dto.AnswerOptionRequestDTO;
import shvalieva.learning_platform.dto.AnswerOptionResponseDTO;
import shvalieva.learning_platform.service.AnswerOptionService;

import java.util.List;

@RestController
@RequestMapping("/answer-options")
@AllArgsConstructor
public class AnswerOptionController {

    private AnswerOptionService answerOptionService;

    @GetMapping("/{id}")
    public ResponseEntity<AnswerOptionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(answerOptionService.getById(id));
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<AnswerOptionResponseDTO>> getByQuestionId(
            @PathVariable Long questionId) {
        return ResponseEntity.ok(answerOptionService.getByQuestionId(questionId));
    }

    @PostMapping
    public ResponseEntity<AnswerOptionResponseDTO> createAnswerOption(
            @Valid @RequestBody AnswerOptionRequestDTO dto) {
        AnswerOptionResponseDTO created = answerOptionService.createAnswerOption(dto);
        return ResponseEntity.status(201).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswerOption(@PathVariable Long id) {
        answerOptionService.deleteAnswerOption(id);
        return ResponseEntity.noContent().build();
    }
}
