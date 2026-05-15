package biblioteca_spring.controller;

import biblioteca_spring.dto.UsuarioRequestDTO.UsuarioAtualizarDTO;
import biblioteca_spring.dto.UsuarioRequestDTO.UsuarioCadastroDTO;
import biblioteca_spring.dto.UsuarioRequestDTO.UsuarioResponseDTO;
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
    public List<UsuarioResponseDTO> listarTodos(){
        return usuarioService.listarTodos();
    }

    @PostMapping
    public Usuario salvar (@RequestBody UsuarioCadastroDTO usuarioDTO){
        return usuarioService.salvar(usuarioDTO);
    }

    @GetMapping("{id}")
    public UsuarioResponseDTO buscarPorId(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarPorId(id);
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.obterTipo(), usuario.getLimiteLivros(), usuario.getLimiteSaldo(), usuario.getSaldo().getSaldoDevedor());
    }

    @PutMapping("{id}")
    public UsuarioResponseDTO atualizar(@PathVariable Long id, @RequestBody UsuarioAtualizarDTO usuarioDTO){
        Usuario usuario = usuarioService.atualizar(id, usuarioDTO);
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.obterTipo(), usuario.getLimiteLivros(), usuario.getLimiteSaldo(), usuario.getSaldo().getSaldoDevedor());
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable Long id){
        usuarioService.deletar(id);
    }

    @GetMapping("buscarNome/{nome}")
    public List<UsuarioResponseDTO> buscarUsuarioPorNome(@PathVariable String nome){
        return usuarioService.buscarUsuarioPorNome(nome);
    }
}

