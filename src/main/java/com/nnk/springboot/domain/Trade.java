package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
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
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer tradeId;

    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Account is required")
    String account;

    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Type is required")
    String type;

    @NotNull(message = "Cannot be null")
    @Min(value = 0, message = "Must be greater than or equal to 0")
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
