package com.femass.gestao.domain.gasto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.femass.gestao.domain.carteira.Carteira;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name= "Gastos")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carteira_id", nullable = false)
    Carteira carteira;

    private BigDecimal valor;
    private boolean eParcela;
    private BigDecimal valorParcela;
    private String descricao;
    private String Local;
    private int parcelas;

    public Gasto(BigDecimal valor, boolean eParcela, BigDecimal valorParcela, String descricao, String Local, Carteira carteira, int parcelas) {
        this.valor = valor;
        if(eParcela){
            this.valor = valorParcela.multiply(BigDecimal.valueOf(parcelas));
        }
        this.descricao = descricao;
        this.Local = Local;
        this.carteira = carteira;
    }
}
