package biblioteca_spring.dto.UsuarioRequestDTO;

public class UsuarioCadastroDTO {
    private String nome;
    private String tipo;
    private String email;
    private String senha;

    public UsuarioCadastroDTO(String nome, String tipo, String email, String senha){
        this.nome = nome;
        this.tipo = tipo;
        this.email = email;
        this.senha = senha;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
