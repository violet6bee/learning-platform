package shvalieva.learning_platform.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shvalieva.learning_platform.entity.ModuleEntity;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleEntity, Long> {

    @EntityGraph(attributePaths = "lessons")
    List<ModuleEntity> findByCourseId(Long courseId);
}
