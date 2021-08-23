package com.nnk.springboot.domain.Dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {

    Integer id;

    String moodysRating;

    String sandPRating;

    String fitchRating;

    Integer orderNumber;
}
