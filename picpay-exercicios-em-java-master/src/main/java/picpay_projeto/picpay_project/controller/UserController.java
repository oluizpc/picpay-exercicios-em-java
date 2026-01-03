package picpay_projeto.picpay_project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import picpay_projeto.picpay_project.dto.UserUpdateDTO;
import picpay_projeto.picpay_project.dto.User.UserRequestDTO;
import picpay_projeto.picpay_project.dto.User.UserResponseDTO;
import picpay_projeto.picpay_project.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // 1. Post para criar o user
    @PostMapping
    public ResponseEntity <UserResponseDTO> createUser (@RequestBody @Valid UserRequestDTO dto) {
        UserResponseDTO responseDTO = userService.createUser(dto);
        return ResponseEntity.ok(responseDTO);
    }


    // 2. Get para puxar todos usuários
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        List<UserResponseDTO> responseDTO = userService.getAllUsers();
            return ResponseEntity.ok(responseDTO);
    }


    // 3. Get para puxar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity <UserResponseDTO> findById (@PathVariable Long id) {
        UserResponseDTO responseDTO = userService.getUserById(id);
        return ResponseEntity.ok(responseDTO);
    }
    
    // 4. Deletar user por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }


    // 5. Patch para alteração de algo no usuário, todas regras e validações no service.
    @PatchMapping("/{id}")
    public ResponseEntity <UserResponseDTO> updateUser (@PathVariable Long id, @RequestBody @Valid UserUpdateDTO dto) {
        UserResponseDTO updateUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(updateUser);
    }

}
