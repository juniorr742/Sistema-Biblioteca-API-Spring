package biblioteca_spring.dto.UsuarioRequestDTO;

    public record UsuarioResponseDTO(Long id, String nome, String email, String tipo, int limiteLivros, double limiteSaldo){}

