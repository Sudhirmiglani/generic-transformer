package com.rubix.transformer.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sudhir.m on 11/11/16.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transformation {

    private Long id;

    private Field source;

    private Field destination;

}
