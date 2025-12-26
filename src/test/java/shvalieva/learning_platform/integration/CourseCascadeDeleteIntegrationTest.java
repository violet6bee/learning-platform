package shvalieva.learning_platform.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import shvalieva.learning_platform.entity.*;
import shvalieva.learning_platform.enums.Role;
import shvalieva.learning_platform.repository.*;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class CourseCascadeDeleteIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Test
    void cascadeDeleteCourse_shouldDeleteModulesAndQuizzes() {
        // ---------- USER ----------
        UserEntity teacher = new UserEntity(
                "Anna",
                "Ivanova",
                "teacher@test.com",
                Role.TEACHER,
                "password"
        );
        teacher = userRepository.save(teacher);

        // ---------- CATEGORY ----------
        CategoryEntity category = new CategoryEntity();
        category.setName("Programming");
        category = categoryRepository.save(category);

        // ---------- COURSE ----------
        CourseEntity course = new CourseEntity();
        course.setTitle("Spring Boot");
        course.setDescription("Spring Boot course");
        course.setTeacher(teacher);
        course.setCategory(category);
        course = courseRepository.save(course);

        // ---------- MODULE ----------
        ModuleEntity module = new ModuleEntity();
        module.setTitle("Introduction");
        module.setCourse(course);
        module = moduleRepository.save(module);

        // ---------- QUIZ ----------
        QuizEntity quiz = new QuizEntity();
        quiz.setTitle("Intro Quiz");
        quiz.setCourse(course);
        quiz = quizRepository.save(quiz);

        // ---------- ASSERT CREATED ----------
        assertNotNull(course.getId());
        assertNotNull(module.getId());
        assertNotNull(quiz.getId());

        // ---------- DELETE COURSE ----------
        courseRepository.delete(course);
        courseRepository.flush();

        // ---------- ASSERT CASCADE DELETE ----------
        assertFalse(courseRepository.existsById(course.getId()));
        assertFalse(moduleRepository.existsById(module.getId()));
        assertFalse(quizRepository.existsById(quiz.getId()));
    }
}
