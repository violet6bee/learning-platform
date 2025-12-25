package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shvalieva.learning_platform.dto.AnswerOptionRequestDTO;
import shvalieva.learning_platform.dto.AnswerOptionResponseDTO;
import shvalieva.learning_platform.entity.AnswerOptionEntity;
import shvalieva.learning_platform.entity.QuestionEntity;
import shvalieva.learning_platform.mapper.AnswerOptionMapper;
import shvalieva.learning_platform.repository.AnswerOptionRepository;
import shvalieva.learning_platform.repository.QuestionRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AnswerOptionService {

    private AnswerOptionRepository answerOptionRepository;
    private QuestionRepository questionRepository;
    private AnswerOptionMapper answerOptionMapper;

    @Transactional(readOnly = true)
    public AnswerOptionResponseDTO getById(Long id) {
        AnswerOptionEntity entity = answerOptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Выбор ответа не найден"));
        return answerOptionMapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<AnswerOptionResponseDTO> getByQuestionId(Long questionId) {
        return answerOptionRepository.findByQuestionId(questionId).stream()
                .map(answerOptionMapper::toDto)
                .toList();
    }

    @Transactional
    public AnswerOptionResponseDTO createAnswerOption(AnswerOptionRequestDTO dto) {

        QuestionEntity question = questionRepository.findById(dto.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("Вопрос не найден"));
        AnswerOptionEntity entity = answerOptionMapper.fromRequestDto(dto);
        entity.setQuestion(question);
        AnswerOptionEntity saved = answerOptionRepository.save(entity);
        return answerOptionMapper.toDto(saved);
    }

    @Transactional
    public void deleteAnswerOption(Long id) {
        if (!answerOptionRepository.existsById(id)) {
            throw new EntityNotFoundException("Выбор ответа не найден");
        }
        answerOptionRepository.deleteById(id);
    }
}
