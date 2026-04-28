package biblioteca_spring.controller;

import biblioteca_spring.config.BibliotecaConfig;
import biblioteca_spring.model.Usuario;
import biblioteca_spring.service.PagamentoService;
import biblioteca_spring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios/{id}/pagamento")
public class PagamentoController {
    BibliotecaConfig bibliotecaConfig;
    PagamentoService pagamentoService;
    UsuarioService usuarioService;

    @Autowired
    public PagamentoController(BibliotecaConfig bibliotecaConfig, PagamentoService pagamentoService, UsuarioService usuarioService){
        this.bibliotecaConfig = bibliotecaConfig;
        this.pagamentoService = pagamentoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public Usuario verificarSaldo(@PathVariable Long id) {
        return pagamentoService.verificarSaldo(id);
    }

    @GetMapping("/taxa")
    public double verificarCustoFixo(@PathVariable Long id){
        return BibliotecaConfig.CUSTO_FIXO_EMPRESTIMO;
    }

    @PostMapping
    public Usuario processarPagamento(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarPorId(id);
        return pagamentoService.processarPagamentoTotal(usuario);
    }


}
