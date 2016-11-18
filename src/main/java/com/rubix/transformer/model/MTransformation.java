package com.rubix.transformer.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by sudhir.m on 11/11/16.
 */
@Entity
@Table(name = "transformation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MTransformation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transformation-id-generator")
    @SequenceGenerator(name = "transformation-id-generator", sequenceName = "transformation_id_generator", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private MField source;

    @OneToOne(fetch = FetchType.EAGER)
    private MField destination;

}
