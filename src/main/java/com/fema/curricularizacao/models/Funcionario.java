package com.fema.curricularizacao.models;

import com.fema.curricularizacao.enums.Atuacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="funcionario")
public class Funcionario implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="funcionario_id")
    private Long id;

    private String nome;

    @Column(name="email_usuario")
    private String email;

    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name="atuacao_enum")
    private Atuacao atuacao;

    public  Funcionario(Long id, String nome, String email, String senha, Atuacao atuacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.atuacao = atuacao;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + atuacao.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
