package picpay_projeto.picpay_project.dto.Tranfer;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
public record TransferRequestDTO(

    @NotNull
    @DecimalMin(value = "0.01", message = "The transfer amount must be greater than zero.")
    BigDecimal amount,

    @NotNull(message = "Payer ID cannot be null.")
    Long payer,

    @NotNull(message = "Payee ID cannot be null.")
    Long payee
) {}
