package biblioteca_spring.service;

import biblioteca_spring.dto.UsuarioRequestDTO;
import biblioteca_spring.exception.BusinessException;
import biblioteca_spring.exception.NotFoundException;
import biblioteca_spring.model.Aluno;
import biblioteca_spring.model.Professor;
import biblioteca_spring.model.Usuario;
import biblioteca_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario salvar(UsuarioRequestDTO usuarioDTO){

        switch (usuarioDTO.getTipo().trim().toLowerCase()){
            case "aluno": Usuario usuario = new Aluno(usuarioDTO.getNome());
                return usuarioRepository.save(usuario);

            case "professor": Usuario usuario1 = new Professor(usuarioDTO.getNome());
                return usuarioRepository.save(usuario1);

            default:
                throw new BusinessException("[AVISO] - Identificação de usuário inexistente no sistema");
        }
    }

    public Usuario buscarPorId(long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("[AVISO] - Usuário não encontrado"));
    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Usuario atualizar(Long id, UsuarioRequestDTO usuarioDTO){
       Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("[AVISO] - Usuário não encontrado"));

       usuario.setNome(usuarioDTO.getNome());
        return usuarioRepository.save(usuario);
    }

    public void deletar(long id){
        if (!usuarioRepository.existsById(id)){
            throw new NotFoundException("[AVISO] - Usuário não existente");
        }
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> buscarUsuarioPorNome(String nome){
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }
}
