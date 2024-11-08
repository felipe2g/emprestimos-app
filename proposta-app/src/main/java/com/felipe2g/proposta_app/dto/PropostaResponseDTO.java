package com.felipe2g.proposta_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PropostaResponseDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private Double renda;
    private String valorSolicitadoFmt;
    private int prazoPagamento;
    private boolean aprovada;
    private String observacao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropostaResponseDTO that = (PropostaResponseDTO) o;
        return prazoPagamento == that.prazoPagamento && aprovada == that.aprovada && Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(sobrenome, that.sobrenome) && Objects.equals(cpf, that.cpf) && Objects.equals(renda, that.renda) && Objects.equals(valorSolicitadoFmt, that.valorSolicitadoFmt) && Objects.equals(observacao, that.observacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, sobrenome, cpf, renda, valorSolicitadoFmt, prazoPagamento, aprovada, observacao);
    }
}

