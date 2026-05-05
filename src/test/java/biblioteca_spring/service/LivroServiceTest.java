package biblioteca_spring.service;

import biblioteca_spring.dto.LivroRequestDTO;
import biblioteca_spring.exception.BusinessException;
import biblioteca_spring.exception.NotFoundException;
import biblioteca_spring.model.Livro;
import biblioteca_spring.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService livroService;

    @Test
    void deveLancarExcecaoQuandoTituloDuplicado() {

        LivroRequestDTO dto = new LivroRequestDTO("Clean Code", "Robert Martin");
        Livro livroSalvo = new Livro("Clean Code", "Robert Martin");

        when(livroRepository.existsByTituloIgnoreCase("Clean Code")).thenReturn(true);

        assertThrows(BusinessException.class, () -> livroService.salvar(dto));

    }

    @Test
    void deveSalvarLivroComSucesso() {

        LivroRequestDTO dto = new LivroRequestDTO("Clean Code", "Robert Martin");
        Livro livroSalvo = new Livro("Clean Code", "Robert Martin");

        when(livroRepository.existsByTituloIgnoreCase("Clean Code")).thenReturn(false);
        when(livroRepository.save(any(Livro.class))).thenReturn(livroSalvo);

        Livro resultado = livroService.salvar(dto);

        assertNotNull(resultado);
        assertEquals("Clean Code", resultado.getTitulo());
    }

    @Test
    void deveBuscarIdComSucesso(){
        long id = 5;
        Livro livro = new Livro("Clean Code", "Robert Martin");

        when(livroRepository.findById(id)).thenReturn(Optional.of(livro));


        Livro resultado = livroService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(livro, resultado);
    }

    @Test
    void deveDarErroAoBuscarPorId(){
        long id = 5;

        when(livroRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> livroService.buscarPorId(id));
    }

    @Test
    void deveAtualizar(){
        long id = 56;
        LivroRequestDTO livroAtualizado = new LivroRequestDTO("Harry Potter", "Chico");
        Livro livro = new Livro("Harry Potter", "Bento");

        when(livroRepository.findById(56L)).thenReturn(Optional.of(livro));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        Livro resultado = livroService.atualizar(56L, livroAtualizado);

        assertNotNull(livro);
        assertEquals("Chico", resultado.getAutor());
    }
    @Test
    void deveDarErroAoAtualizar(){
        long id = 56;
        LivroRequestDTO livroRequestDTO = new LivroRequestDTO("Harry Potter", "Chico");

        when(livroRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> livroService.atualizar(id, livroRequestDTO));
    }
    @Test
    void deveDeletar(){
        long id = 56L;

        when(livroRepository.existsById(id)).thenReturn(true);
        livroService.deletar(id);

        Mockito.verify(livroRepository).deleteById(id);
    }
    @Test
    void deveDarErroAoDeletar(){
        long id = 56;

        when(livroRepository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> livroService.deletar(id));
    }
}


