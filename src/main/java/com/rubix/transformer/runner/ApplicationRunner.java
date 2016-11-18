package com.rubix.transformer.runner;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rubix.transformer.component.Transformer;
import com.rubix.transformer.dummy.Dummy;
import com.rubix.transformer.processor.PayloadProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sudhir.m on 15/11/16.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationRunner implements CommandLineRunner {

    private final static Log log = LogFactory.getLog(ApplicationRunner.class);

    private final PayloadProcessor payloadProcessor;

    private final Transformer transformer;

    @Override
    public void run(String... strings) throws Exception {
        Class[] classes = {Dummy.class};

        JSONObject jsonObject = new JSONObject()
                .put("xId", 1)
                .put("str", "sjhd")
                .put("bool", false);

        transformer.transform(jsonObject, 1L);

        //payloadProcessor.persistJsonSchema(classes);

//        JSONObject jsonObject = new JSONObject()
//                .put("rating", new JSONObject()
//                        .put("primary", new JSONObject()
//                                .put("values", 3))
//                        .put("quality", new JSONObject()
//                                .put("values", 3)));
//
//        System.out.println(jsonObject);
//
//
//        JSONArray jsonArray = new JSONArray()
//                .put(new JSONObject()
//                        .put("operation", "shift")
//                        .put("spec", new JSONObject()
//                                .put("rating", new JSONObject()
//                                        .put("primary", new JSONObject()
//                                                .put("values", "RATING")))));
//
//
//        List chainrSpecJSON = JsonUtils.jsonToList(jsonArray.toString());
//        Chainr chainr = Chainr.fromSpec(chainrSpecJSON);
//
//        Object inputJSON = JsonUtils.jsonToObject(jsonObject.toString());
//
//        Object output = chainr.transform(inputJSON);
//
//        System.out.println(output);
    }

}
