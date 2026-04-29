package biblioteca_spring.service;

import biblioteca_spring.dto.EmprestimoRequestDTO;
import biblioteca_spring.dto.LivroRequestDTO;
import biblioteca_spring.dto.UsuarioRequestDTO;
import biblioteca_spring.model.*;
import biblioteca_spring.repository.LivroRepository;
import biblioteca_spring.repository.RegistrosRepository;


import biblioteca_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    private UsuarioService usuarioService;
    private LivroService livroService;
    private UsuarioRepository usuarioRepository;

    @Autowired
    public EmprestimoService(ValidadorEmprestimo validador, CalculadoraMulta calculadora, PagamentoService pagamento, RegistrosRepository registrosRepository, LivroRepository livroRepository,
    UsuarioService usuarioService, LivroService livroService, UsuarioRepository usuarioRepository) {
        this.validador = validador;
        this.calculadora = calculadora;
        this.pagamento = pagamento;
        this.registrosRepository = registrosRepository;
        this.livroRepository = livroRepository;
        this.usuarioService = usuarioService;
        this.livroService = livroService;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public RegistroEmprestimo emprestarLivro(EmprestimoRequestDTO emprestimoDTO) {
        Usuario usuario = usuarioService.buscarPorId(emprestimoDTO.getIdUsuario());
        Livro livro = livroService.buscarPorId(emprestimoDTO.getIdLivro());

        if (!validador.podeEmprestar(usuario, livro)){
            throw new RuntimeException("[AVISO] - Erro de validação");
        }

        RegistroEmprestimo registroEmprestimo = new RegistroEmprestimo(usuario, livro);
        pagamento.aplicarTaxaEmprestimo(usuario);
        livro.setDisponivel(false);
        usuarioRepository.save(usuario);
        livroRepository.save(livro);
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
        usuarioRepository.save(registro.getUsuario());
        livroRepository.save(registro.getLivro());
        return registrosRepository.save(registro);
    }

    public List<RegistroEmprestimo> listarHistorico(){
        return registrosRepository.findAll();
    }

    public List<Livro> listarLivrosAtivosPorUsuario(long idUsuario) {
        return registrosRepository.findLivrosAtivosPorUsuario(idUsuario);
    }

    public RegistroEmprestimo buscarPorId(Long idTransacao){
        return registrosRepository.findById(idTransacao).orElseThrow(() -> new RuntimeException("[AVISO] - Registro de empréstimo não encontrado"));
    }
}