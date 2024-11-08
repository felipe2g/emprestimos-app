package com.felipe2g.notificacao.listener;

import com.felipe2g.notificacao.constante.MensagemConstante;
import com.felipe2g.notificacao.domain.Proposta;
import com.felipe2g.notificacao.service.NotificacaoTwilioService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    private NotificacaoTwilioService notificacaoTwillioService;

    public PropostaConcluidaListener(NotificacaoTwilioService notificacaoTwillioService) {
        this.notificacaoTwillioService = notificacaoTwillioService;
    }

    @RabbitListener(queues = "${spring.queue.proposta.concluida}")
    public void propostaConcluida(Proposta proposta) {
        String mensagem;

        if (proposta.getAprovada()) {
            mensagem = String.format(MensagemConstante.PROPOSTA_APROVADA, proposta.getUsuario().getNome());
        } else {
            mensagem = String.format(MensagemConstante.PROPOSTA_REPROVADA, proposta.getUsuario().getNome());
        }

        notificacaoTwillioService.notificar(mensagem);
    }
}
