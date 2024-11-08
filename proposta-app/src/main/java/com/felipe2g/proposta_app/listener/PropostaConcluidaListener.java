package com.felipe2g.proposta_app.listener;

import com.felipe2g.proposta_app.dto.PropostaResponseDTO;
import com.felipe2g.proposta_app.entity.Proposta;
import com.felipe2g.proposta_app.mapper.PropostaMapper;
import com.felipe2g.proposta_app.repository.PropostaRepository;
import com.felipe2g.proposta_app.service.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private WebSocketService webSocketService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta-concluida}")
    public void propostaConcluida(Proposta proposta) {
        propostaRepository.save(proposta);
        PropostaResponseDTO propostaResponseDTO = PropostaMapper.INSTANCE.convertEntityToDto(proposta);
        webSocketService.notificar(propostaResponseDTO);
    }
}
