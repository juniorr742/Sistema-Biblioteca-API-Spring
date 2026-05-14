package biblioteca_spring.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "registros")
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransacao;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_livro")
    private Livro livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean finalizado;

    protected Emprestimo(){}
    public Emprestimo(Usuario usuario, Livro livro){
        this.usuario = usuario;
        this.livro = livro;
        this.dataEmprestimo = LocalDate.now();
        this.finalizado = false;
    }

    public Long getIdTransacao() {
        return idTransacao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void finalizarEmprestimo(){
        this.dataDevolucao = LocalDate.now();
        this.finalizado = true;
    }

    @Override
    public String toString(){
        return String.format("Transação: %d | Usuário ID: %d | Livro ID: %d | Data: %s | Status: %s",
                idTransacao, usuario.getId(), livro.getId(), dataEmprestimo, (finalizado ? "Devolvido":"Ativo"));
    }
}