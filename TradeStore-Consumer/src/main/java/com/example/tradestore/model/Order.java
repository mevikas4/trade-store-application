package com.example.tradestore.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "Orders")
public class Order {
    @Id
    private Long id; // Or Integer, String, etc.

    // Other fields of your Trade entity
    @Column(name = "tradeId")
    private Long tradeId;

    @Column(name = "version")
    private int version;

    @Column(name = "counterPartyId")
    private String counterPartyId;
    @Column(name = "bookId")
    private String bookId;
    @Column(name = "maturityDate")
    private LocalDate maturityDate;
    @Column(name = "createdDate")
    private LocalDate createdDate;
    @Column(name = "expired")
    private boolean expired;

    public Order() {
    }

    public Order(Long id, Long tradeId, int version, String counterPartyId, String bookId, LocalDate maturityDate, LocalDate createdDate, boolean expired) {
        this.id = id;
        this.tradeId = tradeId;
        this.version = version;
        this.counterPartyId = counterPartyId;
        this.bookId = bookId;
        this.maturityDate = maturityDate;
        this.createdDate = createdDate;
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "Order{" +
                "tradeId=" + tradeId + ", version=" + version + ", counterPartyId=" + counterPartyId + ", bookId=" + bookId +
                ", maturityDate=" + maturityDate + ", createdDate=" + createdDate + ", expired=" + expired +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCounterPartyId() {
        return counterPartyId;
    }

    public void setCounterPartyId(String counterPartyId) {
        this.counterPartyId = counterPartyId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}