package com.fema.curricularizacao.auth;

import com.fema.curricularizacao.models.Funcionario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String gerarToken(Funcionario funcionario) {
        Date agora = new Date();
        Date dataExpiracao = new Date(agora.getTime() + expiration);

        SecretKey chaveSecreta = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        return Jwts.builder()
                .issuer("API Curricularização")
                .subject(funcionario.getEmail())
                .issuedAt(agora)
                .expiration(dataExpiracao)
                .signWith(chaveSecreta)
                .compact();
    }

    public String getSubjectToken(String token) {
        SecretKey chaveSecreta = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        return Jwts.parser()
                .verifyWith(chaveSecreta)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
