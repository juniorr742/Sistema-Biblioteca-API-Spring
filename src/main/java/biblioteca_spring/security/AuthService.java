package biblioteca_spring.security;


import biblioteca_spring.dto.UsuarioRequestDTO.UsuarioLoginDTO;
import biblioteca_spring.exception.BusinessException;
import biblioteca_spring.model.Usuario;
import biblioteca_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    UsuarioRepository usuarioRepository;
    BCryptPasswordEncoder passwordEncoder;
    JwtService jwtService;

    @Autowired
    public AuthService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String autenticar(UsuarioLoginDTO usuarioLoginDTO){
        Optional<Usuario> usuario = usuarioRepository.findByEmailIgnoreCase(usuarioLoginDTO.getEmail());
        return usuario.filter(value -> passwordEncoder.matches(usuarioLoginDTO.getSenha(), value.getSenha()))
                .map(value -> jwtService.gerarToken(value))
                .orElseThrow(() -> new BusinessException("[AVISO] - Email ou senha inválidos"));
    }
}
