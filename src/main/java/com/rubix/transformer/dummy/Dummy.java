package com.rubix.transformer.dummy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by sudhir.m on 14/11/16.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dummy {

    private Long id;

    private String name;

    private Boolean isPresent;

    private Object obj;

    private YetAnother yetAnother;

    private List<AnotherDummy> anotherDummies;
}
