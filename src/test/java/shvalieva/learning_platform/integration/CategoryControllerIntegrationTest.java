package shvalieva.learning_platform.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import shvalieva.learning_platform.dto.CategoryRequestDTO;
import shvalieva.learning_platform.entity.CategoryEntity;
import shvalieva.learning_platform.repository.CategoryRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CategoryControllerIntegrationTest {

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
    private CategoryRepository categoryRepository;

    @Test
    void createAndGetCategory_success() throws Exception {
        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName("Programming");

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Programming"));

        CategoryEntity saved = categoryRepository.findAll().get(0);

        mockMvc.perform(get("/categories/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Programming"));
    }

    @Test
    void getAllCategories_success() throws Exception {
        categoryRepository.save(new CategoryEntity("Math", null));
        categoryRepository.save(new CategoryEntity("Science", null));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void updateCategory_success() throws Exception {
        CategoryEntity entity = new CategoryEntity();
        entity.setName("Old");
        entity = categoryRepository.save(entity);

        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName("New");

        mockMvc.perform(put("/categories/{id}", entity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New"));
    }

    @Test
    void deleteCategory_success() throws Exception {
        CategoryEntity entity = new CategoryEntity();
        entity.setName("Delete");
        entity = categoryRepository.save(entity);

        mockMvc.perform(delete("/categories/{id}", entity.getId()))
                .andExpect(status().isNoContent());
    }
}
