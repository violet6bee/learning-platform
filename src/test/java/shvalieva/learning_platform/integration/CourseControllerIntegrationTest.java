package shvalieva.learning_platform.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import shvalieva.learning_platform.dto.CourseRequestDTO;
import shvalieva.learning_platform.entity.CategoryEntity;
import shvalieva.learning_platform.entity.CourseEntity;
import shvalieva.learning_platform.entity.UserEntity;
import shvalieva.learning_platform.enums.Role;
import shvalieva.learning_platform.repository.CategoryRepository;
import shvalieva.learning_platform.repository.CourseRepository;
import shvalieva.learning_platform.repository.UserRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CourseControllerIntegrationTest {

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
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CourseRepository courseRepository;

    private UserEntity teacher;
    private CategoryEntity category;

    @BeforeEach
    void setUp() {
        courseRepository.deleteAll();
        userRepository.deleteAll();
        categoryRepository.deleteAll();

        teacher = new UserEntity();
        teacher.setFirstName("Integration");
        teacher.setSecondName("Teacher");
        teacher.setEmail("teacher@test.com");
        teacher.setRole(Role.TEACHER);
        teacher.setPassword("password");
        teacher = userRepository.save(teacher);

        category = new CategoryEntity();
        category.setName("Programming");
        category = categoryRepository.save(category);
    }

    @Test
    void createCourse_success() throws Exception {
        CourseRequestDTO request = new CourseRequestDTO();
        request.setTitle("Spring Boot Course");
        request.setDescription("Learn Spring Boot");
        request.setCategoryId(category.getId());

        mockMvc.perform(post("/courses/users/{userId}/courses", teacher.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Spring Boot Course"))
                .andExpect(jsonPath("$.description").value("Learn Spring Boot"));
    }

    @Test
    void getCourseById_success() throws Exception {
        CourseEntity course = new CourseEntity();
        course.setTitle("Java Course");
        course.setDescription("Java Basics");
        course.setTeacher(teacher);
        course.setCategory(category);
        course = courseRepository.save(course);

        mockMvc.perform(get("/courses/{id}", course.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Course"))
                .andExpect(jsonPath("$.description").value("Java Basics"));
    }

    @Test
    void getAllCourses_success() throws Exception {
        CourseEntity course = new CourseEntity();
        course.setTitle("Course 1");
        course.setDescription("Desc");
        course.setTeacher(teacher);
        course.setCategory(category);
        courseRepository.save(course);

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    void getCourseById_notFound() throws Exception {
        mockMvc.perform(get("/courses/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}
