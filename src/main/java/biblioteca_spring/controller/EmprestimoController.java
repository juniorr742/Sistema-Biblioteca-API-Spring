package biblioteca_spring.controller;

import biblioteca_spring.dto.EmprestimoRequestDTO;
import biblioteca_spring.dto.LivroRequestDTO;
import biblioteca_spring.dto.UsuarioRequestDTO;
import biblioteca_spring.model.Livro;
import biblioteca_spring.model.RegistroEmprestimo;
import biblioteca_spring.model.Usuario;
import biblioteca_spring.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registros")
public class EmprestimoController {
    EmprestimoService emprestimoService;

    @Autowired
    public EmprestimoController(EmprestimoService emprestimoService){
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public RegistroEmprestimo emprestarLivro(@RequestBody EmprestimoRequestDTO emprestimoDTO){
        return emprestimoService.emprestarLivro(emprestimoDTO);
    }

    @PutMapping("{idTransacao}")
    public RegistroEmprestimo devolverLivro(@PathVariable Long idTransacao){
       return emprestimoService.devolverLivro(idTransacao);
    }

    @GetMapping
    public List<RegistroEmprestimo> listarTodos() {
        return emprestimoService.listarHistorico();
    }

    @GetMapping("{idUsuario}")
    public List<Livro> livrosAtivosPorUsuario(Long idUsuario){
        return emprestimoService.listarLivrosAtivosPorUsuario(idUsuario);
    }

}
