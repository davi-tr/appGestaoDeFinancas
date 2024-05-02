package com.femass.gestao.domain.usuario;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.femass.gestao.domain.carteira.Carteira;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "usuario")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String numeroTelefone;
    private String email;
    private boolean status;
    private String password;
    private String login;
    @OneToOne
    @JoinColumn(name = "carteira_id")
    Carteira carteira;

    public Usuario(DadosCadastroUsuario dados){
        this.status = true;
        this.numeroTelefone = dados.numeroTelefone();
        this.email = dados.email();
        this.login = dados.login();
        this.nome = dados.nome();
    }

    public void addWallet(Carteira carteira){
        this.carteira = carteira;
        carteira.setUsuario(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
