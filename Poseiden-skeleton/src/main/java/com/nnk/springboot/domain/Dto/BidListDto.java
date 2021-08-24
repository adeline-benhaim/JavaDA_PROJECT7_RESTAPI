package com.nnk.springboot.domain.Dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidListDto {

    private Integer bidListId;

    @NotBlank(message = "Account is mandatory")
    @Size(max = 30, message = "30 characters maximum allowed")
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30, message = "30 characters maximum allowed")
    private String type;

    private Double bidQuantity;
}
