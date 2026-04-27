package biblioteca_spring.service;

import biblioteca_spring.model.Livro;
import biblioteca_spring.model.RegistroEmprestimo;
import biblioteca_spring.model.Usuario;
import biblioteca_spring.repository.LivroRepository;
import biblioteca_spring.repository.RegistrosRepository;
import biblioteca_spring.service.PagamentoService;
import biblioteca_spring.service.CalculadoraMulta;
import biblioteca_spring.service.ValidadorEmprestimo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmprestimoService {

    private ValidadorEmprestimo validador;
    private CalculadoraMulta calculadora;
    private PagamentoService pagamento;
    private RegistrosRepository registrosRepository;
    private LivroRepository livroRepository;

    @Autowired
    public EmprestimoService(ValidadorEmprestimo validador, CalculadoraMulta calculadora, PagamentoService pagamento, RegistrosRepository registrosRepository, LivroRepository livroRepository) {
        this.validador = validador;
        this.calculadora = calculadora;
        this.pagamento = pagamento;
        this.registrosRepository = registrosRepository;
        this.livroRepository = livroRepository;
    }

    public RegistroEmprestimo emprestarLivro(Usuario usuario, Livro livro) {
        if (!validador.podeEmprestar(usuario, livro)) {
            throw new RuntimeException("[AVISO] - Erro de validação");
        }
        pagamento.aplicarTaxaEmprestimo(usuario);
        livro.setDisponivel(false);
        RegistroEmprestimo registro = new RegistroEmprestimo(usuario, livro);
        return registrosRepository.save(registro);
    }

    public RegistroEmprestimo realizarDevolucao(RegistroEmprestimo registro) {

        long diasCorridos = ChronoUnit.DAYS.between(registro.getDataEmprestimo(), LocalDate.now());
        double valorMulta = calculadora.valorCalculado(diasCorridos);

        if (valorMulta > 0) {
            pagamento.aplicarMultaAtraso(registro.getUsuario(), valorMulta);
        }

        registro.getLivro().setDisponivel(true);
        registro.finalizarEmprestimo();
        return registrosRepository.save(registro);
    }

    public List<RegistroEmprestimo> listarHistorico(){
        return registrosRepository.findAll();
    }

    public List<Livro> listarLivrosEmprestadosPorUsuario(long idUsuario) {
            List<RegistroEmprestimo> ativos = registrosRepository.findByUsuarioAndFinalizadoFalse(idUsuario);
            List<Livro> livros = new ArrayList<>();
            for (RegistroEmprestimo r : ativos) {
                Livro l = livroRepository.findById(r.getLivro().getId()).orElse(null);
                if (l != null) livros.add(l);
            }
            return livros;
    }
}