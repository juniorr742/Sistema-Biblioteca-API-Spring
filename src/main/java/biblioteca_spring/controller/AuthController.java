package biblioteca_spring.controller;


import biblioteca_spring.dto.UsuarioRequestDTO.UsuarioLoginDTO;
import biblioteca_spring.security.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping
    public String login(@RequestBody UsuarioLoginDTO usuarioLoginDTO){
        return authService.autenticar(usuarioLoginDTO);
    }

}
