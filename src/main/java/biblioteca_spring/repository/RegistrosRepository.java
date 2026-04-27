package biblioteca_spring.repository;

import biblioteca_spring.model.RegistroEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrosRepository extends JpaRepository<RegistroEmprestimo, Long> {
    List<RegistroEmprestimo> findByUsuarioAndFinalizadoFalse(long idUsuario);
}
