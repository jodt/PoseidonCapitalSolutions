package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Account is mandatory")
    String account;

    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Type is mandatory")
    String type;

    @Min(value = 0, message = "Must be greater than or equal to 0")
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
