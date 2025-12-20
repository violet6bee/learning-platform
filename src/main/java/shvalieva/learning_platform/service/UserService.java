package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shvalieva.learning_platform.dto.UserRequestDTO;
import shvalieva.learning_platform.dto.UserResponseDTO;
import shvalieva.learning_platform.entity.UserEntity;
import shvalieva.learning_platform.mapper.UserMapper;
import shvalieva.learning_platform.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Пользователь с id: " + id + " не найден"));
        return userMapper.toDto(entity);
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        UserEntity entity = userMapper.fromRequestDto(userRequestDTO);
        entity.setId(null);
        UserEntity savedEntity = userRepository.save(entity);
        return userMapper.toDto(savedEntity);
    }
}
