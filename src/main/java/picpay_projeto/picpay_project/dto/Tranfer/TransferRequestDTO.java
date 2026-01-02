package picpay_projeto.picpay_project.dto.Tranfer;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import picpay_projeto.picpay_project.enums.TipoUsuario;

public record TransferRequestDTO(
    @DecimalMin(value = "0.01", message = "The transfer value must be greater than zero.")
    BigDecimal value,

    @NotNull(message = "Payer ID cannot be null.")
    Long payerId,
    @NotNull(message = "Payee ID cannot be null.")
    Long payeeId,

    TipoUsuario tipoUsuario,

    BigDecimal balence
) {}
