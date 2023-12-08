package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank(message = "Moody's rating is required")
    String moodysRating;

    @NotBlank(message = "S and P is required")
    String sandPRating;

    @NotBlank(message = "Fitch's rating is required")
    String fitchRating;

    @Min(value = 1, message = "Must be greater than or equal to 1")
    Integer orderNumber;

}
