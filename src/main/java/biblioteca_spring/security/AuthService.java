package biblioteca_spring.security;


import biblioteca_spring.dto.UsuarioRequestDTO.UsuarioLoginDTO;
import biblioteca_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    UsuarioRepository usuarioRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean autenticar(UsuarioLoginDTO usuarioLoginDTO){
        usuarioRepository.findByEmailIgnoreCase(usuarioLoginDTO.getEmail());
        passwordEncoder.matches(usuarioLoginDTO.getSenha(), )
    }
}
