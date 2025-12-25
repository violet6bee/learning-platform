package shvalieva.learning_platform.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shvalieva.learning_platform.dto.QuizTakeRequestDTO;
import shvalieva.learning_platform.dto.QuizTakeResponseDTO;
import shvalieva.learning_platform.service.QuizTakeService;

@RestController
@RequestMapping("/quizzes")
@AllArgsConstructor
public class QuizTakeController {

    private QuizTakeService quizTakeService;

    @PostMapping("/take")
    public ResponseEntity<QuizTakeResponseDTO> takeQuiz(
            @RequestBody QuizTakeRequestDTO dto) {
        return ResponseEntity.ok(quizTakeService.takeQuiz(dto));
    }
}
