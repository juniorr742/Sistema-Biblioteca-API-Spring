package biblioteca_spring.service;

import biblioteca_spring.model.Usuario;
import biblioteca_spring.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario salvar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("[AVISO] - Usuário não encontrado"));
    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Usuario atualizar(Usuario usuario){
        if (!usuarioRepository.existsById(usuario.getId())){
            throw new RuntimeException("[AVISO] - Usuário não existente");
        }
        return usuarioRepository.save(usuario);
    }

    public void delete(long id){
        if (!usuarioRepository.existsById(id)){
            throw new RuntimeException("[AVISO] - Usuário não existente");
        }
        usuarioRepository.deleteById(id);
    }
}
