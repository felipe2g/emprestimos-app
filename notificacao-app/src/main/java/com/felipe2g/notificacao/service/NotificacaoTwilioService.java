package com.felipe2g.notificacao.service;

import com.felipe2g.notificacao.config.TwilioConfiguration;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoTwilioService {

    private TwilioConfiguration twilioConfiguration;

    public NotificacaoTwilioService(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    public void notificar(String mensagem) {
        twilioConfiguration.sendSMS("+5534996572760", mensagem);
    }
}
