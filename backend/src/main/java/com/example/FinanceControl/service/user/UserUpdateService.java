package com.example.FinanceControl.service.user;

import com.example.FinanceControl.dto.request.userUpdate.UserUpdateEmailRequestDTO;
import com.example.FinanceControl.dto.request.userUpdate.UserUpdateNameRequestDTO;
import com.example.FinanceControl.dto.request.userUpdate.UserUpdatePasswordRequestDTO;
import com.example.FinanceControl.dto.request.userUpdate.UserUpdateRoleRequestDTO;
import com.example.FinanceControl.exception.businessExceptions.EmailNotFoundException;
import com.example.FinanceControl.exception.businessExceptions.InvalidPasswordException;
import com.example.FinanceControl.exception.businessExceptions.RoleNotFoundException;
import com.example.FinanceControl.model.Role;
import com.example.FinanceControl.model.User;
import com.example.FinanceControl.repository.RoleRepository;
import com.example.FinanceControl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserUpdateService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public void updateEmail(UserUpdateEmailRequestDTO dto){
        User user = getAuthenticatedUser();
        validatePassword(dto.getPassword(), user.getPassword());

        user.setEmail(dto.getEmail());
        userRepository.save(user);

    }

    public void updateName(UserUpdateNameRequestDTO dto){
        User user = getAuthenticatedUser();
        validatePassword(dto.getPassword(), user.getPassword());

        user.setName(dto.getName());
        userRepository.save(user);

    }

    public void updatePassword(UserUpdatePasswordRequestDTO dto){
        User user = getAuthenticatedUser();
        validatePassword(dto.getPassword(), user.getPassword());

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

    }

    public void updateRole(UserUpdateRoleRequestDTO dto){
        User user = getAuthenticatedUser();

        Role newRole = roleRepository.findByName(dto.getRoleName())
                .orElseThrow(() -> new RoleNotFoundException("Role não encontrada: " + dto.getRoleName()));

        // Substitui a role antiga pela nova
        user.setRole(newRole);

        userRepository.save(user);

    }

    private User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("Email nao encontrado."));
    }

    private void validatePassword(String inputPassword, String userPassword) {
        if (!passwordEncoder.matches(inputPassword, userPassword)) {
            throw new InvalidPasswordException("Senha incorreta.");
        }
    }

    private boolean isUserLevelRole(String roleName) {
        return roleName.equals("ROLE_USER_FREE") || roleName.equals("ROLE_USER_PREMIUM");
    }
}
