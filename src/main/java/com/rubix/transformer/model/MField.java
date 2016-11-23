package com.rubix.transformer.model;

import com.rubix.transformer.pojo.PayloadType;
import lombok.*;

import javax.persistence.*;

/**
 * Created by sudhir.m on 11/11/16.
 */

@Entity
@Table(name = "field")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MField {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "field-id-generator")
    @SequenceGenerator(name = "field-id-generator", sequenceName = "field_id_generator", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @Column
    private String type;

    @Column(name = "is_internal")
    private Boolean isInternal;

    //private PayloadType payloadType;

}
