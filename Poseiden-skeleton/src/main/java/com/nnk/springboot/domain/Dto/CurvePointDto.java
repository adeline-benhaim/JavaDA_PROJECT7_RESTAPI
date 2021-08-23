package com.nnk.springboot.domain.Dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurvePointDto {

    Integer id;

    @NotNull(message = "Curve Id must not be null")
    Integer curveId;

    Double term;

    Double value;
}
