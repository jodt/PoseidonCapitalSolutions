package com.nnk.springboot.domain;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String moodysRating;

    String sandPRating;

    String fitchRating;

    Integer orderNumber;

}
