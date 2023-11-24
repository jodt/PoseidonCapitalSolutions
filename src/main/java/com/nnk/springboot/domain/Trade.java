package com.nnk.springboot.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer tradeId;

    String account;

    String type;

    Double buyQuantity;

    Double sellQuantity;

    Double buyPrice;

    Double sellPrice;

    String benchmark;

    Timestamp tradeDate;

    String security;

    String status;

    String trader;

    String book;

    String creationName;

    Timestamp creationDate;

    String revisionName;

    Timestamp revisionDate;

    String dealName;

    String dealType;

    String sourceListId;

    String side;

}
