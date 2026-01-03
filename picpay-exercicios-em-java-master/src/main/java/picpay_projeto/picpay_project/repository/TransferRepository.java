package picpay_projeto.picpay_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import picpay_projeto.picpay_project.model.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {


}
