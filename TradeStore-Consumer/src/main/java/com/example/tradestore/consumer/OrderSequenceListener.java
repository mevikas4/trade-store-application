package com.example.tradestore.consumer;

import com.example.tradestore.model.OrderSequence;
import com.example.tradestore.model.Order;
import com.example.tradestore.service.impl.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class OrderSequenceListener extends AbstractMongoEventListener<Order> {
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    public void onBeforeConvert(BeforeConvertEvent<Order> event) {
        if (event.getSource().getId() == null) { // Assuming getId() returns null for new documents
            event.getSource().setId(sequenceGeneratorService.generateSequence(OrderSequence.SEQUENCE_NAME)); // Replace SEQUENCE_NAME
        }

    }
}