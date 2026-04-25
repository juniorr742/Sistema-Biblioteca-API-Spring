package biblioteca_spring.repository;

import biblioteca_spring.model.RegistroEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrosRepository extends JpaRepository<RegistroEmprestimo, Long> {
}
