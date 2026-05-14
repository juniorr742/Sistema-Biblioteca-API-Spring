package biblioteca_spring.controller;

import biblioteca_spring.config.BibliotecaConfig;
import biblioteca_spring.dto.UsuarioRequestDTO.UsuarioResponseDTO;
import biblioteca_spring.model.Usuario;
import biblioteca_spring.service.PagamentoService;
import biblioteca_spring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios/pagamento")
public class PagamentoController {
    private final PagamentoService pagamentoService;
    private final UsuarioService usuarioService;

    @Autowired
    public PagamentoController(PagamentoService pagamentoService, UsuarioService usuarioService){
        this.pagamentoService = pagamentoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO verificarSaldo(@PathVariable Long id) {
        Usuario usuario = pagamentoService.verificarSaldo(id);
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.obterTipo(), usuario.getLimiteLivros(), usuario.getLimiteSaldo());
    }

    @GetMapping
    public double verificarCustoFixo(){
        return BibliotecaConfig.CUSTO_FIXO_EMPRESTIMO;
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO processarPagamentoTotal(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarPorId(id);
        pagamentoService.processarPagamentoTotal(usuario);
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.obterTipo(), usuario.getLimiteLivros(), usuario.getLimiteSaldo());
    }

    @PutMapping("/{id}/{valorPago}")
    public UsuarioResponseDTO processarPagamentoParcial(@PathVariable Long id, @PathVariable double valorPago){
        Usuario usuario = usuarioService.buscarPorId(id);
        pagamentoService.processarPagamentoParcial(usuario, valorPago);
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.obterTipo(), usuario.getLimiteLivros(), usuario.getLimiteSaldo());
    }

}
