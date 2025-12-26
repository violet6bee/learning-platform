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
import shvalieva.learning_platform.dto.AnswerOptionRequestDTO;
import shvalieva.learning_platform.entity.AnswerOptionEntity;
import shvalieva.learning_platform.entity.QuestionEntity;
import shvalieva.learning_platform.repository.AnswerOptionRepository;
import shvalieva.learning_platform.repository.QuestionRepository;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AnswerOptionControllerIntegrationTest {

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
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    private QuestionEntity question;

    @BeforeEach
    void setUp() {
        answerOptionRepository.deleteAll();
        questionRepository.deleteAll();

        question = new QuestionEntity();
        question.setText("Sample question");
        question = questionRepository.save(question);
    }

    @Test
    void createAnswerOption_shouldReturnCreatedOption() throws Exception {
        AnswerOptionRequestDTO request = new AnswerOptionRequestDTO();
        request.setQuestionId(question.getId());
        request.setText("Option A");
        request.isCorrect();

        mockMvc.perform(post("/answer-options")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.text").value("Option A"))
                .andExpect(jsonPath("$.isCorrect").value(true))
                .andExpect(jsonPath("$.questionId").value(question.getId()));
    }

    @Test
    void getById_shouldReturnAnswerOption() throws Exception {
        AnswerOptionEntity option = new AnswerOptionEntity(
                question,
                "Option X",
                false
        );
        option = answerOptionRepository.save(option);

        mockMvc.perform(get("/answer-options/{id}", option.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(option.getId()))
                .andExpect(jsonPath("$.text").value("Option X"))
                .andExpect(jsonPath("$.isCorrect").value(false))
                .andExpect(jsonPath("$.questionId").value(question.getId()));
    }

    @Test
    void getByQuestionId_shouldReturnListOfOptions() throws Exception {
        AnswerOptionEntity option1 = new AnswerOptionEntity(question, "Option 1", true);
        AnswerOptionEntity option2 = new AnswerOptionEntity(question, "Option 2", false);

        answerOptionRepository.saveAll(List.of(option1, option2));

        mockMvc.perform(get("/answer-options/question/{questionId}", question.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].text",
                        containsInAnyOrder("Option 1", "Option 2")));
    }

    @Test
    void deleteAnswerOption_shouldRemoveOption() throws Exception {
        AnswerOptionEntity option = new AnswerOptionEntity(
                question,
                "Delete me",
                true
        );
        option = answerOptionRepository.save(option);

        mockMvc.perform(delete("/answer-options/{id}", option.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/answer-options/{id}", option.getId()))
                .andExpect(status().isNotFound());
    }
}
