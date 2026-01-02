package picpay_projeto.picpay_project.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import picpay_projeto.picpay_project.model.User;

@Service
public class NotificationService {

    public void notifyPayee(User payee, BigDecimal value) {
        try {
            // Simula envio de POST para serviço externo
            System.out.println("Notificando " + payee.getNomeCompleto() + " do valor: " + value);
        } catch (Exception e) {
            // Loga erro, mas não interrompe a transferência
            System.err.println("Falha ao notificar payee: " + e.getMessage());
        }
    }
}
