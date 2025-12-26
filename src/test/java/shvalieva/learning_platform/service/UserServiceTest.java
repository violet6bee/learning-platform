package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import shvalieva.learning_platform.dto.UserRequestDTO;
import shvalieva.learning_platform.dto.UserResponseDTO;
import shvalieva.learning_platform.entity.UserEntity;
import shvalieva.learning_platform.enums.Role;
import shvalieva.learning_platform.mapper.UserMapper;
import shvalieva.learning_platform.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers_success() {
        UserEntity entity = new UserEntity();
        UserResponseDTO dto = new UserResponseDTO();

        when(userRepository.findAll()).thenReturn(List.of(entity));
        when(userMapper.toDto(entity)).thenReturn(dto);

        List<UserResponseDTO> result = userService.getAllUsers();

        assertEquals(1, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_success() {
        UserEntity entity = new UserEntity();
        UserResponseDTO dto = new UserResponseDTO();

        when(userRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(userMapper.toDto(entity)).thenReturn(dto);

        UserResponseDTO result = userService.getUserById(1L);

        assertNotNull(result);
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.getUserById(1L));
    }

    @Test
    void createUser_success() {
        UserRequestDTO request = new UserRequestDTO();
        request.setFirstName("Ivan");
        request.setSecondName("Ivanov");
        request.setEmail("ivan@mail.com");
        request.setPassword("123");

        UserEntity entity = new UserEntity();
        UserResponseDTO response = new UserResponseDTO();

        when(userMapper.fromRequestDto(request)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toDto(entity)).thenReturn(response);

        UserResponseDTO created = userService.createUser(request);

        assertNotNull(created);
        verify(userRepository).save(entity);
    }

    @Test
    void createUser_duplicateEmail() {
        UserRequestDTO request = new UserRequestDTO();

        UserEntity entity = new UserEntity();

        when(userMapper.fromRequestDto(request)).thenReturn(entity);
        when(userRepository.save(entity))
                .thenThrow(DataIntegrityViolationException.class);

        assertThrows(RuntimeException.class,
                () -> userService.createUser(request));
    }
}
