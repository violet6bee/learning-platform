package shvalieva.learning_platform.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shvalieva.learning_platform.dto.SubmissionRequestDTO;
import shvalieva.learning_platform.dto.SubmissionResponseDTO;
import shvalieva.learning_platform.service.SubmissionService;

import java.util.List;

@RestController
@RequestMapping("/submissions")
@AllArgsConstructor
public class SubmissionController {

    private SubmissionService submissionService;

    @GetMapping("/{id}")
    public ResponseEntity<SubmissionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(submissionService.getById(id));
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<SubmissionResponseDTO>> getByAssignment(@PathVariable Long assignmentId) {
        return ResponseEntity.ok(submissionService.getByAssignment(assignmentId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<SubmissionResponseDTO>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(submissionService.getByStudent(studentId));
    }

    @PostMapping
    public ResponseEntity<SubmissionResponseDTO> create(@RequestBody SubmissionRequestDTO dto) {
        return ResponseEntity.ok(submissionService.create(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        submissionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Преподаватель выставляет оценку
     */
    @PutMapping("/{submissionId}/grade")
    public SubmissionResponseDTO grade(
            @PathVariable Long submissionId,
            @RequestParam byte score,
            @RequestParam(required = false) String feedback
    ) {
        return submissionService.gradeSubmission(submissionId, score, feedback);
    }
}
