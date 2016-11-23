package com.rubix.transformer.component;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.rubix.transformer.dao.TransformDao;
import com.rubix.transformer.model.MField;
import com.rubix.transformer.model.MTransformAdaptor;
import com.rubix.transformer.pojo.Field;
import com.rubix.transformer.pojo.TransformAdaptor;
import com.rubix.transformer.pojo.Transformation;
import com.rubix.transformer.transformer.TransformAdaptorTransformer;
import com.rubix.transformer.util.BinarySearchTree;
import com.rubix.transformer.util.TransformationComparator;
import com.rubix.wms.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by sudhir.m on 11/11/16.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransformerComponentImpl implements TransformerComponent {

    private final TransformDao transformDao;

    private final TransformationComparator transformationComparator;

    private final TransformAdaptorTransformer transformAdaptorTransformer;

    @Override
    public JSONObject transform(JSONObject jsonObject, Long adaptorId) throws NotFoundException {
        System.out.println("Input JSON " + jsonObject);

        final MTransformAdaptor mTransformAdaptor = transformDao.findOne(adaptorId);
        if (mTransformAdaptor == null) {
            throw new NotFoundException(String.format("Adaptor with id %d Not Found", adaptorId));
        }
        final TransformAdaptor transformAdaptor = transformAdaptorTransformer.toPojo(mTransformAdaptor);
        final List<Transformation> transformations = transformAdaptor.getTransformations();

        Collections.sort(transformations, transformationComparator);

        final Map<String, String> sourceDestinationMapping = getSourceToDestinationMappings(transformations);

        final JSONObject inputJson = createJsonSpec(transformations, jsonObject, sourceDestinationMapping);

        System.out.println("Output JSON " + inputJson);

        return transform(jsonObject, inputJson);

    }

    private Map<String, String> getSourceToDestinationMappings(final List<Transformation> transformations) {
        Map<String, String> sourceDestinationMapping = new HashMap<>();
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        transformations.forEach((transformation) -> {
            final Field source = transformation.getSource();
            final Field destination = transformation.getDestination();
            binarySearchTree.insert(source.getName(), destination.getName());
        });
        sourceDestinationMapping = binarySearchTree.getAllLeaveNodes();
        return sourceDestinationMapping;
    }

    private JSONObject createJsonSpec(List<Transformation> transformations, JSONObject jsonObject, Map<String, String> sourceDestinationMapping) {

        JSONObject inputJson = null;
        try {
            inputJson = new JSONObject(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        traverseInputJson(inputJson, jsonObject, sourceDestinationMapping, "");
        return inputJson;
    }


    private void traverseInputJson(JSONObject inputJson, JSONObject jsonObject, Map<String, String> sourceDestinationMapping, String prefix) {
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = StringUtils.isNotBlank(prefix) ? prefix + "." + iterator.next() : iterator.next();
            if (sourceDestinationMapping.containsKey(key)) {
                putNestedObject(inputJson, key, sourceDestinationMapping.get(key));
            } else {
                try {
                    Object object = jsonObject.get(key);
                    if (object instanceof JSONObject) {
                        JSONObject j = (JSONObject) object;
                        if (j.keys() != null) {
                            traverseInputJson(inputJson, j, sourceDestinationMapping, key);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private JSONObject getJsonObjectByNestedProperty(JSONObject inputJson, String key) {
        String nodes[] = key.split("\\.");
        JSONObject jsonObject = inputJson;
        int i = 0;
        while (i > nodes.length - 1) {
            if (!jsonObject.has(nodes[i])) {
                return null;
            }
            try {
                jsonObject = (JSONObject) jsonObject.get(nodes[i]);
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    private void putNestedObject(JSONObject inputJson, String key, String value) {

        String nodes[] = key.split("\\.");
        JSONObject jsonObject = inputJson;
        int i = 0;
        while (i < nodes.length - 1) {
            try {
                jsonObject = (JSONObject) jsonObject.get(nodes[i]);
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            jsonObject.put(nodes[i], value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private JSONObject transform(JSONObject jsonObject, JSONObject spec) {
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray()
                    .put(new JSONObject()
                            .put("operation", "shift")
                            .put("spec", spec));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray == null) {
            // handle error scenario
        }
        List chainrSpecJSON = JsonUtils.jsonToList(jsonArray.toString());
        Chainr chainr = Chainr.fromSpec(chainrSpecJSON);

        Object inputJSON = JsonUtils.jsonToObject(jsonObject.toString());

        Object output = chainr.transform(inputJSON);

        JSONObject transformedJson = null;
        try {
            transformedJson = new JSONObject(JsonUtils.toJsonString(output));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return transformedJson;

    }


    private void createJsonObject(MField source, MField destination, JSONObject jsonObject) {

        int depthOfNode = source.getName().split("\\.").length;
        if (depthOfNode == 1) {
            try {
                jsonObject.put(source.getName(), destination.getName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return;
        }
//        while (depthOfNode > 1) {
//            //
//
//        }

    }

    private Object getJsonObjectByType(String type) {

        //switch (type)
        return null;

    }
}
