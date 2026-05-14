package biblioteca_spring.dto.UsuarioRequestDTO;

public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String tipo;
    private int limiteLivros;
    private double limiteSaldo;

    public UsuarioResponseDTO(Long id, String nome, String email, String tipo, int limiteLivros, double limiteSaldo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
        this.limiteLivros = limiteLivros;
        this.limiteSaldo = limiteSaldo;
    }

}
