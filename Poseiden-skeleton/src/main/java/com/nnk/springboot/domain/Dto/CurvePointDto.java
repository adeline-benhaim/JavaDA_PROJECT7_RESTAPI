package com.nnk.springboot.domain.Dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurvePointDto {

    @NotBlank(message = "Curve Id must not be null")
    Integer curveId;

    Double term;

    Double value;
}
