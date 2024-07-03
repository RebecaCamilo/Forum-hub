package api.forum.hub.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizacaoTopicoRequest(
        @NotNull
        Long id,
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
