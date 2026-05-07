package biblioteca_spring.service;

import biblioteca_spring.dto.UsuarioRequestDTO.UsuarioCadastroDTO;
import biblioteca_spring.exception.BusinessException;
import biblioteca_spring.exception.NotFoundException;
import biblioteca_spring.model.Aluno;
import biblioteca_spring.model.Professor;
import biblioteca_spring.model.Usuario;
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
        UsuarioCadastroDTO dto = new UsuarioCadastroDTO("Francisco", "aluno", "francisco@email.com", "ggg");
        Usuario usuario = new Aluno(dto.getNome(), dto.getEmail());

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.salvar(dto);

        assertNotNull(resultado);
        assertEquals("Francisco", resultado.getNome());
    }

    @Test
    void deveSalvarProfessorComSucesso(){
        UsuarioCadastroDTO dto = new UsuarioCadastroDTO("Caio", "Professor", "caio@email.com", "ggg");
        Usuario usuario = new Professor(dto.getNome(), dto.getEmail());

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.salvar(dto);

        assertNotNull(resultado);
        assertEquals("Caio", resultado.getNome());
    }

    @Test
    void deveDarDefault(){
        UsuarioCadastroDTO dto = new UsuarioCadastroDTO("Lucas", "Inválido", "lucas@email.com", "ggg");

        assertThrows(BusinessException.class, () -> usuarioService.salvar(dto));
    }

    @Test
    void buscarPorIdComSucesso(){
        long id = 56L;
        Usuario usuario = new Aluno("Caio", "caio@email.com");

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
        UsuarioCadastroDTO dto = new UsuarioCadastroDTO("Junior", "Aluno", "junior@email.com", "ggg");
        Usuario usuario = new Aluno(dto.getNome(), dto.getEmail());

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.atualizar(id, dto);

        assertNotNull(resultado);
        assertEquals("Junior", resultado.getNome());
    }

    @Test
    void deveDarErroAoAtualizar(){
        long id = 56L;
        UsuarioCadastroDTO dto = new UsuarioCadastroDTO("Junior", "Aluno", "junior@email.com", "ggg");
        Usuario usuario = new Aluno(dto.getNome(), dto.getEmail());

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> usuarioService.atualizar(id, dto));
    }

    @Test
    void deveDeletar(){
        long id = 56L;

        UsuarioCadastroDTO dto = new UsuarioCadastroDTO("Junior", "Aluno", "junior@email.com", "ggg");
        Usuario usuario = new Aluno(dto.getNome(), dto.getEmail());

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
