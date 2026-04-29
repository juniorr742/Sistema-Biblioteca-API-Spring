package biblioteca_spring.service;

import biblioteca_spring.dto.LivroRequestDTO;
import biblioteca_spring.model.Livro;
import biblioteca_spring.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository){
        this.livroRepository = livroRepository;
    }

    public Livro salvar(LivroRequestDTO livroDTO){

        if (livroRepository.existsByTituloContainingIgnoreCase(livroDTO.getTitulo())){
            throw new RuntimeException("[AVISO] - Livro com titulos idênticos.");
        }

        Livro livro = new Livro(livroDTO.getTitulo(), livroDTO.getAutor());
        return livroRepository.save(livro);
    }
    public Livro buscarPorId(Long id){
        return livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    public List<Livro> listarTodos(){
        return livroRepository.findAll();
    }

    public Livro atualizar(Long id, LivroRequestDTO livroDTO){
        if (!livroRepository.existsById(id)){
            throw new RuntimeException("Livro não existente");
         }

        Livro livro = new Livro(livroDTO.getTitulo(), livroDTO.getAutor());
        livro.setId(id);

        return livroRepository.save(livro);
    }

    public void deletar(Long id){
        if (!livroRepository.existsById(id)){
            throw new RuntimeException("[AVISO] - Livro não encontrado");
        }
        livroRepository.deleteById(id);
    }

    public List<Livro> buscarLivroPorTitulo(String titulo){
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }
}
