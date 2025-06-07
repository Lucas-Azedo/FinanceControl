package com.example.FinanceControl.service.user;

import com.example.FinanceControl.dto.request.UserRequestDTO;
import com.example.FinanceControl.dto.response.UserResponseDTO;
import com.example.FinanceControl.dto.response.UserSignUpResponseDTO;
import com.example.FinanceControl.exception.EmailAlreadyExistsException;
import com.example.FinanceControl.exception.IdNotFoundException;
import com.example.FinanceControl.exception.RoleNotFoundException;
import com.example.FinanceControl.model.Role;
import com.example.FinanceControl.model.User;
import com.example.FinanceControl.repository.RoleRepository;
import com.example.FinanceControl.repository.UserRepository;
import com.example.FinanceControl.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    public UserSignUpResponseDTO createUser(UserRequestDTO dto){
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("E-mail já cadastrado.");
        }

        Role defaultRole = roleRepository.findByName("ROLE_USER_FREE")
                .orElseThrow(() -> new RoleNotFoundException("Role padrão não encontrada."));

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of(defaultRole));

        user = userRepository.save(user);

        String token = tokenService.generateToken(user);

        return new UserSignUpResponseDTO(user.getId(), token, user.getName(), user.getEmail());
    }

    public UserResponseDTO getUserById(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Id nao encontrado"));

        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), getRoleNames(user));
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        getRoleNames(user)))
                .toList();
    }

    public void deleteUser(UUID id){
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new IdNotFoundException("Id nao encontrado"));

        userRepository.delete(user);
    }

    public void changeUserRole(UUID userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException("Usuário não encontrado"));

        Role newRole = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException("Role não encontrada"));

        user.setRoles(Set.of(newRole));
        userRepository.save(user);
    }

    private Set<String> getRoleNames(User user) {
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
