package biblioteca_spring.dto.EmprestimoRequestDTO;

public record EmprestimoResponseDTO(String nomeUsuario, String nomeLivro, Long idTransacao, boolean finalizado) {
}
