package com.felipe2g.analisecredito.service.strategy.impl;

import com.felipe2g.analisecredito.domain.Proposta;
import com.felipe2g.analisecredito.service.strategy.CalculoPonto;
import org.springframework.stereotype.Component;

@Component
public class PrazoPagamentoImpl implements CalculoPonto {


    @Override
    public int calcular(Proposta proposta) {
        return proposta.getPrazoPagamento() < 120 ? 80 : 0;
    }
}
