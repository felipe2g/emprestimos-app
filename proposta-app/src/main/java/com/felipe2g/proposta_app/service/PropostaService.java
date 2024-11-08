package com.felipe2g.proposta_app.service;

import com.felipe2g.proposta_app.dto.PropostaRequestDTO;
import com.felipe2g.proposta_app.dto.PropostaResponseDTO;
import com.felipe2g.proposta_app.entity.Proposta;
import com.felipe2g.proposta_app.mapper.PropostaMapper;
import com.felipe2g.proposta_app.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;

    private final NotificacaoRabbitService notificacaoRabbitService;

    private final String exchange;

    public PropostaService(NotificacaoRabbitService notificacaoRabbitService,
                           @Value("${rabbitmq.propostapendente.exchange}") String exchange,
                           PropostaRepository propostaRepository) {
        this.notificacaoRabbitService = notificacaoRabbitService;
        this.exchange = exchange;
        this.propostaRepository = propostaRepository;
    }

    public PropostaResponseDTO criar(PropostaRequestDTO requestDTO) {
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(requestDTO);
        propostaRepository.save(proposta);

        notificarRabbitMQ(proposta);

        return PropostaMapper.INSTANCE.convertEntityToDto(proposta);
    }

    private void notificarRabbitMQ(Proposta proposta) {
        try {
            notificacaoRabbitService.notificar(proposta, exchange);
        } catch (RuntimeException e) {
            proposta.setIntegrada(false);
            propostaRepository.save(proposta);
        }
    }

    public List<PropostaResponseDTO> obterProposta() {
        Iterable<Proposta> all = propostaRepository.findAll();
        return PropostaMapper.INSTANCE.convertListEntityToListDto(all);
    }
}
