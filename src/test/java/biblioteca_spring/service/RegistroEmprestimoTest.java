package biblioteca_spring.service;

import biblioteca_spring.dto.EmprestimoRequestDTO;
import biblioteca_spring.exception.BusinessException;
import biblioteca_spring.exception.NotFoundException;
import biblioteca_spring.model.Aluno;
import biblioteca_spring.model.Livro;
import biblioteca_spring.model.RegistroEmprestimo;
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
public class RegistroEmprestimoTest {

    @Mock
    private ValidadorEmprestimo validador;
    @Mock
    private CalculadoraMulta calculadora;
    @Mock
    private PagamentoService pagamento;
    @Mock
    private RegistrosRepository registrosRepository;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private LivroService livroService;

    @InjectMocks
    EmprestimoService emprestimoService;

    @Test
    void emprestimoComSucesso(){
        Long idUsuario = 56L;
        Long idLivro = 56L;
        EmprestimoRequestDTO dto = new EmprestimoRequestDTO(idUsuario, idLivro);
        Usuario usuario = new Aluno("Caio", "caio@email.com");
        Livro livro = new Livro("Harry Potter", "Chico");
        RegistroEmprestimo registroEmprestimo = new RegistroEmprestimo(usuario, livro);

        when(usuarioService.buscarPorId(idUsuario)).thenReturn(usuario);
        when(livroService.buscarPorId(idLivro)).thenReturn(livro);
        when(registrosRepository.save(any(RegistroEmprestimo.class))).thenReturn(registroEmprestimo);

       RegistroEmprestimo resultado = emprestimoService.emprestarLivro(dto);

       verify(validador).podeEmprestar(usuario, livro);
       verify(pagamento).aplicarTaxaEmprestimo(usuario);


        assertNotNull(resultado);
        assertEquals(usuario.getNome(), resultado.getUsuario().getNome());
    }

    @Test
    void erroAoBuscarUsuario(){
        Long idUsuario = 56L;
        EmprestimoRequestDTO dto = new EmprestimoRequestDTO(idUsuario, 6L);

        when(usuarioService.buscarPorId(idUsuario)).thenThrow(new NotFoundException("Usuário não encontrado"));

        assertThrows(NotFoundException.class, () -> emprestimoService.emprestarLivro(dto));
    }

    @Test
    void erroAoBuscarLivro(){
        Long idLivro = 56L;
        EmprestimoRequestDTO dto = new EmprestimoRequestDTO(56L, idLivro);

        when(livroService.buscarPorId(idLivro)).thenThrow(new NotFoundException("Livro não encontrado"));

        assertThrows(NotFoundException.class, () -> emprestimoService.emprestarLivro(dto));
    }

    @Test
    void erroValidacao(){
        Long idUsuario = 56L;
        Long idLivro = 56L;
        EmprestimoRequestDTO dto = new EmprestimoRequestDTO(idUsuario, idLivro);
        Usuario usuario = new Aluno("Caio", "caio@email.com");
        Livro livro = new Livro("Harry Potter", "Chico");

        when(usuarioService.buscarPorId(idUsuario)).thenReturn(usuario);
        when(livroService.buscarPorId(idLivro)).thenReturn(livro);
        doThrow(new BusinessException("Erro de validação")).when(validador).podeEmprestar(usuario, livro);

        assertThrows(BusinessException.class, () -> emprestimoService.emprestarLivro(dto));
    }

    @Test
    void devolucaoSemMulta(){
        Long idTransacao = 56L;
        Usuario usuario = new Aluno("Caio", "caio@email.com");
        Livro livro = new Livro("Harry Potter", "Chico");
        RegistroEmprestimo registroEmprestimo = new RegistroEmprestimo(usuario, livro);

        when(registrosRepository.findById(idTransacao)).thenReturn(Optional.of(registroEmprestimo));
        when(calculadora.valorCalculado(anyLong())).thenReturn(0.0);

        RegistroEmprestimo resultado = emprestimoService.devolverLivro(idTransacao);
        verifyNoInteractions(pagamento);

        assertNotNull(resultado);
        assertEquals("Caio", resultado.getUsuario().getNome());
    }

    @Test
    void devolucaoComMulta(){
        Long id =56L;
        Usuario usuario = new Aluno("Caio", "caio@email.com");
        Livro livro = new Livro("Harry Potter", "Chico");
        RegistroEmprestimo registroEmprestimo = new RegistroEmprestimo(usuario, livro);

        when(registrosRepository.findById(id)).thenReturn(Optional.of(registroEmprestimo));
        when(calculadora.valorCalculado(anyLong())).thenReturn(5.0);

        RegistroEmprestimo resultado = emprestimoService.devolverLivro(id);
        verify(pagamento).aplicarMultaAtraso(usuario, 5.0);

        assertNotNull(resultado);
        assertEquals("Caio", resultado.getUsuario().getNome());
    }

    @Test
    void erroAoBuscarId(){
        Long id = 56L;

        when(registrosRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> emprestimoService.devolverLivro(id));
    }


}
