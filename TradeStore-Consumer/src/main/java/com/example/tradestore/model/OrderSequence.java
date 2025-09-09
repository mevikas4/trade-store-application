package com.example.tradestore.model;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
public class OrderSequence {

    @Transient
    public static final String SEQUENCE_NAME = "order_sequence";

    @Id
    private String id;
    private long seq;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }
}
