package com.rubix.transformer.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by sudhir.m on 11/11/16.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransformAdaptor {

    private Long id;

    private List<Transformation> transformations;

    private PayloadType sourcePayloadType;

}
