package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shvalieva.learning_platform.dto.SubmissionRequestDTO;
import shvalieva.learning_platform.dto.SubmissionResponseDTO;
import shvalieva.learning_platform.entity.AssignmentEntity;
import shvalieva.learning_platform.entity.SubmissionEntity;
import shvalieva.learning_platform.entity.UserEntity;
import shvalieva.learning_platform.mapper.SubmissionMapper;
import shvalieva.learning_platform.repository.AssignmentRepository;
import shvalieva.learning_platform.repository.SubmissionRepository;
import shvalieva.learning_platform.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SubmissionService {

    private SubmissionRepository submissionRepository;
    private AssignmentRepository assignmentRepository;
    private UserRepository userRepository;
    private SubmissionMapper submissionMapper;

    @Transactional(readOnly = true)
    public SubmissionResponseDTO getById(Long id) {
        SubmissionEntity entity = submissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Решение задание с id:  " + id + " не найдено"));
        return submissionMapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<SubmissionResponseDTO> getByAssignment(Long assignmentId) {
        return submissionRepository.findAllByAssignmentId(assignmentId)
                .stream()
                .map(submissionMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SubmissionResponseDTO> getByStudent(Long studentId) {
        return submissionRepository.findAllByStudentId(studentId)
                .stream()
                .map(submissionMapper::toDto)
                .toList();
    }

    @Transactional
    public SubmissionResponseDTO create(SubmissionRequestDTO dto) {
        AssignmentEntity assignment = assignmentRepository.findById(dto.getAssignmentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Задание с id:  " + dto.getAssignmentId() + " не найдено"));
        UserEntity student = userRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Пользователь с id: " + dto.getStudentId() + " не найден"));
        SubmissionEntity entity = submissionMapper.fromRequestDto(dto);
        entity.setAssignment(assignment);
        entity.setStudent(student);
        SubmissionEntity saved = submissionRepository.save(entity);
        return submissionMapper.toDto(saved);
    }

    @Transactional
    public void delete(Long id) {
        submissionRepository.deleteById(id);
    }


    /**
     * Студент отправляет работу
     */
    @Transactional
    public SubmissionResponseDTO submitAssignment(
            Long studentId,
            Long assignmentId,
            SubmissionRequestDTO dto
    ) {
        if (submissionRepository.existsByAssignmentIdAndStudentId(assignmentId, studentId)) {
            throw new IllegalStateException("Задание уже отправленно этим студентом");
        }
        AssignmentEntity assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Задание не найдено"));
        UserEntity student = userRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Студент не найден"));
        SubmissionEntity submission = submissionMapper.fromRequestDto(dto);
        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setSubmittedAt(LocalDateTime.now());

        return submissionMapper.toDto(
                submissionRepository.save(submission)
        );
    }

    /**
     * Преподаватель смотрит все решения по заданию
     */
    @Transactional(readOnly = true)
    public List<SubmissionResponseDTO> getSubmissionsByAssignment(Long assignmentId) {
        return submissionRepository.findByAssignmentId(assignmentId)
                .stream()
                .map(submissionMapper::toDto)
                .toList();
    }

    /**
     * Студент смотрит свои решения
     */
    @Transactional(readOnly = true)
    public List<SubmissionResponseDTO> getSubmissionsByStudent(Long studentId) {
        return submissionRepository.findByStudentId(studentId)
                .stream()
                .map(submissionMapper::toDto)
                .toList();
    }

    /**
     * Преподаватель выставляет оценку
     */
    @Transactional
    public SubmissionResponseDTO gradeSubmission(Long submissionId, byte score, String feedback) {
        SubmissionEntity submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new EntityNotFoundException("Решение не найдено"));
        submission.setScore(score);
        submission.setFeedback(feedback);
        return submissionMapper.toDto(submission);
    }
}
