package biblioteca_spring.service;

import biblioteca_spring.dto.LivroRequestDTO;
import biblioteca_spring.exception.BusinessException;
import biblioteca_spring.model.Livro;
import biblioteca_spring.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}


