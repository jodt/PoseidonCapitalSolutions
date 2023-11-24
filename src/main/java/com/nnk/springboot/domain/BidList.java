package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    Integer BidListId;

    @NotNull
    String account;

    @NotNull
    String type;

    Double bidQuantity;

    Double askQuantity;

    Double bid;

    Double ask;

    String benchmark;

    Timestamp bidListDate;

    String commentary;

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
