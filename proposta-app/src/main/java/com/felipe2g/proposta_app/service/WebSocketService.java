package com.felipe2g.proposta_app.service;

import com.felipe2g.proposta_app.dto.PropostaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    public void notificar(PropostaResponseDTO proposta) {
        template.convertAndSend("/propostas", proposta);
    }
}
