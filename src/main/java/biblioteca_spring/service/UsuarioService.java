package biblioteca_spring.service;

import biblioteca_spring.dto.UsuarioRequestDTO.UsuarioAtualizarDTO;
import biblioteca_spring.dto.UsuarioRequestDTO.UsuarioCadastroDTO;
import biblioteca_spring.dto.UsuarioRequestDTO.UsuarioResponseDTO;
import biblioteca_spring.exception.BusinessException;
import biblioteca_spring.exception.NotFoundException;
import biblioteca_spring.model.Aluno;
import biblioteca_spring.model.Professor;
import biblioteca_spring.model.Usuario;
import biblioteca_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario salvar(UsuarioCadastroDTO usuarioDTO){

        if (usuarioRepository.existsByEmailIgnoreCase(usuarioDTO.getEmail())){
            throw new BusinessException("[AVISO] - Email ja existente");
        }

        switch (usuarioDTO.getTipo().trim().toLowerCase()){
            case "aluno":
                String senhaCriptografada = passwordEncoder.encode(usuarioDTO.getSenha());
                Usuario usuario = new Aluno(usuarioDTO.getNome(), usuarioDTO.getEmail(),senhaCriptografada);
                return usuarioRepository.save(usuario);

            case "professor":
                String senhaCriptogradada1 = passwordEncoder.encode(usuarioDTO.getSenha());
                Usuario usuario1 = new Professor(usuarioDTO.getNome(), usuarioDTO.getEmail(), senhaCriptogradada1);
                return usuarioRepository.save(usuario1);

            default:
                throw new BusinessException("[AVISO] - Identificação de usuário inexistente no sistema");
        }
    }

    public Usuario buscarPorId(long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("[AVISO] - Usuário não encontrado"));
    }

    public List<UsuarioResponseDTO> listarTodos(){
        return usuarioRepository.findAll()
                .stream().map(u -> new UsuarioResponseDTO(
                        u.getId(),
                        u.getNome(),
                        u.getEmail(),
                        u.obterTipo(),
                        u.getLimiteLivros(),
                        u.getLimiteSaldo()
                )).toList();
    }

    public Usuario atualizar(Long id, UsuarioAtualizarDTO usuarioDTO){
       Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("[AVISO] - Usuário não encontrado"));

       if (usuarioRepository.existsByEmailIgnoreCaseAndIdNot(usuarioDTO.getEmail(), id)){
           throw new BusinessException("[AVISO] - Email já cadastrado em outra conta");
       }

       usuario.setNome(usuarioDTO.getNome());
       usuario.setEmail(usuarioDTO.getEmail());
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
