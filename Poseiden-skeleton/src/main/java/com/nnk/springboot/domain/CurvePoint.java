package com.nnk.springboot.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull(message = "Curve Id must not be null")
    @Column(name = "curve_id")
    Integer curveId;

    @Column(name = "as_of_date")
    Timestamp asOfDate;

    Double term;

    Double value;

    @Column(name = "creation_date")
    Timestamp creationDate;

}
