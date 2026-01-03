package picpay_projeto.picpay_project.dto.User;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import picpay_projeto.picpay_project.enums.TipoUsuario;

public record UserRequestDTO(
    @NotBlank(message = "Name cannot be blank")
    String nomeCompleto,

    @Size(min = 11, max = 11, message = "CPF must have 11 digits")
    @NotBlank(message = "CPF cannot be blank")
    String cpf,

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    String email,

    @NotBlank(message = "Password cannot be blank")
    String senha,

    @Column
    BigDecimal balance,

    @NotNull(message = "User type cannot be null")
    TipoUsuario tipoUsuario
) {}
