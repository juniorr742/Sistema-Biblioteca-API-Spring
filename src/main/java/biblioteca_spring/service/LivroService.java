package biblioteca_spring.service;

import biblioteca_spring.model.Livro;
import biblioteca_spring.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {
    LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository){
        this.livroRepository = livroRepository;
    }

    public Livro salvar(Livro livro){
        if (livroRepository.findByTitulo(livro.getTitulo()).isPresent()){
            throw new RuntimeException("[AVISO] - Livro com titulos idênticos.");
        }
        return livroRepository.save(livro);
    }
    public Livro buscarPorId(Long id){
        return livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    public List<Livro> listarTodos(){
        return livroRepository.findAll();
    }

    public Livro atualizar(Livro livro){

        if (!livroRepository.existsById(livro.getId())){
            throw new RuntimeException("Livro não existente");
         }
        return livroRepository.save(livro);
    }

    public void deletar(Long id){
        if (!livroRepository.existsById(id)){
            throw new RuntimeException("[AVISO] - Livro não encontrado");
        }
        livroRepository.deleteById(id);
    }
}
