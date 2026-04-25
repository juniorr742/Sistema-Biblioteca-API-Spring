package biblioteca_spring.service;

import biblioteca_spring.model.Livro;
import biblioteca_spring.repository.LivroRepository;
import org.springframework.stereotype.Service;

@Service
public class LivroService {
    LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository){
        this.livroRepository = livroRepository;
    }

    public Livro salvar(Livro livro){
        return livroRepository.save(livro);
    }

    public Livro buscarPorId(Long id){
        return livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }
}
