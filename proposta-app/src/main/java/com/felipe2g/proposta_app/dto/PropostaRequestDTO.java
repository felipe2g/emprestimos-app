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
public class PropostaRequestDTO {
    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private Double renda;
    private Double valorSolicitado;
    private int prazoPagamento;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropostaRequestDTO that = (PropostaRequestDTO) o;
        return prazoPagamento == that.prazoPagamento && Objects.equals(nome, that.nome) && Objects.equals(sobrenome, that.sobrenome) && Objects.equals(cpf, that.cpf) && Objects.equals(renda, that.renda) && Objects.equals(valorSolicitado, that.valorSolicitado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, sobrenome, cpf, renda, valorSolicitado, prazoPagamento);
    }

}
