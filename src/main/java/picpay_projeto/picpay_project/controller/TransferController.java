package picpay_projeto.picpay_project.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import picpay_projeto.picpay_project.dto.Tranfer.TransferRequestDTO;
import picpay_projeto.picpay_project.dto.Tranfer.TransferResponseDTO;
import picpay_projeto.picpay_project.service.TransferService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    // 1. Post(criar) transferência com todas regras no service.
    @PostMapping
    public ResponseEntity <TransferResponseDTO> createTrasnfer (@RequestBody @Valid TransferRequestDTO dto) {
        TransferResponseDTO responseDTO = transferService.createTransfer(dto);
        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping
    public ResponseEntity<List<TransferResponseDTO>> allTransfer () {
        List<TransferResponseDTO> responseDTO = transferService.listAllTransfer();
        return ResponseEntity.ok(responseDTO);
    }
}