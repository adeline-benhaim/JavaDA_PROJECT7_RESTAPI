package com.nnk.springboot.domain.Dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleNameDto {

    Integer id;

    @Size(max = 125, message = "125 characters maximum allowed")
    String name;

    @Size(max = 125, message = "125 characters maximum allowed")
    String description;

    @Size(max = 125, message = "125 characters maximum allowed")
    String json;

    @Size(max = 125, message = "125 characters maximum allowed")
    String template;

    @Size(max = 125, message = "125 characters maximum allowed")
    String sqlStr;

    @Size(max = 125, message = "125 characters maximum allowed")
    String sqlPart;
}
