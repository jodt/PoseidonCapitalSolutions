package com.nnk.springboot.domain;

import jakarta.persistence.*;
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

    Double term;

    Double value;

    Timestamp creationDate;

}
