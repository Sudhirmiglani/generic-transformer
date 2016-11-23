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

        JSONArray jsonArray = new JSONArray();
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        jsonArray.put(list);

        JSONObject j2 = new JSONObject();
        j2.put("arr", jsonArray);

        JSONObject j1= new JSONObject();
        j1.put("arr", j2);


        System.out.println(j1);
        JSONObject jsonObject = new JSONObject()
                .put("arr", jsonArray)
                .put("str", "sjhd")
                .put("bool", false);

        System.out.println(transformer.transform(j1, 2L));

        //payloadProcessor.persistJsonSchema(classes);

    }

}
