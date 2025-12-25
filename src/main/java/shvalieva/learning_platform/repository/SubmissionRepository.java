package shvalieva.learning_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shvalieva.learning_platform.entity.SubmissionEntity;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<SubmissionEntity, Long> {
    List<SubmissionEntity> findAllByAssignmentId(Long assignmentId);
    List<SubmissionEntity> findAllByStudentId(Long studentId);
    List<SubmissionEntity> findByAssignmentId(Long assignmentId);
    List<SubmissionEntity> findByStudentId(Long studentId);
    boolean existsByAssignmentIdAndStudentId(Long assignmentId, Long studentId);
}
