package com.rubix.transformer.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by sudhir.m on 12/11/16.
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayloadType {

    private Long id;

    private String name;

    private Long version;

    private List<Field> fields;
}
