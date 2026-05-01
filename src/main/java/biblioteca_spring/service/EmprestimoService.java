package biblioteca_spring.service;

import biblioteca_spring.dto.EmprestimoRequestDTO;
import biblioteca_spring.exception.NotFoundException;
import biblioteca_spring.model.*;
import biblioteca_spring.repository.RegistrosRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EmprestimoService {

    private final ValidadorEmprestimo validador;
    private final CalculadoraMulta calculadora;
    private final PagamentoService pagamento;
    private final RegistrosRepository registrosRepository;
    private final UsuarioService usuarioService;
    private final LivroService livroService;

    @Autowired
    public EmprestimoService(ValidadorEmprestimo validador, CalculadoraMulta calculadora, PagamentoService pagamento, RegistrosRepository registrosRepository,
    UsuarioService usuarioService, LivroService livroService) {
        this.validador = validador;
        this.calculadora = calculadora;
        this.pagamento = pagamento;
        this.registrosRepository = registrosRepository;
        this.usuarioService = usuarioService;
        this.livroService = livroService;
    }

    @Transactional
    public RegistroEmprestimo emprestarLivro(EmprestimoRequestDTO emprestimoDTO) {
        Usuario usuario = usuarioService.buscarPorId(emprestimoDTO.getIdUsuario());
        Livro livro = livroService.buscarPorId(emprestimoDTO.getIdLivro());

        validador.podeEmprestar(usuario, livro);

        RegistroEmprestimo registroEmprestimo = new RegistroEmprestimo(usuario, livro);
        pagamento.aplicarTaxaEmprestimo(usuario);
        livro.setDisponivel(false);
        registrosRepository.save(registroEmprestimo);
        return registroEmprestimo;
    }

    @Transactional
    public RegistroEmprestimo devolverLivro(Long idTransacao) {

        RegistroEmprestimo registro = buscarPorId(idTransacao);

        long diasCorridos = ChronoUnit.DAYS.between(registro.getDataEmprestimo(), LocalDate.now());
        double valorMulta = calculadora.valorCalculado(diasCorridos);

        if (valorMulta > 0) {
            pagamento.aplicarMultaAtraso(registro.getUsuario(), valorMulta);
        }

        registro.getLivro().setDisponivel(true);
        registro.finalizarEmprestimo();
        return registro;
    }

    public List<RegistroEmprestimo> listarHistorico(){
        return registrosRepository.findAll();
    }

    public List<Livro> listarLivrosAtivosPorUsuario(long idUsuario) {
        return registrosRepository.findLivrosAtivosPorUsuario(idUsuario);
    }

    public RegistroEmprestimo buscarPorId(Long idTransacao){
        return registrosRepository.findById(idTransacao).orElseThrow(() -> new NotFoundException("[AVISO] - Registro de empréstimo não encontrado"));
    }
}