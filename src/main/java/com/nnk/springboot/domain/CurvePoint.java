package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer curveId;

    Timestamp asOfDate;

    @NotNull(message = "Cannot be null")
    @Min(value = 0, message = "Must be greater than or equal to 0")
    Double term;

    @NotNull(message = "Cannot be null")
    @Min(value = 0, message = "Must be greater than or equal to 0")
    Double value;

    Timestamp creationDate;

}
