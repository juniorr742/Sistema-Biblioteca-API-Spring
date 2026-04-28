package biblioteca_spring.repository;

import biblioteca_spring.model.Livro;
import biblioteca_spring.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

     List<Usuario> findByNomeContainingIgnoreCase(String nome);
}
