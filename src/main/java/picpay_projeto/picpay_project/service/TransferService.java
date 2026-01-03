package picpay_projeto.picpay_project.service;

import java.math.BigDecimal;
import java.util.List;

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
        User payer = userRepository.findById(transferDTO.payer())
                .orElseThrow(() -> new BusinessException("Payer not found"));

        User payee = userRepository.findById(transferDTO.payee())
                .orElseThrow(() -> new BusinessException("Payee not found"));

        // 2. Validações
        validateTransfer(payer, payee, transferDTO.amount());

        // 3. Chama serviço autorizador externo
        boolean authorized = authorizationService.authorize();
        if (!authorized) {
            throw new UnauthorizedTransferException("Transfer not authorized by external service");
        }

        // 4. Atualiza saldos
        updateBalance(payer, payee, transferDTO.amount());

        // 5. Cria registro da transferência
        Transfer transfer = Transfer.builder()
                .amount(transferDTO.amount())
                .payer(payer)
                .payee(payee)
                .statusTransfer(StatusTransfer.PENDING)
                .build();
        transferRepository.save(transfer);

        // 6. Notifica payee
        notificationService.notifyPayee(payee, transferDTO.amount());

        // 7. Retorna DTO
        return new TransferResponseDTO(
                transfer.getAmount(),
                transfer.getPayer().getNomeCompleto(),
                transfer.getPayee().getNomeCompleto(),
                transfer.getStatusTransfer()
        );
    }

    // Método privado para validações
    private void validateTransfer(User payer, User payee, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new UnauthorizedTransferException("The transfer amount must be greater than zero!");
        }
        if (payer.getTipoUsuario() != TipoUsuario.COMUM) {
            throw new UnauthorizedTransferException("Only COMUM users can make transfers");
        }
        if (payer.getId().equals(payee.getId())) {
            throw new UnauthorizedTransferException("Cannot transfer to yourself");
        }
        if (amount.compareTo(payer.getBalance()) > 0) {
            throw new InsufficientBalanceException("The transfer amount is greater than your balance");
        }
    }

    // Método privado para atualizar saldo
    private void updateBalance(User payer, User payee, BigDecimal amount) {
        payer.setBalance(payer.getBalance().subtract(amount));
        payee.setBalance(payee.getBalance().add(amount));
        userRepository.save(payer);
        userRepository.save(payee);
    }


    public List<TransferResponseDTO> listAllTransfer () {
        return transferRepository.findAll().stream()
                .map(TransferResponseDTO::new)
                .toList();
    }
}
