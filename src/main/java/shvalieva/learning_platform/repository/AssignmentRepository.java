package shvalieva.learning_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shvalieva.learning_platform.entity.AssignmentEntity;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long> {
    List<AssignmentEntity> findAllByLessonId(Long lessonId);
}
