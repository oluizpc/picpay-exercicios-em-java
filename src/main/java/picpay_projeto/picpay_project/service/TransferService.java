package picpay_projeto.picpay_project.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import picpay_projeto.picpay_project.dto.Tranfer.TransferRequestDTO;
import picpay_projeto.picpay_project.dto.Tranfer.TransferResponseDTO;
import picpay_projeto.picpay_project.enums.StatusTransfer;
import picpay_projeto.picpay_project.enums.TipoUsuario;
import picpay_projeto.picpay_project.exception.BusinessException;
import picpay_projeto.picpay_project.exception.InsufficientBalanceException;
import picpay_projeto.picpay_project.exception.UnauthorizedTransferException;
import picpay_projeto.picpay_project.model.Transfer;
import picpay_projeto.picpay_project.model.User;
import picpay_projeto.picpay_project.repository.TransferRepository;
import picpay_projeto.picpay_project.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final AuthorizationService authorizationService;

    @Transactional
    public TransferResponseDTO createTransfer(TransferRequestDTO transferDTO) {

        // 1️. Busca usuários
        User payer = userRepository.findById(transferDTO.payerId())
                .orElseThrow(() -> new BusinessException("Payer not found"));

        User payee = userRepository.findById(transferDTO.payeeId())
                .orElseThrow(() -> new BusinessException("Payee not found"));

        // 2. Validações
        validateTransfer(payer, payee, transferDTO.value());

        // 3. Chama serviço autorizador externo
        boolean authorized = authorizationService.authorize();
        if (!authorized) {
            throw new UnauthorizedTransferException("Transfer not authorized by external service");
        }

        // 4. Atualiza saldos
        updateBalance(payer, payee, transferDTO.value());

        // 5. Cria registro da transferência
        Transfer transfer = Transfer.builder()
                .value(transferDTO.value())
                .payer(payer)
                .payee(payee)
                .statusTransfer(StatusTransfer.PENDING)
                .build();
        transferRepository.save(transfer);

        // 6. Notifica payee
        notificationService.notifyPayee(payee, transferDTO.value());

        // 7. Retorna DTO
        return new TransferResponseDTO(
                transfer.getValue(),
                transfer.getPayer().getNomeCompleto(),
                transfer.getPayee().getNomeCompleto(),
                transfer.getStatusTransfer()
        );
    }

    // Método privado para validações
    private void validateTransfer(User payer, User payee, BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new UnauthorizedTransferException("The transfer amount must be greater than zero!");
        }
        if (payer.getTipoUsuario() != TipoUsuario.COMUM) {
            throw new UnauthorizedTransferException("Only COMUM users can make transfers");
        }
        if (payer.getId().equals(payee.getId())) {
            throw new UnauthorizedTransferException("Cannot transfer to yourself");
        }
        if (value.compareTo(payer.getBalance()) > 0) {
            throw new InsufficientBalanceException("The transfer value is greater than your balance");
        }
    }

    // Método privado para atualizar saldo
    private void updateBalance(User payer, User payee, BigDecimal value) {
        payer.setBalance(payer.getBalance().subtract(value));
        payee.setBalance(payee.getBalance().add(value));
        userRepository.save(payer);
        userRepository.save(payee);
    }
}
