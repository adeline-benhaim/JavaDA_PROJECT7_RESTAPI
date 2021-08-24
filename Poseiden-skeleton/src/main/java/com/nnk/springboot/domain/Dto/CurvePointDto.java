package com.nnk.springboot.domain.Dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurvePointDto {

    Integer id;

    @Min(value=1, message = "The value of curve id must be positive and greater than 0")
    @NotNull(message = "Curve Id must not be null")
    Integer curveId;

    Double term;

    Double value;
}
