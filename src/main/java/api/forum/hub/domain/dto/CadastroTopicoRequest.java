package api.forum.hub.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record CadastroTopicoRequest(
        @NotBlank(message = "titulo obrigatório")
        String titulo,
        @NotBlank(message = "mensagem obrigatória")
        String mensagem,
        @NotBlank(message = "autor obrigatório")
        String autor,
        @NotBlank(message = "curso obrigatório")
        String curso
) {
}
