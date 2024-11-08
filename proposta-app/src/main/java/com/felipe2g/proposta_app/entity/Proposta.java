package com.felipe2g.proposta_app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valorSolicitado;

    private int prazoPagamento;

    private Boolean aprovada;

    private boolean integrada;

    private String observacao;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_usuario")
    @JsonManagedReference
    private Usuario usuario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposta proposta = (Proposta) o;
        return prazoPagamento == proposta.prazoPagamento && integrada == proposta.integrada && Objects.equals(id, proposta.id) && Objects.equals(valorSolicitado, proposta.valorSolicitado) && Objects.equals(aprovada, proposta.aprovada) && Objects.equals(observacao, proposta.observacao) && Objects.equals(usuario, proposta.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valorSolicitado, prazoPagamento, aprovada, integrada, observacao, usuario);
    }
}
