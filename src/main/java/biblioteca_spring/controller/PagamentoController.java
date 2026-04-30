package biblioteca_spring.controller;

import biblioteca_spring.config.BibliotecaConfig;
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
    public Usuario verificarSaldo(@PathVariable Long id) {
        return pagamentoService.verificarSaldo(id);
    }

    @GetMapping
    public double verificarCustoFixo(){
        return BibliotecaConfig.CUSTO_FIXO_EMPRESTIMO;
    }

    @PutMapping("/{id}")
    public Usuario processarPagamentoTotal(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarPorId(id);
        return pagamentoService.processarPagamentoTotal(usuario);
    }

    @PutMapping("/{id}/{valorPago}")
    public Usuario processarPagamentoParcial(@PathVariable Long id, @PathVariable double valorPago){
        Usuario usuario = usuarioService.buscarPorId(id);
        return pagamentoService.processarPagamentoParcial(usuario, valorPago);
    }

}
