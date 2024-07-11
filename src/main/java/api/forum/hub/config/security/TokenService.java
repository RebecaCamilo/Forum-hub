package api.forum.hub.config.security;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Forum.hub")
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token jwt", e);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Voll.med")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));

    }
}
