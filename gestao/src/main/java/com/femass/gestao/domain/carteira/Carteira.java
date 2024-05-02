package com.femass.gestao.domain.carteira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.femass.gestao.domain.gasto.Gasto;
import com.femass.gestao.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name= "Carteira")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Carteira {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    Usuario usuario;
    private BigDecimal Salario;
    @OneToMany(mappedBy = "carteira")
    ArrayList<Gasto> gastos;

    public void addGasto(Gasto gasto) {
        gastos.add(gasto);
    }

    public void addUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
