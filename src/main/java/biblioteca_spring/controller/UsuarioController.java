package biblioteca_spring.controller;

import biblioteca_spring.dto.UsuarioRequestDTO;
import biblioteca_spring.model.Usuario;
import biblioteca_spring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listarTodos(){
        return usuarioService.listarTodos();
    }

    @PostMapping
    public Usuario salvar (@RequestBody UsuarioRequestDTO usuarioDTO){
        return usuarioService.salvar(usuarioDTO);
    }

    @GetMapping("{id}")
    public Usuario buscarPorId(@PathVariable Long id){
        return usuarioService.buscarPorId(id);
    }

    @PutMapping("{id}")
    public Usuario atualizar(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuarioDTO){
        return usuarioService.atualizar(id, usuarioDTO);
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable Long id){
        usuarioService.deletar(id);
    }

    @GetMapping("buscarNome/{nome}")
    public List<Usuario> buscarUsuarioPorNome(@PathVariable String nome){
        return usuarioService.buscarUsuarioPorNome(nome);
    }
}

