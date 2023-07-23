package io.github.calvary.erp.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BsItemConsumer {

    private static final Logger log = LoggerFactory.getLogger(BsItemConsumer.class);

    @KafkaListener(topics = "transaction-entry-topic", groupId = "calvary-erp-reports")
    public void processDepreciationJobMessages(List<TransactionEntryMessage> messages) {
        // Process the batch of depreciation job messages
        for (TransactionEntryMessage message : messages) {
            // Extract the necessary details from the message

            log.debug("Received message for entry-id id {}", message.getId());

            // TODO run balance-sheet update

            log.debug("BS Update for transaction-entry-id id {} complete, sequence status update begins...", message.getId());
        }
    }
}
