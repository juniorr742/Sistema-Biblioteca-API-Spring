package biblioteca_spring.repository;

import biblioteca_spring.model.Livro;
import biblioteca_spring.model.Emprestimo;
import biblioteca_spring.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistrosRepository extends JpaRepository<Emprestimo, Long> {

    @Query("SELECT l FROM RegistroEmprestimo r JOIN r.livro l WHERE r.usuario.id = :idUsuario AND r.finalizado = false")
    List<Livro> findLivrosAtivosPorUsuario(@Param("idUsuario") long idUsuario);

    boolean existsByUsuarioAndLivroAndFinalizadoFalse(Usuario usuario, Livro livro);

    Long countByUsuarioAndFinalizadoFalse(Usuario usuario);
}
