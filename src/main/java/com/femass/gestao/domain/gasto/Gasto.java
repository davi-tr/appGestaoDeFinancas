package com.femass.gestao.domain.gasto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.femass.gestao.domain.carteira.Carteira;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
    @JsonIgnore
    protected Carteira carteira;

    private BigDecimal valor;
    private boolean eParcela;
    private BigDecimal valorParcela;
    private String descricao;
    private String Local;
    private ZonedDateTime dataEntrada;
    private int parcelas;
    @Enumerated(EnumType.STRING)
    private Categorias categoria;

    public Gasto(DadosGasto dadosGasto) {
        this.valor = dadosGasto.valor();
        this.descricao = dadosGasto.descricao();
        this.Local = dadosGasto.Local();
        if(eParcela){
            this.valor = valorParcela.multiply(BigDecimal.valueOf(parcelas));
            this.parcelas = dadosGasto.parcelas();
            this.valorParcela = dadosGasto.valorParcela();
        }
        this.dataEntrada = ZonedDateTime.now();
        this.categoria = dadosGasto.categoria();
    }


    public void updateGasto(DadosGasto dadosGasto){
        if (dadosGasto.valor() != valor){
            this.valor = dadosGasto.valor();
        }
        if (dadosGasto.descricao() != descricao){
            this.descricao = dadosGasto.descricao();
        }
        if(dadosGasto.Local() != Local){
            this.Local = dadosGasto.Local();
        }
        if(eParcela){
            this.valor = valorParcela.multiply(BigDecimal.valueOf(parcelas));
            this.parcelas = dadosGasto.parcelas();
            this.valorParcela = dadosGasto.valorParcela();
        }
    }
}
