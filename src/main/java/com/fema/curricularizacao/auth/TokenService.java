package com.fema.curricularizacao.auth;

import com.fema.curricularizacao.models.Funcionario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    private final Key chave;

    public TokenService() {
        String secret = System.getenv("JWT_SECRET");
        if (secret == null || secret.length() < 32) {
            secret = "muda_para_uma_chave_secreta_maior_de_32_bytes";
        }
        this.chave = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String gerarToken(Funcionario funcionario) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + 24 * 60 * 60 * 1000);

        return Jwts.builder()
                .setSubject(funcionario.getEmail())
                .claim("atuacao", funcionario.getAtuacao().name())
                .setIssuedAt(agora)
                .setExpiration(expiracao)
                .signWith(chave)
                .compact();
    }

    public boolean isTokenValido(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(chave)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();
            return expiration == null || expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(chave)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public String getAtuacaoFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(chave)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("atuacao", String.class);
        } catch (Exception e) {
            return null;
        }
    }
}