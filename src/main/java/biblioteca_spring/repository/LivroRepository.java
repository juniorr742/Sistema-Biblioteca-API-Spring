package biblioteca_spring.repository;

import biblioteca_spring.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTitulo (String titulo);
}
