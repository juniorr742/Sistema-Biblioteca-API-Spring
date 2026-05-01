package biblioteca_spring.service;

import biblioteca_spring.config.BibliotecaConfig;
import biblioteca_spring.exception.BusinessException;
import biblioteca_spring.exception.NotFoundException;
import biblioteca_spring.model.Usuario;
import biblioteca_spring.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {
    private final UsuarioRepository usuarioRepository;

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
        throw new BusinessException("[AVISO] - O usuário não tem pendências");
    }

    public Usuario processarPagamentoParcial(Usuario usuario, double valorPago){

        if (valorPago > 0 && usuario.getSaldo().getSaldoDevedor() >= valorPago){
            usuario.getSaldo().reduzirValor(valorPago);
            return usuarioRepository.save(usuario);
        }
        throw new BusinessException("[AVISO] - Insira um valor pago válido.");
    }

    public Usuario aplicarMultaAtraso(Usuario usuario, double valorMulta) {
        if (valorMulta > 0) {
            usuario.getSaldo().aumentarDebito(valorMulta);
            return usuario;
        }
        throw new BusinessException("[AVISO] - O valor da multa não pode ser negativo");
    }

    public Usuario aplicarTaxaEmprestimo(Usuario usuario) {
        double taxa = BibliotecaConfig.CUSTO_FIXO_EMPRESTIMO;
        usuario.getSaldo().aumentarDebito(taxa);
        return usuario;
    }

    public Usuario verificarSaldo(long id) {
        return usuarioRepository.findById(id).orElseThrow((() -> new NotFoundException("[AVISO] - Usuário não encontrado")));
    }
}
