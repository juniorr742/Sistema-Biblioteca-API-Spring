package biblioteca_spring.model;

import jakarta.persistence.*;
import biblioteca_spring.model.Pagamento;

import java.util.Collections;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Embedded
    private Pagamento saldo;


    protected Usuario(){}
    public Usuario (String nome){
        this.nome = nome;
        this.saldo = new Pagamento();
    }

    public String getNome(){return nome;}
    public Long getId(){return id;}
    public Pagamento getSaldo() {
        return saldo;
    }

    public abstract double getLimiteSaldo();
    public abstract int getLimiteLivros();

    public abstract String obterTipo();

    @Override
    public String toString() {
        return "Nome: " + this.nome + " | ID: " + this.id + " | Saldo: R$ " + this.saldo;
    }

}
