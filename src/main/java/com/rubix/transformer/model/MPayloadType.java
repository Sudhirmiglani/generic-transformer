package com.rubix.transformer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by sudhir.m on 12/11/16.
 */

@Entity
@Table(name = "payload_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MPayloadType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payload-type-id-generator")
    @SequenceGenerator(name = "payload-type-id-generator", sequenceName = "payload_type_id_generator", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long version;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable
    private List<MField> fields;
}
