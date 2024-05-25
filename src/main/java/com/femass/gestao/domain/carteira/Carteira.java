package com.femass.gestao.domain.carteira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.femass.gestao.domain.entradas.Entrada;
import com.femass.gestao.domain.gasto.Gasto;
import com.femass.gestao.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

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
    private BigDecimal valorDisponivel;
    private BigDecimal Salario;
    @OneToMany(mappedBy = "carteira")
    List<Gasto> gastos;
    @OneToMany(mappedBy = "carteira")
    List<Entrada> entradas;

    public void addGasto(Gasto gasto) {
        gastos.add(gasto);
    }
    public void removeGasto(Gasto gasto) {
        gastos.remove(gasto);
    }

    public void addEntrada(Entrada entrada) {
        entradas.add(entrada);
    }

    public void removeEntrada(Entrada entrada) {
        entradas.remove(entrada);
    }

    public void updateValorDisponivel() {
        var total = getValorDisponivel();
        for(Gasto gasto : gastos){
            gasto.setValor(gasto.getValor().multiply(BigDecimal.valueOf(-1)));
            total = (total.add(gasto.getValor()));
            setValorDisponivel(total);
        }
        for(Entrada entrada : entradas){
            total = (total.add(entrada.getValor()));
            setValorDisponivel(total);
        }
    }

    public void addUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Object> getGastos20() {
        int limite = 1;
        int index = 0;
        List<Object> gastosN = new ArrayList<>();
        for (Gasto gasto : this.gastos) {
                gastosN.add(gasto);
        }
        for (Entrada entrada : this.entradas) {
            gastosN.add(entrada);
        }
        return gastosN;
    }

}
