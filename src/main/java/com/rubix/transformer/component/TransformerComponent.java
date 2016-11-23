package com.rubix.transformer.component;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * Created by sudhir.m on 11/11/16.
 */
public interface TransformerComponent {

    JSONObject transform(JSONObject jsonObject, Long adaptorId) throws Exception;

}
