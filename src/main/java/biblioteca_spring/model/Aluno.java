package biblioteca_spring.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Aluno")
public class Aluno extends Usuario{
    protected Aluno(){}
    public Aluno(String nome, String email){
        super(nome, email);
    }

    @Override
    public double getLimiteSaldo() {
        return 15;
    }

    @Override
    public int getLimiteLivros(){
        return 3;
    }

    @Override
    public String obterTipo(){
        return "Aluno";
    }
}
