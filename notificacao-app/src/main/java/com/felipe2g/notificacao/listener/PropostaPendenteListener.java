package com.felipe2g.notificacao.listener;

import com.felipe2g.notificacao.constante.MensagemConstante;
import com.felipe2g.notificacao.domain.Proposta;
import com.felipe2g.notificacao.service.NotificacaoTwilioService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PropostaPendenteListener {

    private NotificacaoTwilioService notificacaoTwillioService;

    public PropostaPendenteListener(NotificacaoTwilioService notificacaoTwillioService) {
        this.notificacaoTwillioService = notificacaoTwillioService;
    }

    @RabbitListener(queues = "${spring.queue.proposta.pendente}")
    public void propostaPendente(Proposta proposta) {
        String mensagem = String.format(MensagemConstante.PROPOSTA_EM_ANALISE, proposta.getUsuario().getNome());

        notificacaoTwillioService.notificar(mensagem);
    }
}
