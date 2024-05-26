package com.femass.gestao.domain.entradas;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.femass.gestao.domain.carteira.Carteira;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name= "Entrada")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Entrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carteira_id", nullable = false)
    @JsonIgnore
    protected Carteira carteira;
    private String dataEntrada;
    private BigDecimal valor;
    private String descricao;
    private String Local;

    public Entrada(DadosEntrada dadosEntrada) {
        this.valor = dadosEntrada.valor();
        this.descricao = dadosEntrada.descricao();
        this.Local = dadosEntrada.Local();
        this.dataEntrada = formatarData(LocalDateTime.now());
    }

    private String formatarData(LocalDateTime data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        return data.format(formatter);
    }

    public void updateEntrada(DadosEntrada dadosEntrada){
        if (dadosEntrada.valor() != valor){
            this.valor = dadosEntrada.valor();
        }
        if (dadosEntrada.descricao() != descricao){
            this.descricao = dadosEntrada.descricao();
        }
        if(dadosEntrada.Local() != Local){
            this.Local = dadosEntrada.Local();
        }
    }
}
