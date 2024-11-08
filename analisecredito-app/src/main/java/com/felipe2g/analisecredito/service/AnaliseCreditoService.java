package com.felipe2g.analisecredito.service;

import com.felipe2g.analisecredito.domain.Proposta;
import com.felipe2g.analisecredito.exceptions.StrategyException;
import com.felipe2g.analisecredito.service.strategy.CalculoPonto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnaliseCreditoService {

    @Autowired
    private List<CalculoPonto> calculoPontoList;

    @Autowired
    NotificacaoRabitService notificacaoRabitService;

    @Value("${rabbitmq.exchange.proposta-concluida}")
    private String exchangePropostaConcluida;

    public void analisar(Proposta proposta) {
        try {
            int pontos = calculoPontoList.stream().mapToInt(impl -> impl.calcular(proposta)).sum();
            proposta.setAprovada(pontos > 350);
        } catch (StrategyException e) {
            proposta.setAprovada(false);
            proposta.setObservacao(e.getMessage());
        }
        notificacaoRabitService.notificar(exchangePropostaConcluida, proposta);
    }
}
