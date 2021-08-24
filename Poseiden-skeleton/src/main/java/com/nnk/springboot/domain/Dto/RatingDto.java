package com.nnk.springboot.domain.Dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {

    Integer id;

    @Size(max = 125, message = "125 characters maximum allowed")
    String moodysRating;

    @Size(max = 125, message = "125 characters maximum allowed")
    String sandPRating;

    @Size(max = 125, message = "125 characters maximum allowed")
    String fitchRating;

    Integer orderNumber;
}
