package com.nnk.springboot.domain.Dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeDto {

    Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    @Size(max = 30, message = "30 characters maximum allowed")
    String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30, message = "30 characters maximum allowed")
    String type;

    Double buyQuantity;
}
