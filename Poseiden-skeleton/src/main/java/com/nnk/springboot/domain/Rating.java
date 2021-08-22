package com.nnk.springboot.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "moodys_rating")
    String moodysRating;

    @Column(name = "sand_p_rating")
    String sandPRating;

    @Column(name = "fitch_rating")
    String fitchRating;

    @Column(name = "order_number")
    Integer orderNumber;
}
