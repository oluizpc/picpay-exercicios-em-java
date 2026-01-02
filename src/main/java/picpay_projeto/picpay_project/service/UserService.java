package picpay_projeto.picpay_project.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import picpay_projeto.picpay_project.dto.UserUpdateDTO;
import picpay_projeto.picpay_project.dto.User.UserRequestDTO;
import picpay_projeto.picpay_project.dto.User.UserResponseDTO;
import picpay_projeto.picpay_project.exception.BusinessException;
import picpay_projeto.picpay_project.model.User;
import picpay_projeto.picpay_project.repository.UserRepository;
import picpay_projeto.picpay_project.utils.PasswordUtils;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDTO createUser(UserRequestDTO userDTO) {

        // Validações de CPF e email duplicados
        if (userRepository.existsByCpf(userDTO.cpf())) {
            throw new BusinessException("CPF already registered");
        }
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new BusinessException("Email already registered");
        }

        // Criação do usuário com hash da senha e saldo inicial zero
        User user = User.builder()
                .nomeCompleto(userDTO.nomeCompleto())
                .cpf(userDTO.cpf())
                .email(userDTO.email())
                .senha(PasswordUtils.hashPassword(userDTO.senha()))
                .tipoUsuario(userDTO.tipoUsuario())
                .balance(BigDecimal.ZERO) // sempre inicializa o saldo com zero
                .build();

        userRepository.save(user);

        // Retorna DTO com saldo incluso
        return new UserResponseDTO(
            user.getId(),
            user.getNomeCompleto(),
            user.getCpf(),
            user.getEmail(),
            user.getTipoUsuario(),
            user.getBalance()
        );
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getNomeCompleto(),
                        user.getCpf(),
                        user.getEmail(),
                        user.getTipoUsuario(),
                        user.getBalance()))
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User not found with id: " + id));

        return new UserResponseDTO(
                user.getId(),
                user.getNomeCompleto(),
                user.getCpf(),
                user.getEmail(),
                user.getTipoUsuario(),
                user.getBalance()
        );
    }

    public UserResponseDTO deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User not found with id: " + id));

        userRepository.delete(user);

        return new UserResponseDTO(
                user.getId(),
                user.getNomeCompleto(),
                user.getCpf(),
                user.getEmail(),
                user.getTipoUsuario(),
                user.getBalance()
        );
    }


    public UserResponseDTO updateUser (Long id, UserUpdateDTO dto) {
        User user = userRepository.findById(id)
        .orElseThrow(() -> new BusinessException("User not found with id: " + id));

         // Atualiza apenas os campos que não forem nulos
        if (dto.nomeCompleto() != null && !dto.nomeCompleto().isBlank()) {
            user.setNomeCompleto(dto.nomeCompleto());
        }

        if (dto.email() != null && !dto.email().isBlank()) {
            // Verifica se o novo email já existe
            if (userRepository.existsByEmail(dto.email()) && !user.getEmail().equals(dto.email())) {
                throw new BusinessException("Email already registered!");
            }
            user.setEmail(dto.email());
        }

        if (dto.senha() != null && !dto.senha().isBlank()) {
            user.setSenha(PasswordUtils.hashPassword(dto.senha()));
        }

        // Salva alterações
        userRepository.save(user);

        // Retorna DTO
        return new UserResponseDTO(
            user.getId(),
            user.getNomeCompleto(),
            user.getCpf(),
            user.getEmail(),
            user.getTipoUsuario(),
            user.getBalance()
    );
    }
}
