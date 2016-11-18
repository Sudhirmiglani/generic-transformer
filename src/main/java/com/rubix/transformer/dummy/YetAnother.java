package com.rubix.transformer.dummy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * Created by sudhir.m on 14/11/16.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YetAnother {

    private HashMap<String, String> hashMap;
}
