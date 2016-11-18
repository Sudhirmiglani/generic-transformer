package com.rubix.transformer.pojo;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
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
public class Field {

    private Long id;

    private String name;

    private String type;

    // to check this, not working
    @JsonProperty(defaultValue = "false")
    private Boolean isInternal;

}

