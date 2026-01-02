package picpay_projeto.picpay_project.dto.User;

import java.math.BigDecimal;

import picpay_projeto.picpay_project.enums.TipoUsuario;

public record UserResponseDTO(
    Long Id,
    String nomeCompleto,
    String cpf,
    String email,
    TipoUsuario tipoUsuario,
    BigDecimal balance // <- opcional para mostrar ao usuário
) {

}
