package biblioteca_spring.service;

import biblioteca_spring.config.BibliotecaConfig;
import biblioteca_spring.model.Usuario;
import biblioteca_spring.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {
    UsuarioRepository usuarioRepository;

    public PagamentoService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }


    public Usuario processarPagamentoTotal(Usuario usuario) {
        double valorSaldoDevedor = usuario.getSaldo().getSaldoDevedor();
        if (valorSaldoDevedor > 0) {
            usuario.getSaldo().quitarTotalmente();
            usuarioRepository.save(usuario);
            return usuario;
        }
        throw new RuntimeException("[AVISO] - O usuário não tem pendências");
    }

    public Usuario aplicarMultaAtraso(Usuario usuario, double valorMulta) {
        if (valorMulta > 0) {
            usuario.getSaldo().aumentarDebito(valorMulta);
            usuarioRepository.save(usuario);
            return usuario;
        }
        throw new RuntimeException("[AVISO] - O valor da multa não pode ser negativo");
    }

    public Usuario aplicarTaxaEmprestimo(Usuario usuario) {
        double taxa = BibliotecaConfig.CUSTO_FIXO_EMPRESTIMO;
        usuario.getSaldo().aumentarDebito(taxa);
        usuarioRepository.save(usuario);
        return usuario;
    }
}
