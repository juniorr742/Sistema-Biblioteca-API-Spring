package biblioteca_spring.controller;

import biblioteca_spring.dto.EmprestimoRequestDTO.EmprestimoRequestDTO;
import biblioteca_spring.dto.EmprestimoRequestDTO.EmprestimoResponseDTO;
import biblioteca_spring.model.Livro;
import biblioteca_spring.model.Emprestimo;
import biblioteca_spring.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registros")
public class EmprestimoController {
    private final EmprestimoService emprestimoService;

    @Autowired
    public EmprestimoController(EmprestimoService emprestimoService){
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public EmprestimoResponseDTO emprestarLivro(@RequestBody EmprestimoRequestDTO emprestimoDTO){
        Emprestimo registro = emprestimoService.emprestarLivro(emprestimoDTO);
        return new EmprestimoResponseDTO(registro.getUsuario().getNome(), registro.getLivro().getTitulo(), registro.getIdTransacao(), registro.isFinalizado());
    }

    @PutMapping("{idTransacao}")
    public EmprestimoResponseDTO devolverLivro(@PathVariable Long idTransacao){
        Emprestimo registro = emprestimoService.devolverLivro(idTransacao);
        return new EmprestimoResponseDTO(registro.getUsuario().getNome(), registro.getLivro().getTitulo(), registro.getIdTransacao(), registro.isFinalizado());
    }

    @GetMapping
    public List<EmprestimoResponseDTO> listarTodos() {
        return emprestimoService.listarHistorico();
    }

    @GetMapping("{idTransacao}/buscar")
    public EmprestimoResponseDTO buscarPorId(@PathVariable Long idTransacao){
        Emprestimo registro = emprestimoService.buscarPorId(idTransacao);
        return new EmprestimoResponseDTO(registro.getUsuario().getNome(), registro.getLivro().getTitulo(), registro.getIdTransacao(), registro.isFinalizado());
    }

    @GetMapping("{idUsuario}")
    public List<Livro> livrosAtivosPorUsuario(@PathVariable Long idUsuario){
        return emprestimoService.listarLivrosAtivosPorUsuario(idUsuario);
    }

}
