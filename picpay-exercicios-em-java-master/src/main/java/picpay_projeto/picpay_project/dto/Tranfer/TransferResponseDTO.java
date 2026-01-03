package picpay_projeto.picpay_project.dto.Tranfer;

import java.math.BigDecimal;
import picpay_projeto.picpay_project.enums.StatusTransfer;

public record TransferResponseDTO(
    BigDecimal value,
    String payerName,
    String payeeName,
    StatusTransfer statusTransfer
) {}
