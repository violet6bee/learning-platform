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
import shvalieva.learning_platform.entity.CategoryEntity;
import shvalieva.learning_platform.entity.CourseEntity;
import shvalieva.learning_platform.entity.UserEntity;
import shvalieva.learning_platform.enums.Role;
import shvalieva.learning_platform.repository.CategoryRepository;
import shvalieva.learning_platform.repository.CourseRepository;
import shvalieva.learning_platform.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class CourseCrudIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:17.5")
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
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testSaveAndFindCourse() {
        // --- создаём и сохраняем преподавателя ---
        UserEntity teacher = new UserEntity();
        teacher.setFirstName("Test");
        teacher.setSecondName("Teacher");
        teacher.setEmail("teacher@test.com");
        teacher.setRole(Role.TEACHER);
        teacher.setPassword("password");
        teacher = userRepository.save(teacher);

        // --- создаём и сохраняем категорию ---
        CategoryEntity category = new CategoryEntity();
        category.setName("Programming");
        category = categoryRepository.save(category);

        // --- создаём курс ---
        CourseEntity course = new CourseEntity();
        course.setTitle("Test Course");
        course.setDescription("Test Description");
        course.setTeacher(teacher);
        course.setCategory(category);

        CourseEntity savedCourse = courseRepository.save(course);

        // --- получаем курс из БД ---
        CourseEntity foundCourse =
                courseRepository.findById(savedCourse.getId()).orElse(null);

        // --- проверки ---
        assertNotNull(foundCourse);
        assertEquals("Test Course", foundCourse.getTitle());
        assertEquals("Test Description", foundCourse.getDescription());
        assertEquals(teacher.getId(), foundCourse.getTeacher().getId());
        assertEquals(category.getId(), foundCourse.getCategory().getId());
    }
}
