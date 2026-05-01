package biblioteca_spring.service;

import biblioteca_spring.exception.BusinessException;
import biblioteca_spring.model.Livro;
import biblioteca_spring.model.Usuario;
import biblioteca_spring.repository.RegistrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidadorEmprestimo {
    private final RegistrosRepository registrosRepository;
    @Autowired
    public ValidadorEmprestimo(RegistrosRepository registrosRepository){
        this.registrosRepository = registrosRepository;
    }

    public void podeEmprestar(Usuario usuario, Livro livro){
        boolean jaPossuiEsseExemplar = registrosRepository.existsByUsuarioAndLivroAndFinalizadoFalse(usuario, livro);

        if (jaPossuiEsseExemplar){
            throw new BusinessException("[AVISO] - O Usuário ja está com esse livro. ");
        }

        if (!livro.isDisponivel()){
            throw new BusinessException("[AVISO] - O livro não esta disponível");
        }

        if (usuario.getSaldo().getSaldoDevedor() >= usuario.getLimiteSaldo()) {
            throw new BusinessException("[AVISO] - Limite de saldo devedor atingido");
        }

        if (registrosRepository.countByUsuarioAndFinalizadoFalse(usuario) >= usuario.getLimiteLivros()){
            throw new BusinessException("[AVISO] - Limite de livros em posse atingido");
        }
    }
}
