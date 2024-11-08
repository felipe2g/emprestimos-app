package com.felipe2g.analisecredito.service.strategy.impl;

import com.felipe2g.analisecredito.domain.Proposta;
import com.felipe2g.analisecredito.service.strategy.CalculoPonto;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OutrosEmprestimosAndamentoImpl implements CalculoPonto {


    @Override
    public int calcular(Proposta proposta) {
        return outrosEmprestimosAndamento() ? 0 : 80;
    }

    private boolean outrosEmprestimosAndamento() {
        return new Random().nextBoolean();
    }
}
