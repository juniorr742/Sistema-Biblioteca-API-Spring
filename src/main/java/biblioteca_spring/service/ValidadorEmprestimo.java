package biblioteca_spring.service;

import biblioteca_spring.model.Livro;
import biblioteca_spring.model.Usuario;
import biblioteca_spring.repository.UsuarioRepository;

public class ValidadorEmprestimo {
    UsuarioRepository usuarioRepository;

    public ValidadorEmprestimo(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public boolean podeEmprestar(Usuario usuario, Livro livro){
        boolean jaPossuiEsseExemplar = usuario.getlivroEmprestado().stream()
                .anyMatch(l -> l.getId() == livro.getId());

        if (jaPossuiEsseExemplar){
            System.out.println("ERRO: O usuário já está em posse desse exemplar.");
            return false;
        }

        if (!livro.isDisponivel()){
            System.err.println("ERRO: O Livro não está disponivel.");
            return false;
        }

        if (usuario.getSaldo().getSaldoDevedor() >= usuario.getLimiteSaldo()) {
            System.out.println("ERRO: Limite de saldo atingido, por favor realize o pagamento.");
            return false;
        }

        if (usuario.getlivroEmprestado().size() >= usuario.getLimiteLivros()){
            System.out.println("ERRO: Limite de livros atingido, por favor realize a devolução.");
            return false;
        }

        return true;
    }
}
