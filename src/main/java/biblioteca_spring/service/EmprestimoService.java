package biblioteca_spring.service;

import biblioteca_spring.dto.EmprestimoRequestDTO.EmprestimoRequestDTO;
import biblioteca_spring.dto.EmprestimoRequestDTO.EmprestimoResponseDTO;
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
    public Emprestimo emprestarLivro(EmprestimoRequestDTO emprestimoDTO) {
        Usuario usuario = usuarioService.buscarPorId(emprestimoDTO.getIdUsuario());
        Livro livro = livroService.buscarPorId(emprestimoDTO.getIdLivro());

        validador.podeEmprestar(usuario, livro);

        Emprestimo emprestimo = new Emprestimo(usuario, livro);
        pagamento.aplicarTaxaEmprestimo(usuario);
        livro.setDisponivel(false);
        registrosRepository.save(emprestimo);
        return emprestimo;
    }

    @Transactional
    public Emprestimo devolverLivro(Long idTransacao) {

        Emprestimo registro = buscarPorId(idTransacao);

        long diasCorridos = ChronoUnit.DAYS.between(registro.getDataEmprestimo(), LocalDate.now());
        double valorMulta = calculadora.valorCalculado(diasCorridos);

        if (valorMulta > 0) {
            pagamento.aplicarMultaAtraso(registro.getUsuario(), valorMulta);
        }

        registro.getLivro().setDisponivel(true);
        registro.finalizarEmprestimo();
        return registro;
    }

    public List<EmprestimoResponseDTO> listarHistorico(){
        return registrosRepository.findAll().stream().map(e -> new EmprestimoResponseDTO(
                e.getUsuario().getNome(),
                e.getLivro().getTitulo(),
                e.getIdTransacao(),
                e.isFinalizado()
        )).toList();
    }

    public List<Livro> listarLivrosAtivosPorUsuario(long idUsuario) {
        return registrosRepository.findLivrosAtivosPorUsuario(idUsuario);
    }

    public Emprestimo buscarPorId(Long idTransacao){
        return registrosRepository.findById(idTransacao).orElseThrow(() -> new NotFoundException("[AVISO] - Registro de empréstimo não encontrado"));
    }
}