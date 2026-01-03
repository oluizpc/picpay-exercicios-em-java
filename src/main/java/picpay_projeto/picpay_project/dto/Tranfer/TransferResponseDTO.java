package picpay_projeto.picpay_project.dto.Tranfer;

import java.math.BigDecimal;

import picpay_projeto.picpay_project.enums.StatusTransfer;
import picpay_projeto.picpay_project.model.Transfer;

public record TransferResponseDTO(
        BigDecimal amount,
        String payerName,
        String payeeName,
        StatusTransfer statusTransfer
) {

    public TransferResponseDTO(Transfer transfer) {
        this(
            transfer.getAmount(),
            transfer.getPayer().getNomeCompleto(),
            transfer.getPayee().getNomeCompleto(),
            transfer.getStatusTransfer()
        );
    }
}
