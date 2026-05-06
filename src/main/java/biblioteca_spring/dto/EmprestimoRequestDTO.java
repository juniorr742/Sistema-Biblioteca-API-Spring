package biblioteca_spring.dto;

public class EmprestimoRequestDTO {
    private Long idUsuario;
    private Long idLivro;

    public EmprestimoRequestDTO(Long idUsuario, Long idLivro){
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
    }

    public Long getIdLivro() {
        return idLivro;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdLivro(Long idLivro) {
        this.idLivro = idLivro;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
