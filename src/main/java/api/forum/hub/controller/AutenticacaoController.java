package api.forum.hub.controller;

import api.forum.hub.domain.dto.AutenticacaoRequest;
import api.forum.hub.config.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity efetuarLogin(@RequestBody @Valid AutenticacaoRequest dados) {
        var authenticationTtoken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationTtoken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok().build(new TokenJWTResponse(tokenJWT));
    }

}
