package com.rubix.transformer.model;

import com.rubix.transformer.pojo.PayloadType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by sudhir.m on 11/11/16.
 */

@Entity
@Table(name = "transform_adaptor")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MTransformAdaptor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transform-adaptor-id-generator")
    @SequenceGenerator(name = "transform-adaptor-id-generator", sequenceName = "transform_adaptor_id_generator", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private MPayloadType sourcePayloadType;

    @OneToOne(fetch = FetchType.LAZY)
    private MPayloadType destinationPayloadType;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable
    private List<MTransformation> transformations;
}
