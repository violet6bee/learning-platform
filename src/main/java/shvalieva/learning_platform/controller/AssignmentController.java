package shvalieva.learning_platform.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shvalieva.learning_platform.dto.AssignmentRequestDTO;
import shvalieva.learning_platform.dto.AssignmentResponseDTO;
import shvalieva.learning_platform.service.AssignmentService;

import java.util.List;

@RestController
@RequestMapping("/assignments")
@AllArgsConstructor
public class AssignmentController {

    private AssignmentService assignmentService;

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(assignmentService.getById(id));
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<AssignmentResponseDTO>> getByLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok(assignmentService.getByLesson(lessonId));
    }

    @PostMapping
    public ResponseEntity<AssignmentResponseDTO> create(@RequestBody AssignmentRequestDTO dto) {
        return ResponseEntity.ok(assignmentService.create(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        assignmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
