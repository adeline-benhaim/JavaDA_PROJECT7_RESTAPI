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

    @Size(max = 30, message = "30 characters maximum allowed")
    @NotBlank(message = "Account is mandatory")
    String account;

    @Size(max = 30, message = "30 characters maximum allowed")
    @NotBlank(message = "Type is mandatory")
    String type;

    Double buyQuantity;
}
