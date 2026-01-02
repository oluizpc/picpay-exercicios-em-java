package picpay_projeto.picpay_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import picpay_projeto.picpay_project.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
