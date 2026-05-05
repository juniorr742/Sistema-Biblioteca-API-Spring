package biblioteca_spring.service;

import biblioteca_spring.dto.LivroRequestDTO;
import biblioteca_spring.dto.UsuarioRequestDTO;
import biblioteca_spring.exception.BusinessException;
import biblioteca_spring.exception.NotFoundException;
import biblioteca_spring.model.Aluno;
import biblioteca_spring.model.Livro;
import biblioteca_spring.model.Professor;
import biblioteca_spring.model.Usuario;
import biblioteca_spring.repository.LivroRepository;
import biblioteca_spring.repository.UsuarioRepository;
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
public class UsuarioServiceTeste {

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    UsuarioService usuarioService;

    @Test
    void deveSalvarAlunoComSucesso(){
        UsuarioRequestDTO dto = new UsuarioRequestDTO("Francisco", "aluno");
        Usuario usuario = new Aluno(dto.getNome());

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.salvar(dto);

        assertNotNull(resultado);
        assertEquals("Francisco", resultado.getNome());
    }

    @Test
    void deveSalvarProfessorComSucesso(){
        UsuarioRequestDTO dto = new UsuarioRequestDTO("Caio", "Professor");
        Usuario usuario = new Professor(dto.getNome());

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.salvar(dto);

        assertNotNull(resultado);
        assertEquals("Caio", resultado.getNome());
    }

    @Test
    void deveDarDefault(){
        UsuarioRequestDTO dto = new UsuarioRequestDTO("Lucas", "Inválido");

        assertThrows(BusinessException.class, () -> usuarioService.salvar(dto));
    }

    @Test
    void buscarPorIdComSucesso(){
        long id = 56L;
        Usuario usuario = new Aluno("Caio");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals("Caio", resultado.getNome());
    }

    @Test
    void erroAoBuscarId(){
        long id = 56L;

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> usuarioService.buscarPorId(id));
    }

    @Test
    void deveAtualizar(){
        long id = 56L;
        UsuarioRequestDTO dto = new UsuarioRequestDTO("Junior", "Aluno");
        Usuario usuario = new Aluno(dto.getNome());

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.atualizar(id, dto);

        assertNotNull(resultado);
        assertEquals("Junior", resultado.getNome());
    }

    @Test
    void deveDarErroAoAtualizar(){
        long id = 56L;
        UsuarioRequestDTO dto = new UsuarioRequestDTO("Junior", "Aluno");
        Usuario usuario = new Aluno(dto.getNome());

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> usuarioService.atualizar(id, dto));
    }

    @Test
    void deveDeletar(){
        long id = 56L;

        UsuarioRequestDTO dto = new UsuarioRequestDTO("Junior", "Aluno");
        Usuario usuario = new Aluno(dto.getNome());

        when(usuarioRepository.existsById(id)).thenReturn(true);

        usuarioService.deletar(id);

        Mockito.verify(usuarioRepository).deleteById(id);
    }

    @Test
    void deveDarErroAoDeletar(){
        long id =56L;

        when(usuarioRepository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> usuarioService.deletar(id));
    }
}
