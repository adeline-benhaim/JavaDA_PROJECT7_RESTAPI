package com.nnk.springboot.domain.Dto;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleNameDto {

    Integer id;

    String name;

    String description;

    String json;

    String template;

    String sqlStr;

    String sqlPart;
}
