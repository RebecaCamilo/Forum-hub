package api.forum.hub.domain;

import api.forum.hub.domain.dto.CadastroTopicoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private Short status;
    private String autor;
    private String curso;

    public Topico(CadastroTopicoRequest request) {
        this.titulo = request.titulo();
        this.mensagem = request.mensagem();
        this.dataCriacao = LocalDateTime.now();
        this.status = 1;
        this.autor = request.autor();
        this.curso = request.curso();
    }
}
