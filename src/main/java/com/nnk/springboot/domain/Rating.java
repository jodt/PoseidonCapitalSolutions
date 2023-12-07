package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank(message = "Account is mandatory")
    String moodysRating;

    @NotBlank(message = "Account is mandatory")
    String sandPRating;

    @NotBlank(message = "Account is mandatory")
    String fitchRating;

    @NotNull(message = "Cannot be null")
    @Min(value = 1, message = "Must be greater than or equal to 1")
    Integer orderNumber;

}
