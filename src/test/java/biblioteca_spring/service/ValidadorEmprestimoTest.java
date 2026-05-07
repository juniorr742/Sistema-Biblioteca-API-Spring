package biblioteca_spring.service;

import biblioteca_spring.exception.BusinessException;
import biblioteca_spring.model.Aluno;
import biblioteca_spring.model.Livro;
import biblioteca_spring.model.Usuario;
import biblioteca_spring.repository.RegistrosRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidadorEmprestimoTest {

    @Mock
    RegistrosRepository registrosRepository;
    @InjectMocks
    ValidadorEmprestimo validadorEmprestimo;

    @Test
    void validadoComSucesso(){
        Usuario usuario = new Aluno("Caio", "caio@email.com", "ggg");
        Livro livro = new Livro("Harry Potter", "Chico");

        assertDoesNotThrow(() -> validadorEmprestimo.podeEmprestar(usuario, livro));
    }

    @Test
    void erroUsuarioComLivro(){
        Usuario usuario = new Aluno("Caio", "caio@email.com", "ggg");
        Livro livro = new Livro("Harry Potter", "Chico");

        when(registrosRepository.existsByUsuarioAndLivroAndFinalizadoFalse(usuario, livro)).thenReturn(true);

        assertThrows(BusinessException.class, () -> validadorEmprestimo.podeEmprestar(usuario, livro));
    }

    @Test
    void erroLivroIndisponivel(){
        Usuario usuario = new Aluno("Caio", "caio@email.com", "ggg");
        Livro livro = new Livro("Harry Potter", "Chico");

        when(registrosRepository.existsByUsuarioAndLivroAndFinalizadoFalse(usuario, livro)).thenReturn(false);

        livro.setDisponivel(false);

        assertThrows(BusinessException.class, () -> validadorEmprestimo.podeEmprestar(usuario, livro));
    }

    @Test
    void erroLimiteSaldo(){
        Usuario usuario = new Aluno("Caio", "caio@email.com", "ggg");
        Livro livro = new Livro("Harry Potter", "Chico");

        when(registrosRepository.existsByUsuarioAndLivroAndFinalizadoFalse(usuario, livro)).thenReturn(false);
        livro.setDisponivel(true);
        usuario.getSaldo().aumentarDebito(20.0);

        assertThrows(BusinessException.class, () -> validadorEmprestimo.podeEmprestar(usuario, livro));
    }

    @Test
    void erroLimiteLivros(){
        Usuario usuario = new Aluno("Caio", "caio@email.com", "ggg");
        Livro livro = new Livro("Harry Potter", "Chico");

        when(registrosRepository.existsByUsuarioAndLivroAndFinalizadoFalse(usuario, livro)).thenReturn(false);
        livro.setDisponivel(true);
        usuario.getSaldo().quitarTotalmente();
        when(registrosRepository.countByUsuarioAndFinalizadoFalse(usuario)).thenReturn(4L);

        assertThrows(BusinessException.class, () -> validadorEmprestimo.podeEmprestar(usuario, livro));

    }
}
