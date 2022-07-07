package by.ganevich.kafka;

import by.ganevich.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerService {

    private final KafkaTemplate<String, TransactionDto> kafkaTemplate;

    public void produce(TransactionDto transactionDto) {
        kafkaTemplate.send("messages", transactionDto);
    }
}
