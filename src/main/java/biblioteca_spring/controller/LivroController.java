package biblioteca_spring.controller;

import biblioteca_spring.dto.LivroRequestDTO;
import biblioteca_spring.model.Livro;
import biblioteca_spring.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {
    LivroService livroService;

    @Autowired
    public LivroController(LivroService livroService){
        this.livroService = livroService;
    }

    @GetMapping
    public List<Livro> listarTodos(){
        return livroService.listarTodos();
    }

    @PostMapping
    public Livro salvar(@RequestBody LivroRequestDTO livroDTO){
        return livroService.salvar(livroDTO);
    }

    @GetMapping("/{id}")
    public Livro buscarPorId(@PathVariable Long id){
        return livroService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Livro atualizar(@PathVariable Long id ,@RequestBody LivroRequestDTO livroDTO){
        return livroService.atualizar(id ,livroDTO);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        livroService.deletar(id);
    }

    @GetMapping("/buscar")
    public Livro buscarLivroPorTitulo(@RequestParam String titulo){
        return livroService.buscarLivroPorTitulo(titulo);
    }

}
