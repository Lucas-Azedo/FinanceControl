package com.example.FinanceControl.unit;


import com.example.FinanceControl.dto.request.UserRequestDTO;
import com.example.FinanceControl.dto.response.UserResponseDTO;
import com.example.FinanceControl.dto.response.UserSignUpResponseDTO;
import com.example.FinanceControl.exception.businessExceptions.IdNotFoundException;
import com.example.FinanceControl.model.Role;
import com.example.FinanceControl.model.User;
import com.example.FinanceControl.repository.RoleRepository;
import com.example.FinanceControl.repository.UserRepository;
import com.example.FinanceControl.security.TokenService;
import com.example.FinanceControl.service.user.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    UserService userService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup(){
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception{
        closeable.close();
    }

    @Test
    void createUser_shouldCreateUserSuccess(){
        //Simula um DTO de request
        UserRequestDTO dto = new UserRequestDTO();

        dto.setName("john");
        dto.setEmail("john@email.com");
        dto.setPassword("123456");

        //Cria a role default, já que data.sql não é inicializado
        String defaultRole = "ROLE_USER_FREE";
        Role role = new Role();
        role.setName(defaultRole);

        //Simula a criação de um usuário no banco de dados para bater com os dados do DTO de request
        User testUser = new User();
        testUser.setName("john");
        testUser.setEmail("john@email.com");
        testUser.setPassword("encodedPassword");
        testUser.setRole(role);

        //Quando (comando) for enviado
        //Deve retornar (isso)
        when(userRepository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.empty());

        when(roleRepository.findByName(defaultRole))
                .thenReturn(Optional.of(role));

        when(passwordEncoder.encode(dto.getPassword()))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(testUser);

        when(tokenService.generateToken(any(User.class)))
                .thenReturn("fakeToken");

        UserSignUpResponseDTO response = userService.createUser(dto);

        //Certifica que o DTO e o usuário criado batem
        assertNotNull(response);
        assertEquals("john", response.getName());
        assertEquals("john@email.com", response.getEmail());
        assertEquals("fakeToken", response.getToken());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserById_shouldGetUserSuccess() {
        //Cria a role default, já que data.sql não é inicializado
        String defaultRole = "ROLE_USER_FREE";
        Role role = new Role();
        role.setName(defaultRole);

        // Cria um ID fixo
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");

        //Simula a criação de um usuário no banco de dados para bater com os dados do DTO de request
        User testUser = new User();
        testUser.setId(userId);
        testUser.setName("john");
        testUser.setEmail("john@email.com");
        testUser.setPassword("encodedPassword");
        testUser.setRole(role);

        //Simula o comportamento do userRepository - quando achar o id, deve retornar isso
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(testUser));

        UserResponseDTO response = userService.getUserById(userId);

        //Certificações para que o usuário de teste e o resultado do serviço batem
        assertNotNull(response);
        assertEquals(userId, response.getId());
        assertEquals("john", response.getName());
        assertEquals("john@email.com", response.getEmail());
        assertEquals("ROLE_USER_FREE", response.getRoles());
    }

    @Test
    void getAllUsers_shouldGetAllUsersSuccess(){
        String defaultRole = "ROLE_USER_FREE";
        Role role = new Role();
        role.setName(defaultRole);

        UUID userId1 = UUID.fromString("00000000-0000-0000-0000-000000000000");
        UUID userId2 = UUID.fromString("11111111-1111-1111-1111-111111111111");

        User user1 = new User();
        user1.setId(userId1);
        user1.setName("John");
        user1.setEmail("john@email.com");
        user1.setRole(role);

        User user2 = new User();
        user2.setId(userId2);
        user2.setName("Jane");
        user2.setEmail("jane@email.com");
        user2.setRole(role);

        when(userRepository.findAll())
                .thenReturn(List.of(user1,user2));

        List<UserResponseDTO> response = userService.getAllUsers();

        assertEquals(2, response.size());
        assertEquals("John", response.get(0).getName());
        assertEquals("Jane", response.get(1).getName());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void deleteUser_shouldDeleteWhenUserExists(){
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");

        User user = new User();
        user.setId(userId);
        user.setName("John");
        user.setEmail("john@email.com");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        userService.deleteUser(userId);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUser_shouldThrowWhenUserNotFound(){
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> {
            userService.deleteUser(userId);
        });

        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void changeUserRole_shouldChangeRoleSuccessfully() {
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");

        Role oldRole = new Role();
        oldRole.setName("ROLE_USER_FREE");

        Role newRole = new Role();
        newRole.setName("ROLE_USER_PREMIUM");

        User user = new User();
        user.setId(userId);
        user.setName("John");
        user.setEmail("john@email.com");
        user.setRole(oldRole);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findByName("ROLE_USER_PREMIUM")).thenReturn(Optional.of(newRole));

        userService.changeUserRole(userId, "ROLE_USER_PREMIUM");

        assertEquals("ROLE_USER_PREMIUM", user.getRole().getName());
        verify(userRepository, times(1)).save(user);
    }
}
