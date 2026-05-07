package biblioteca_spring.dto;

import jakarta.persistence.Column;

public class UsuarioRequestDTO {
    private String nome;
    private String tipo;
    private String email;

    public UsuarioRequestDTO(String nome, String tipo, String email){
        this.nome = nome;
        this.tipo = tipo;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
